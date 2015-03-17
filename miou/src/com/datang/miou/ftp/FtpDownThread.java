package com.datang.miou.ftp;

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
 * FTP下载主线程
 * 
 * @author suntongwei
 */
public class FtpDownThread extends FtpHelper implements Runnable {
	
	// LOG
	private static Logger Log = Logger.getLogger(FtpDownThread.class);

	// CTX
	private Context mContext;
	// FTP参数集合
	private Ftp ftpParams = null;
	// 已执行次数
	private int curNum = 0;
	
	// 每次下载的开始时间
	private long startTime = 0l;
	
	// 无速率时间
	private long notChangeLenTime = 0l;
	
	/**
	 * 创建下载子线程池
	 */
	private ExecutorService executorServices = Executors.newCachedThreadPool();
	private List<Future<?>> mFutureList = new ArrayList<Future<?>>();
	
	// 统计线程
	private FtpStatThread statThread = null;
	private ExecutorService statExec = Executors.newSingleThreadExecutor();
	private Future<?> curStatFuture = null;
	
	/**
	 * 
	 * @param ctx
	 * @param ftp
	 */
	public FtpDownThread(Context ctx, Ftp ftp) {
		
		// ctx
		mContext = ctx;
		
		// 参数
		ftpParams = ftp;
		
		// 初始化统计线程
		statThread = new FtpStatThread(mContext);
	}
	
	@Override
	public void run() {
		
		Log.error("FtpDownThread Run...");
		
		// 创建一个FTP连接
		FTPClient client = new FTPClient();
		
		// 记录FTP开始
		writeTestBegin();
		
		/**
		 * 循环执行下载
		 */
		while (curNum < ftpParams.getNum()) {
			
			Log.error("FtpDownNum: " + curNum);
			
			/**
			 * 判断线程是否还在执行
			 * 停止子线程
			 */
			if(Thread.currentThread().isInterrupted()) {
				// 终止子线程，并且终止当前线程
				stopChildThread();
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
			
			// 获取所下载的文件
			long totalLen = 0l;
			try {
				
				Log.error(ftpParams.getHostname() + "," + ftpParams.getPort() 
						+ "," + ftpParams.getUsername() + "," + ftpParams.getPassword());
				
				// 登录,如果不成功，则启用重连，重连次数为10
				login(client, ftpParams.getHostname(), ftpParams.getPort(),
						ftpParams.getUsername(), ftpParams.getPassword());
				
				Log.error("FtpDownLogin");
				
				// 记录Attempt时间
				// 在FTP登录成功之后
				writeAttempt(true);
				
				// 获取文件总长度
				totalLen = getFileSize(client, ftpParams.getDownFilePath());
				Log.error("FtpDownTotalLen:" + totalLen);
				
			} catch (IOException e1) {
				
				Log.error("Ftp Restart Link Server.", e1);
				
				// 记录连接失败LOG，只显示一次连接失败
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
			
			// 启动统计线程
			if(curStatFuture != null) {
				curStatFuture.cancel(true);
			}
			statThread.init(ftpParams, startTime, totalLen);
			curStatFuture = statExec.submit(statThread);
			
			/**
			 * 开启子线程来进行下载
			 * 采用极速模式，无视文件分段
			 */
			for(int i = 0; i < ftpParams.getThreadNum(); i++) {
				mFutureList.add(
						executorServices.submit(new FtpDownChildThread(ftpParams, statThread)));
			}
			
			/**
			 * 等待子线程下载结束
			 */
			long lastLen = 0l;
			while(!Thread.currentThread().isInterrupted()) {
				
				// 如果根据时间下载，则当下载时长大于需要的下载时间，则退出循环
				if(ftpParams.isDownByTime() 
						&& System.currentTimeMillis() - startTime > (ftpParams.getDownTime() * 1000)) {
					statThread.setTotalLen(statThread.getLen());
					break;
				}
				// 当下载结束则退出等待
				if(!ftpParams.isDownByTime() && statThread.isEnd()) {
					Log.error("Ftp Down isEnd = true.");
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
							Log.error("Ftp Down Time out.");
							break;
						}
					}
				} else {
					notChangeLenTime = 0;
				}
				// 记录长度
				lastLen = curLen;
				
				SystemClock.sleep(1000);
			}
			
			Log.error("FtpDownEnd");
			
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
