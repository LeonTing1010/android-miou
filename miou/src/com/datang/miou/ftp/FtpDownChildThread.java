package com.datang.miou.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.datang.miou.testplan.bean.Ftp;

/**
 * FTP多线程下载子线程类
 * 
 * @author suntongwei
 */
public class FtpDownChildThread implements Runnable {

	// LOG
	private static final Logger Log = Logger.getLogger(FtpDownChildThread.class);
	
	// 每个子线程FTP_CLIENT
	private FTPClient cFtpClient = new FTPClient();

	// 缓冲区大小
	private final int ByteSize = 1048576;
	
	// FTP参数集合
	private Ftp ftpParams = null;
	
	// 统计线程
	private FtpStatThread ftpStatThread;
	
	public FtpDownChildThread(Ftp ftpParams, FtpStatThread ftpStatThrea) {
		this.ftpParams = ftpParams;
		this.ftpStatThread = ftpStatThrea;
	}
	
	
	@Override
	public void run() {
		
		Log.error(Thread.currentThread().getName());
		
		InputStream in = null;
		
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
				Log.error("Ftp Down Child Login Fail.");
				Thread.interrupted();
				return;
			}
			
			// 设置缓冲区大小
			cFtpClient.setBufferSize(ByteSize);
			
			in = cFtpClient.retrieveFileStream(ftpParams.getDownFilePath());
			byte[] bytes = new byte[ByteSize];
			int c;
			while ((c = in.read(bytes)) != -1) {
				
				// 判断线程是否被终止
				if(Thread.currentThread().isInterrupted()) {
					Log.error("Ftp Down Child Thread is Interrupted.");
					break;
				}
				
				if(ftpStatThread.isEnd()) {
            		Log.error("Ftp Down Child Thread End.");
            		break;
            	}
				
				if(ftpStatThread != null) {
					ftpStatThread.setLen(c);
				}
			}
			
		} catch (IOException e) {
			
			Log.error("Ftp Down Child Thread Exception:" + Thread.currentThread().getName(), e);
			
		} finally {
			
			Log.error("Ftp Down Child Exit.");
			
			if(cFtpClient != null && cFtpClient.isConnected()) {
				try {
					Log.error("Ftp Down Child Ftp LogOut");
					cFtpClient.disconnect();
				} catch (IOException e) {
					Log.error("Ftp Down Child Ftp LogOut Exception.", e);
				}
			}
			
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
	}
}
