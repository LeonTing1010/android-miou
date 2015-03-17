package com.datang.adc.handler;


import android.util.Log;
import com.datang.client.util.SDCardUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by dingzhongchang on 13-6-28.
 */
public final class LogHelper {
    public static final int MAX = 55535;
    public static final String TAG="LogHelper";
    //    private static final Logger L = Logger.getLogger(Heart.class);
//    private static String logFilePath = SDCardUtils.getLogFilePath();
    FileChannel fc = null;
    private int seekIndex;


    public LogHelper(String fileName, int seekIndex) {
        this.seekIndex = seekIndex;
        try {
            RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
            fc = accessFile.getChannel();
            fc.position(seekIndex);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    /**
     * 读取LOG文件流
     *
     * @param fileName 文件目录
     * @param pos      读取位置
     * @param size     读取大小
     * @return 文件流
     */
    static byte[] readLog(String fileName, int pos, int size) {
        byte[] data = SDCardUtils.read(fileName, pos, size);
        if (data == null) {
            return new byte[0];
        }
        return data;
    }

    /**
     * 获取文件总长度
     *
     * @return 获取文件总长度
     */
    public static long getFileSize(String fileName) {
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(fileName, "r");
            return accessFile.length();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
        return 0;
    }

    /**
     * 获取文件总长度
     *
     * @return 获取文件总长度
     */
    public long getFileSize() {
        if (fc != null) {
            try {
                return fc.size();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return 0;
    }

    /**
     * 读取LOG文件流
     *
     * @param size 读取大小
     * @return 文件流
     */
    byte[] readBytes(int size) {
        if (size <= 0 || fc == null) {
            return new byte[0];
        }
        try {
            ByteBuffer b = ByteBuffer.allocateDirect(size);
            int length = fc.read(b);
            if (length <= 0) {
                return new byte[0];
            }
            b.flip();
            if (length < size) {
                byte[] d = new byte[length];
                b.get(d);
                return d;
            } else {
                byte[] data = new byte[size];
                b.get(data);
                return data;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
        return new byte[0];
    }

    void closeQuietly() {
        if (fc != null) {
            try {
                fc.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }
}
