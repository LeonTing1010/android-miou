package com.datang.miou.datastructure;

public class TestPlan {
	private int mId;
	private String mName;
	private int mTimes;
	private int mType;
	private boolean isChecked;
	private static int sCount;
	
	public static final int TEST_PLAN_TYPE_IDLE = 0;
	public static final int TEST_PLAN_TYPE_VOICE = 1;
	public static final int TEST_PLAN_TYPE_VOLTE = 2;
	public static final int TEST_PLAN_TYPE_VIDEO = 3;
	public static final int TEST_PLAN_TYPE_PING = 4;
	public static final int TEST_PLAN_TYPE_PDP = 5;
	public static final int TEST_PLAN_TYPE_ATTACH = 6;
	public static final int TEST_PLAN_TYPE_FTP = 7;
	public static final int TEST_PLAN_TYPE_MAIL_SEND = 8;
	public static final int TEST_PLAN_TYPE_MAIL_RECEIVE = 9;
	public static final int TEST_PLAN_TYPE_MAIN_SR = 10;
	public static final int TEST_PLAN_TYPE_MESSAGE_SEND = 11;
	public static final int TEST_PLAN_TYPE_MESSAGE_RECEIVE = 12;
	public static final int TEST_PLAN_TYPE_MESSAGE_SR = 13;
	public static final int TEST_PLAN_TYPE_WAP_DOWNLOAD = 14;
	public static final int TEST_PLAN_TYPE_FLOW_MEDIA = 15;
	
	public TestPlan() {
		this.mId = sCount++;
		this.isChecked = false;
		this.mName = "Default Test Plan";
	}
	
	public int getId() {
		return mId;
	}
	public void setId(int id) {
		this.mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	
	public int getTimes() {
		return mTimes;
	}
	
	public void setTimes(int times) {
		this.mTimes = times;
	}
	
	public int getType() {
		return mType;
	}
	
	public void setType(int type) {
		this.mType = type;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		this.isChecked = checked;
	}
	
	
}
