package com.datang.miou.datastructure;

public class TestLog {
	private String mName;
	private String mPath;
	private long mSize;
	private long mTimeCost;
	private boolean hasException;
	
	public TestLog(String name) {
		this.mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getPath() {
		return mPath;
	}

	public void setPath(String mPath) {
		this.mPath = mPath;
	}

	public long getSize() {
		return mSize;
	}

	public void setSize(long mSize) {
		this.mSize = mSize;
	}

	public long getTimeCost() {
		return mTimeCost;
	}

	public void setTimeCost(long mTimeCost) {
		this.mTimeCost = mTimeCost;
	}

	public boolean HasException() {
		return hasException;
	}

	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}
	
	
}
