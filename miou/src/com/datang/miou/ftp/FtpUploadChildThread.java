package com.datang.miou.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;
import org.apache.log4j.Logger;

import com.datang.miou.testplan.bean.Ftp;
import com.datang.miou.utils.StringUtils;

/**
 * FTP上传子线程
 * 
 * @author suntongwei
 */
public class FtpUploadChildThread implements Runnable {

	// LOG
	private static final Logger Log = Logger.getLogger(FtpUploadChildThread.class);
	
	// 每个子线程FTP_CLIENT
	private FTPClient cFtpClient = new FTPClient();
	
	// FTP参数集合
	private Ftp ftpParams = null;
	
	// 统计线程
	private FtpStatThread ftpStatThread;
	
	// BUFFER_SIZE
	private static final int COPY_BUFFER_SIZE = Util.DEFAULT_COPY_BUFFER_SIZE * 30;
	
	public FtpUploadChildThread(Ftp ftpParams, FtpStatThread ftpStatThread) {
		this.ftpParams = ftpParams;
		this.ftpStatThread = ftpStatThread;
	}
	
	@Override
	public void run() {
		
		Log.error("Ftp Upload Child Thread. " + Thread.currentThread().getName());
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			
			// 设置数据超时时间
			cFtpClient.setDataTimeout(ftpParams.getTimeout() * 1000);
			
			// 执行登录
			cFtpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
			cFtpClient.connect(ftpParams.getHostname(), ftpParams.getPort());
			cFtpClient.login(ftpParams.getUsername(), ftpParams.getPassword());
			cFtpClient.setControlEncoding("GBK");
			cFtpClient.setFileType(FTP.BINARY_FILE_TYPE);
			cFtpClient.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(cFtpClient.getReplyCode())) {
				// 登录失败
				Log.error("Ftp Upload Child Login Fail.");
				Thread.interrupted();
				return;
			}
			
			File file = null;
			// 获取所需上传的文件方式
			if (ftpParams.getIsUploadBySize()) {
//				long uploadSize = (ftpParams.getUploadFileSize().intValue() * 1024) / 
//					ftpParams.getThreadNum();
				is = new AssignSizeInputStream(ftpParams.getUploadFileSize().intValue() * 1024);
			} else {
				file = new File(ftpParams.getUploadLocalFile());
				is = new FileInputStream(file);
			}
			
			cFtpClient.setBufferSize(COPY_BUFFER_SIZE);
			
			// 创建目录
			if (!StringUtils.isEmpty(ftpParams.getUploadRemotePath())) {
				String[] files = ftpParams.getUploadRemotePath().split("/");
				if (files.length > 0) {
					for (int i = 1; i < files.length; i++) {
						cFtpClient.makeDirectory(files[i]);
						cFtpClient.changeWorkingDirectory(files[i]);
					}
				} else {
					cFtpClient.changeWorkingDirectory("/");
				}
			} else {
				cFtpClient.changeWorkingDirectory("/");
			}
			
			if (ftpParams.getIsUploadBySize()) {
				os = cFtpClient.storeFileStream(System.currentTimeMillis() + ".txt");
			} else {
				os = cFtpClient.storeFileStream(file.getName());
			}
			
			int bytes;
	        long total = 0l;
	        long lastTotal = 0l;
	        byte[] buffer = new byte[COPY_BUFFER_SIZE];

            while ((bytes = is.read(buffer)) != -1) {
            	
            	if(Thread.currentThread().isInterrupted()) {
            		Log.error("Ftp Upload Child Thread Interrupted.");
            		break;
            	}
            	
            	if(ftpStatThread.isEnd()) {
            		Log.error("Ftp Upload Child Thread End.");
            		break;
            	}
            	
                if (bytes == 0) {
                    bytes = is.read();
                    if (bytes < 0) {
                        break;
                    }
                    os.write(bytes);
                    os.flush();
                    ++total;
                    
                    if(total > lastTotal) {
						ftpStatThread.setLen(1);
					}
                    lastTotal = total;
                    continue;
                }

                os.write(buffer, 0, bytes);
                os.flush();
                total += bytes;
                if(total > lastTotal) {
					ftpStatThread.setLen(bytes);
				}
            }
			
		} catch (IOException e) {

			Log.error("Ftp Upload Child Thread Exception:" + Thread.currentThread().getName(), e);
			
		} finally {
			
			Log.error("Ftp Upload Child Exit.");
			
			if(cFtpClient != null && cFtpClient.isConnected()) {
				try {
					cFtpClient.disconnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if(os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				os = null;
			}
			if(is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				is = null;
			}
			
		}
	}

}
