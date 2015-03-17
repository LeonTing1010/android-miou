package com.datang.miou.ftp;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.datang.miou.testplan.bean.Ftp;
/*import com.datang.outum.nlog.LogType;
import com.datang.outum.nlog.LogWriterHandler;
import com.datang.outum.nlog.msg.cmcc.LteEvtInfo;
import com.datang.outum.nlog.msg.cmcc.LteFtpInfo;
*/
/**
 * FTP统计线程
 * 
 * @author suntongwei
 */
public class FtpStatThread implements Runnable {

	// LOG
	private static final Logger Log = Logger.getLogger(FtpStatThread.class);
	// DecimalFormat
	private static final DecimalFormat DF = new DecimalFormat("######.##");
	
	// ctx
	private Context mContext;

	// 开始时间
	private long startTime;
	// 结束时间
	private long endTime = -1l;

	// 已下载或已上传字节数
	private AtomicLong len = new AtomicLong();

	// 需要下载或上传的总字节数
	private long totalLen = 0l;
	
	// FTP参数
	private Ftp ftpParams = null;
	
	/**
	 * 
	 * @param ctx
	 */
	public FtpStatThread(Context ctx) {
		mContext = ctx;
	}

	/**
	 * 初始化统计线程
	 * 
	 * @param isDown
	 * @param startTime
	 * @param totalLen
	 */
	public void init(Ftp ftpParams, long startTime, long totalLen) {
		len.set(0l);
		endTime = -1l;
		this.ftpParams = ftpParams;
		this.startTime = startTime;
		this.totalLen = totalLen;
	}


	@Override
	public void run() {
		
		Log.error("FtpStatThread Run...");
		
		Intent intent = new Intent("com.datang.action.data");
		
		// 最大速率
		long maxSpeed = 0l;
		// 上一次速率
		long lastLen = 0l;
		
		boolean isRun = true;
		
		// 开始统计
		while(!Thread.currentThread().isInterrupted() || isRun) {
			
			/**
			 * 为了保护最后一次统计的呈现
			 * 如果下载完成后，并没有到每秒统计时间，则会丢失最后一次统计的过程
			 * 以下代码保证了当下载或上传完成时，进行最后一次统计
			 */
			if(Thread.currentThread().isInterrupted()) {
				isRun = false;
			}
			
			// 获得当前统计的结算时间
			double runTime = (System.currentTimeMillis() - startTime) / 1000;
			if(endTime > 0) {
				runTime = (endTime - startTime) / 1000;
			}
			
			// 获得当前下载总字节数
			long curTotalLen = len.get();
			// 当前每秒下载速率
			long curLen = curTotalLen - lastLen;
			
			// 如果当前速率大于最大速率，则记录该最大速率
			if(curLen > maxSpeed) {
				maxSpeed = curLen;
			}
			
			Bundle value = new Bundle();
	        
	        value.putBoolean("Ftp_Down", ftpParams.getIsDown());
	        value.putString("Ftp_Total", logFormat(curTotalLen));
	        value.putLong("Ftp_Total_Val", curTotalLen);
	        curLen = curLen > 0 ? curLen : 0;
	        value.putLong("Ftp_Current_Val", curLen);
	        value.putString("Ftp_Current", formatSpeed(curLen));
	        value.putDouble("Ftp_Max_Val", maxSpeed);
	        value.putString("Ftp_Max", formatSpeed(maxSpeed));
	        
	        if(runTime > 0) {
	        	 double avgLen = curTotalLen / runTime;
			     value.putDouble("Ftp_Avg_Val", avgLen);
			     value.putString("Ftp_Avg", formatSpeed(avgLen));
	        } else {
	        	value.putDouble("Ftp_Avg_Val", 0d);
			    value.putString("Ftp_Avg", formatSpeed(0d));
	        }
	        
	        value.putLong("Ftp_RunTime", (long) runTime);
	        
	        intent.putExtra("DATA", value);
	        mContext.sendBroadcast(intent);
	        
	        // 记录日志
	        if(curTotalLen != 0 || runTime > 1) {
	        	/*LteFtpInfo lteFtpInfo = new LteFtpInfo();
	 			lteFtpInfo.setTime(System.currentTimeMillis());
	 			if(ftpParams.getIsDown()) {
	 				lteFtpInfo.setApp_currentThroughputdl(DF.format((curTotalLen - lastLen) * 8 / 1024));
	 				lteFtpInfo.setAppBytesReceivedLTE(String.valueOf(curTotalLen / 1024));
	 				lteFtpInfo.setAppBytesReceivedTotal(String.valueOf(curTotalLen / 1024));
	 			} else {
	 				lteFtpInfo.setApp_currentThroughputul(DF.format((curTotalLen - lastLen) * 8 / 1024));
	 				lteFtpInfo.setAppBytesSendLTE(String.valueOf(curTotalLen / 1024));
	 				lteFtpInfo.setAppBytesSendTotal(String.valueOf(curTotalLen / 1024));
	 			}
	 			LogWriterHandler.getInstance().writeLog(LogType.CMCC,lteFtpInfo);*/
	        }
	        
	        // 把当前总长度赋予上一次总长度，来统计当前速率
	        lastLen = curTotalLen;
	        
	        SystemClock.sleep(1000);
		}
		
		// 如果下载或上传成功则记录成功事件
		if(isEnd()) {
			// 写入最后次统计信息
			// LTEEVT   FTP_(totalLen)
			/*LteEvtInfo stopLteEvtInfo = new LteEvtInfo();
			stopLteEvtInfo.setTime(System.currentTimeMillis());
			if(ftpParams.getIsDown()) {
				stopLteEvtInfo.setEvent("4104");
			} else {
				stopLteEvtInfo.setEvent("4107");
			}
			stopLteEvtInfo.setEventInfo("FTP");
			stopLteEvtInfo.setFileSize(String.valueOf(len.get()));
			LogWriterHandler.getInstance().writeLog(LogType.CMCC, stopLteEvtInfo);
			
			if(len.get() > 0) {
				LteFtpInfo lteFtpInfo = new LteFtpInfo();
				lteFtpInfo.setTime(System.currentTimeMillis());
				if(ftpParams.getIsDown()) {
					lteFtpInfo.setAppBytesReceivedLTE(String.valueOf(len.get() / 1024));
					lteFtpInfo.setAppBytesReceivedTotal(String.valueOf(len.get() / 1024));
				} else {
					lteFtpInfo.setAppBytesSendLTE(String.valueOf(len.get() / 1024));
					lteFtpInfo.setAppBytesSendTotal(String.valueOf(len.get() / 1024));
				}
				LogWriterHandler.getInstance().writeLog(LogType.CMCC,lteFtpInfo);
			}*/
		}
		
		Log.error("Ftp Stat Stop");
	}

	/**
	 * 返回总长度
	 * 
	 * @return
	 */
	public long getLen() {
		return len.get();
	}

	/**
	 * 设置总长度，增加长度保护措施，当处理的总字节数超过了需要下载或上传的总字节数
	 * 
	 * @param l
	 * @return
	 */
	public void setLen(long l) {
		if(len.get() < totalLen) {
			len.addAndGet(l);
		} else {
			len.set(totalLen);
		}
	}

	/**
	 * 主要判断是否下载结束
	 * 如果下载结束了，并设置当前结束时间
	 * 
	 * @return
	 */
	public boolean isEnd() {
		if(len.get() >= totalLen) {
			endTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	public void setTotalLen(long len) {
		totalLen = len;
	}
	
	private String formatSpeed(double speed) {
		DecimalFormat df = null;
		double kbps = (speed * 8) / 1024;
		if(kbps > 1024) {
			df = new DecimalFormat("######.##");
			return df.format(kbps / 1024) + "Mbps";
		}
		df = new DecimalFormat("######");
		return df.format(kbps) + "Kbps";
	}
	
	public String logFormat(long total) {
        DecimalFormat df = null;
        double kbps = total / 1024;
        if (kbps > 1024) {
            df = new DecimalFormat("#########.##");
            return df.format(kbps / 1024) + "MB";
        }
        df = new DecimalFormat("#########");
        return df.format(kbps) + "KB";
    }
}
