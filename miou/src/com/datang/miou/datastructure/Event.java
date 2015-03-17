package com.datang.miou.datastructure;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Event implements Serializable {
	Date mTime;
	String mContent;
	String mLabel;
	
	public Event() {
		mTime = new Date();
		mContent = "Default event";
		mLabel = "";
	}
	
	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String label) {
		this.mLabel = label;
	}
	
	public void setContent(String content) {
		mContent = content;
	}
	
	public String getContent() {
		return mContent;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return mTime.toGMTString() + " : " + mContent + " " + mLabel;
	}
}
