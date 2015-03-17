package com.datang.miou.testplan.bean;

import android.widget.EditText;

public class Voice {
	
	private String mCallNum = "10086"; //呼叫号码
    private String mTestTimes = "5";  //测试次数
    private String mTestduration = "5";  //测试时间（s）
    private String mTestInver = "5";   //测试间隔（s）
    private String mWaitTime = "5";    //超时时间（s）
    private boolean misIncoming = false;   //是否是被叫
	public boolean isMisIncoming() {
		return misIncoming;
	}
	public void setMisIncoming(boolean misIncoming) {
		this.misIncoming = misIncoming;
	}
	public String getmCallNum() {
		return mCallNum;
	}
	public void setmCallNum(String mCallNum) {
		this.mCallNum = mCallNum;
	}
	public String getmTestTimes() {
		return mTestTimes;
	}
	public void setmTestTimes(String mTestTimes) {
		this.mTestTimes = mTestTimes;
	}
	public String getmTestduration() {
		return mTestduration;
	}
	public void setmTestduration(String mTestduration) {
		this.mTestduration = mTestduration;
	}
	public String getmTestInver() {
		return mTestInver;
	}
	public void setmTestInver(String mTestInver) {
		this.mTestInver = mTestInver;
	}
	public String getmWaitTime() {
		return mWaitTime;
	}
	public void setmWaitTime(String mWaitTime) {
		this.mWaitTime = mWaitTime;
	}
}
