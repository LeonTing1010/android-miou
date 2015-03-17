package com.datang.miou.ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import android.os.SystemClock;

/*import com.datang.outum.nlog.LogType;
import com.datang.outum.nlog.LogWriterHandler;
import com.datang.outum.nlog.msg.cmcc.LabInfo;
import com.datang.outum.nlog.msg.cmcc.LteEvtInfo;*/

/**
 * FTP公用抽象类
 * 
 * @author suntongwei
 */
public abstract class FtpHelper {

	// LOG
	private static final Logger FHLog = Logger.getLogger(FtpHelper.class);
	
	// LOG记录
	//protected LogWriterHandler LogWriter = LogWriterHandler.getInstance();
	
	// 重连次数10
	protected int reLinkCount = 5;
	
	// 是否需要暂停
	protected boolean isPause = false;
	protected boolean isWritePause = true;
	
	/**
	 * 登录FTP服务器
	 * 
	 * @throws IOException
	 * @throws SocketException
	 */
	public void login(FTPClient client, String hostname, int port, String username,
			String password) throws SocketException, IOException {
		client.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
		
		//client.connect(hostname, port);
		
		try {
		client.connect("222.68.172.16", 23);
		} catch (Exception e1) {
			
				System.out.println("aaaa");
		} finally {
			// 执行断开连接
			//disconnect(client);
		}
		client.login(username, password);
		client.setControlEncoding("GBK");
		client.setFileType(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
			FHLog.error("FtpHelper link fail.");
			disconnect(client);
			throw new IOException();
		}
		logLogin(true);
		
		reLinkCount = 10;
	}
	
	/**
	 * 登录事件
	 */
	protected void logLogin(boolean isLogin) {
		/*LteEvtInfo logInfo = new LteEvtInfo();
		if (isLogin) {
			logInfo.setTime(System.currentTimeMillis());
			logInfo.setEvent("4100");
			logInfo.setEventInfo("FTP server logon success");
		} else {
			logInfo.setTime(System.currentTimeMillis());
			logInfo.setEvent("4101");
			logInfo.setEventInfo("FTP server logon fail");
		}
		try {
			LogWriter.writeLog(LogType.CMCC, logInfo);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 返回文件长度
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getFileSize(FTPClient client, String path) throws IOException {
		FTPFile[] files = client.listFiles(path);
		if(files.length == 1) {
			long size = files[0].getSize();
			if(size > 0) {
				return size;
			} else {
				throw new IOException();
			}
		} else {
			throw new IOException();
		}
	}
	
	/**
	 * 
	 * 
	 * @param client
	 */
	protected void disconnect(FTPClient client) {
		if(client != null && client.isConnected()) {
			try {
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 暂停继续操作
	 * 
	 * @param isPause
	 */
	public void setPause(boolean isPause) {
		this.isPause = isPause;
	} 
	
	/**
	 * 记录日志FTP开始
	 */
	protected void writeTestBegin() {
		/*LabInfo sLabInfo = new LabInfo();
		sLabInfo.setTime(System.currentTimeMillis());
		sLabInfo.setLabel(LabInfo.FTP_BEGIN_CODE);
		sLabInfo.setLabelInfo(LabInfo.FTP_BEGIN_INFO);
		LogWriter.writeLog(LogType.CMCC, sLabInfo);*/
	}
	
	/**
	 * 记录日志FTP结束
	 */
	protected void writeTestEnd() {
		/*LabInfo eLabInfo = new LabInfo();
		eLabInfo.setTime(System.currentTimeMillis());
		eLabInfo.setLabel(LabInfo.FTP_END_CODE);
		eLabInfo.setLabelInfo(LabInfo.FTP_END_INFO);
		LogWriter.writeLog(LogType.CMCC, eLabInfo);*/
	}
	
	/**
	 * 记录Attempt
	 */
	protected void writeAttempt(boolean isDown) {
		/*LteEvtInfo lteEvtInfo = new LteEvtInfo();
		if(isDown) {
			lteEvtInfo.setEvent("4102");
			lteEvtInfo.setEventInfo("FTP Download Attempt");
		} else {
			lteEvtInfo.setEvent("4105");
			lteEvtInfo.setEventInfo("FTP Upload Attempt");
		}
		lteEvtInfo.setTime(System.currentTimeMillis());
		LogWriter.writeLog(LogType.CMCC, lteEvtInfo);*/
	}
	
	
	/**
	 * 记录日志FTP暂停
	 */
	protected void writeTestSuspend() {
		/*LabInfo eLabInfo = new LabInfo();
		eLabInfo.setTime(System.currentTimeMillis());
		eLabInfo.setLabel("4000");
		eLabInfo.setLabelInfo("Test Suspend");
		LogWriter.writeLog(LogType.CMCC, eLabInfo);*/
	}
	
	/**
	 * 记录日志FTP暂停
	 */
	protected void writeTestContinue() {
		/*LabInfo eLabInfo = new LabInfo();
		eLabInfo.setTime(System.currentTimeMillis());
		eLabInfo.setLabel("4001");
		eLabInfo.setLabelInfo("Test Continue");
		LogWriter.writeLog(LogType.CMCC, eLabInfo);*/
	}
	
	/**
	 * 记录FTP FAIL
	 * 
	 * @param isDown
	 */
	protected void writeTestFail(boolean isDown) {
		/*LteEvtInfo logInfo = new LteEvtInfo();
		logInfo.setTime(System.currentTimeMillis());
		if(isDown) {
			logInfo.setEvent("4013");
			logInfo.setEventInfo("Ftp Download Fail");
		} else {
			logInfo.setEvent("4106");
			logInfo.setEventInfo("FTP Upload Fail");
		}
		LogWriterHandler.getInstance().writeLog(
				LogType.CMCC, logInfo);*/
	}
	
	/**
	 * 记录FTP DROP
	 * 
	 * @param isDown
	 */
	protected void writeTestDrop(boolean isDown) {
		/*LteEvtInfo logInfo = new LteEvtInfo();
		logInfo.setTime(System.currentTimeMillis());
		if(isDown) {
			logInfo.setEvent("4108");
			logInfo.setEventInfo("FTP Download Drop");
		} else {
			logInfo.setEvent("410A");
			logInfo.setEventInfo("FTP Upload Drop");
		}
		LogWriterHandler.getInstance().writeLog(
				LogType.CMCC, logInfo);*/
	}
}
