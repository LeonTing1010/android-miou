package com.datang.miou.datastructure;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Signal implements Serializable{
	Date mTime;
	String mContent;
	
	public Signal() {
		mTime = new Date();
		mContent = "Default signal";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return mTime.toGMTString() + " : " + mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}
	
	public String getContent() {
		return mContent;
	}
}
