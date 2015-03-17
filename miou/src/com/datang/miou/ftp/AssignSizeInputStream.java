package com.datang.miou.ftp;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * @author suntongwei
 */
public class AssignSizeInputStream extends InputStream {

	private int mIndex = 0;
	private long mMaxSize = 0;
	
	public AssignSizeInputStream(long maxSize) {
		mMaxSize = maxSize;
	}
	
	@Override
	public int read() throws IOException {
		if (mIndex == mMaxSize) {
            return -1;
        }
        mIndex++;
        return 0x1;
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		if(mIndex == mMaxSize) {
			return -1;
		}
		for(int i = 0; i < b.length; i++) {
			if(mIndex == mMaxSize) {
				return i + 1;
			}
			b[i] = 0x1;
			mIndex++;
		}
		return b.length;
	}
	
	@Override
	public void close() throws IOException {
		mIndex = 0;
		mMaxSize = 0;
		super.close();
	}
}
