package com.datang.miou.ftp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import android.content.Context;
import android.os.SystemClock;

import com.datang.miou.testplan.bean.Ftp;

/**
 * FTP上传线程
 * 
 * @author suntongwei
 */
public class FtpUploadThread extends FtpHelper implements Runnable {

	// LOG
	private static final Logger Log = Logger.getLogger(FtpUploadThread.class);
	
	// FTP参数
	private Ftp ftpParams = null;
	
	// 已执行次数
	private int curNum = 0;
	// 每次下载的开始时间
	private long startTime = 0l;
	
	/**
	 * 创建上传子线程池
	 */
	private ExecutorService executorServices = Executors.newCachedThreadPool();
	private List<Future<?>> mFutureList = new ArrayList<Future<?>>();
	
	// 统计线程
	private FtpStatThread statThread = null;
	private ExecutorService statExec = Executors.newSingleThreadExecutor();
	private Future<?> curStatFuture = null;
	
	// 无速率时间
	private long notChangeLenTime = 0l;
	
	/**
	 * 构造方法
	 * 
	 * @param ctx
	 * @param ftp
	 */
	public FtpUploadThread(Context ctx, Ftp ftp) {
		
		// 参数
		ftpParams = ftp;
		
		// 初始化统计线程
		statThread = new FtpStatThread(ctx);
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		Log.error("FtpUploadThread Run...");
		
		// 创建一个FTP连接
		FTPClient client = new FTPClient();
		
		// 记录FTP开始
		writeTestBegin();
		
		/**
		 * 循环执行下载
		 */
		while (curNum < ftpParams.getNum()) {
			
			Log.error("FtpUploadNum: " + curNum);
			
			/**
			 * 判断线程是否还在执行
			 * 停止子线程
			 */
			if(Thread.currentThread().isInterrupted()) {
				// 终止子线程，并且终止当前线程
				stopChildThread();
				// 跳出循环退出线程
				break;
			}
			
			/**
			 * 判断线程是否需要暂停
			 * 对循环进行continue操作，以判断是否还需要执行当前线程
			 */
			if(isPause) {
				// 当isWritePause = true时，写入暂停日志
				// 并且赋值为 false，准备继续业务时记录继续日志
				if(isWritePause) {
					isWritePause = !isWritePause;
					writeTestSuspend();
				}
				SystemClock.sleep(1000);
				continue;
			}
			
			// 当已写入暂停日志时
			if(!isWritePause) {
				// 记录继续执行日志
				isWritePause = !isWritePause;
				writeTestContinue();
			}
			
			// 记录每次下载的开始时间
			startTime = System.currentTimeMillis();
			
			try {
				
				// 登录,如果不成功，则启用重连，重连次数为10
				login(client, ftpParams.getHostname(), ftpParams.getPort(),
						ftpParams.getUsername(), ftpParams.getPassword());
				
				writeAttempt(false);
				
			} catch (IOException e) {
				
				Log.error("Ftp Restart Link Server.", e);
				
				// 记录连接失败LOG
				if(reLinkCount == 5) {
					logLogin(false);
				}
				
				// 如果在执行获取文件发生异常时
				// 如果没有重连次数，则终止线程
				if(reLinkCount != 0) {
					// 继续执行线程
					reLinkCount--;
					// 每5秒重连1次
					SystemClock.sleep(5000);
				} else {
					reLinkCount = 5;
					curNum++;
				}
				// 执行下一次测试
				continue;
			} finally {
				// 执行断开连接
				disconnect(client);
			}
			
			// 增加已执行次数
			curNum++;
			
			Log.error("FtpCurrentNum: " + curNum);
			
			// 获取文件大小
			long totalSize = 0;
			if(ftpParams.getIsUploadBySize()) {
				totalSize = ftpParams.getUploadFileSize() * 1024l;
			} else {
				File file = new File(ftpParams.getUploadLocalFile());
				totalSize = file.length();
			}
			
			Log.error("Ftp Upload File Size: " + totalSize);
			
			// 启动统计线程
			if(curStatFuture != null) {
				curStatFuture.cancel(true);
			}
			statThread.init(ftpParams, startTime, totalSize);
			curStatFuture = statExec.submit(statThread);
			
			/**
			 * 开启子线程来进行上传
			 */
			for(int i = 0; i < ftpParams.getThreadNum(); i++) {
				mFutureList.add(
						executorServices.submit(new FtpUploadChildThread(ftpParams, statThread)));
			}
			
			/**
			 * 等待子线程下载结束
			 */
			long lastLen = 0l;
			while(!Thread.currentThread().isInterrupted()) {
				
				// 判断是否上传完成
				if(statThread.isEnd()) {
					break;
				}
				
				// 当前下载速率
				long curLen = statThread.getLen();
				// 判断速率是否发生变化
				if(curLen == lastLen) {
					// 把开始无变化记录时间
					if(notChangeLenTime == 0) {
						notChangeLenTime = System.currentTimeMillis();
					} else {
						// 如果无速率时间超过超时时间
						if(System.currentTimeMillis() - notChangeLenTime > ftpParams.getTimeout() * 1000) {
							// 如果速率为0, Fail
							if(curLen == 0) {
								writeTestFail(ftpParams.isDown());
							} else {
								writeTestDrop(ftpParams.isDown());
							}
							Log.error("Ftp Upload Time out.");
							break;
						}
					}
				} else {
					notChangeLenTime = 0;
				}
				// 记录长度
				lastLen = curLen;
				
				// 每秒执行一次
				SystemClock.sleep(1000);
			}
			
			Log.error("FtpUploadEnd");
			
			// 终止子线程
			stopChildThread();
			
			// 退出统计线程
			if(curStatFuture != null) {
				curStatFuture.cancel(true);
			}
			
			// 进入间隔时间等待
			if(!Thread.currentThread().isInterrupted()) {
				SystemClock.sleep(ftpParams.getInterval() * 1000);
			}
		}
		
		Log.error("FtpDownStop");
		
		// 记录FTP结束
		writeTestEnd();
		
		// 终止线程
		Thread.interrupted();
	}

	/**
	 * 终止子线程
	 */
	private void stopChildThread() {
		Log.error("Ftp Child Stop...");
		if(mFutureList.size() > 0) {
			for(Future<?> f : mFutureList) {
				f.cancel(true);
			}
		}
	}
	
	/**
	 * 返回执行次数
	 * 
	 * @return
	 */
	public int getExecNum() {
		return curNum;
	}
}
