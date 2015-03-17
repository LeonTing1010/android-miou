package com.datang.miou.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * SD卡读取帮助类
 * <p/>
 * 项目目录规划 |IADC 根目录 |--- system 系统目录 |--- map 系统存放地图目录 |--- map 地图存放目录 |--- log
 * LOG日志存放目录
 *
 * @author suntongwei
 */
public class SDCardUtils {

    /**
     * SD卡项目顶级路径
     */
    private static final String PROJECT_FILE_PATH = "/MIOU";

    /**
     * 系统子目录
     */
    private static final String SYSTEM_PATH = "/system";
    /**
     * 配置文件路径
     */
    private static final String CONFIG = "/config";
    
    /**
     * 系统LOG目录
     */
    private static final String SYSTEM_LOG = "/log";
    
    /**
     * LOG文件子路径
     */
    private static final String LOG_FILE = "/logfile";
    private static final String LOG_DEL_FILE = "/delfile";
    private static final String LOG_SSV_FILE = "/ssv";
    /**
     * 测试子目录
     */
    private static final String DEBUG_PATH = "/debug";
    private static final String LOG_PROJECT_FILE = "/project";



    public static final String TAG = "SDCardUtils";

    /**
     * 自动创建文件系统
     */
    static {
        if (getSDCardExist()) {
            String[] createFileNames = new String[]{
                    PROJECT_FILE_PATH,
                    PROJECT_FILE_PATH + CONFIG,
                    PROJECT_FILE_PATH + SYSTEM_PATH,
                    PROJECT_FILE_PATH + SYSTEM_LOG,
                    PROJECT_FILE_PATH + LOG_FILE,
                    PROJECT_FILE_PATH + LOG_DEL_FILE,
                    PROJECT_FILE_PATH + LOG_PROJECT_FILE,
                    PROJECT_FILE_PATH + DEBUG_PATH
                  };
            for (String filePath : createFileNames) {
                File file = new File(getSDPath() + filePath);
                if (!file.exists()) {
                    Log.d(TAG, "创建目录:" + filePath);
                    file.mkdir();
                }
            }
        }
    }

    /**
     * 返回工程目录路径
     *
     * @return
     */
    public static String getProjectPath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + LOG_PROJECT_FILE;
        }
        return null;
    }/**
     * 判断SD卡是否存在
     *
     * @return true存在, false不存在
     */
    public static boolean getSDCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回SD卡根路径
     *
     * @return
     */
    public static String getSDPath() {
        if (getSDCardExist()) {
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        }
        return null;
    }

    /**
     * 返回项目文件系统路径
     *
     * @return
     */
    public static String getProjectFilePath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH;
        }
        return null;
    }

    /**
     * 返回项DEBUG文件系统路径
     *
     * @return
     */
    public static String getDebugPath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + DEBUG_PATH;
        }
        return null;
    }
    
    /**
     * 返回系统LOG文件目录
     * 
     * @return
     */
    public static String getSystemLogPath() {
    	 if (getSDCardExist()) {
             return getSDPath() + PROJECT_FILE_PATH + SYSTEM_LOG;
         }
         return null;
    }

    /**
     * 返回LOG日志文件路径
     *
     * @return
     */
    public static String getLogFilePath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + LOG_FILE;
        }
        return null;
    }

    /**
     * 返回SSV LOG日志文件路径
     *
     * @return
     */
    public static String getSSVLogFilePath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + LOG_SSV_FILE;
        }
        return null;
    }

    /**
     * 返回LOG日志文件路径
     *
     * @return
     */
    public static String getDelFilePath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + LOG_DEL_FILE;
        }
        return null;
    }

    /**
     * 返回配置文件路径
     *
     * @return
     */
    public static String getConfigPath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + CONFIG;
        }
        return null;
    }


    /**
     * 返回系统平面图路径
     *
     * @return
     */
    public static String getSystemPath() {
        if (getSDCardExist()) {
            return getSDPath() + PROJECT_FILE_PATH + SYSTEM_PATH;
        }
        return null;
    }

    /**
     * 创建文件(已经存在不会重复创建)
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return 文件
     */
    public static File createFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + File.separator + fileName);
    }

    /**
     * copy
     *
     * @param src
     * @param dest
     * @return
     */
    public static void copy(File src, File dest) {
        int length = 2097152;
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            FileChannel inC = in.getChannel();
            FileChannel outC = out.getChannel();
            ByteBuffer b = null;
            while (true) {
                if (inC.position() == inC.size()) {
                    inC.close();
                    outC.close();
                    return;
                }
                if ((inC.size() - inC.position()) < length) {
                    length = (int) (inC.size() - inC.position());
                } else
                    length = 2097152;
                b = ByteBuffer.allocateDirect(length);
                inC.read(b);
                b.flip();
                outC.write(b);
                outC.force(false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读文件
     */
    public static byte[] read(String path) {
        byte[] data = null;
        InputStream is = null;
        try {
            if (State.W == isExternalStorageAvailable()) {
                File srcFile = new File(path);
                is = new FileInputStream(srcFile);
                data = new byte[is.available()];
                is.read(data);

            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return data;
    }

    /**
     * 读文件
     */
    public static byte[] read(String path, int pos, int size) {

        FileChannel fc = null;
        try {
            if (State.W == isExternalStorageAvailable()) {
                ByteBuffer b = ByteBuffer.allocateDirect(size);
                RandomAccessFile accessFile = new RandomAccessFile(path, "r");

                fc = accessFile.getChannel();
                fc.position(pos);
                int length = fc.read(b);
                if (length <= 0) {
                    return null;
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
            }
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage(), ex);
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 写文件
     */

    public static void write(String desPath, String fileName, byte[] data) {
        OutputStream os = null;
        try {
            if (State.W == isExternalStorageAvailable()) {
                File desFile = new File(desPath);
                if (!desFile.exists()) {
                    desFile.mkdirs();
                }
                File file = new File(desFile + File.separator + fileName);
                os = new FileOutputStream(file);
                os.write(data);
            }
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage(), ex);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 写文件
     */

    public static void writeReadOnly(String desPath, String fileName, byte[] data) {
        OutputStream os = null;
        try {
            if (State.W == isExternalStorageAvailable()) {
                File desFile = new File(desPath);
                if (!desFile.exists()) {
                    desFile.mkdirs();
                }
                File file = new File(desFile + File.separator + fileName);
                file.setReadOnly();
                os = new FileOutputStream(file);
                os.write(data);
            }
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage(), ex);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 保存图片
     *
     * @param fileName
     * @param bmp
     */
    public static void writeBmp(String fileName, Bitmap bmp) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);

        } catch (FileNotFoundException e) {
            Log.e(TAG,"保存图片失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG,e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 删除文件夹或者文件
     *
     * @param folderPath String 文件夹路径或者文件的绝对路径 如：/mnt/sdcard/test/1.png
     */
    public static void delete(String folderPath) {
        try {
            // 删除文件夹里所有的文件及文件夹
            deleteAllFile(folderPath);
            File lastFile = new File(folderPath);
            if (lastFile.exists()) {
                // 最后删除空文件夹
                lastFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径或者文件的绝对路径 如：/mnt/sdcard/test/1.png
     */
    private static void deleteAllFile(String path) {
        // 在内存开辟一个文件空间，但是没有创建
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    // 先删除文件夹里面的文件
                    deleteAllFile(path + "/" + tempList[i]);
                    // 再删除空文件夹
                    delete(path + "/" + tempList[i]);
                }
            }
        }
    }

    /**
     * 文件是否存在
     */
    public static boolean hasFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    // public static void delete(String path) {
    // String fileName = path.substring(path.lastIndexOf(File.separator),
    // path.length());
    // String filePath = path.substring(path.lastIndexOf(File.separator));
    // File file = new File(filePath, fileName);
    // file.delete();
    // }

    /**
     * ExternalStorage 是否可用
     *
     * @return 可用状态
     */
    public static State isExternalStorageAvailable() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if (mExternalStorageAvailable && mExternalStorageWriteable) {
            return State.W;
        } else if (mExternalStorageAvailable && !mExternalStorageWriteable) {
            return State.R;
        } else if (!mExternalStorageAvailable && !mExternalStorageWriteable) {
            return State.N;
        }
        return State.U;
    }

    /**
     * 解压缩功能. 将zipFile文件解压到folderPath目录下.
     *
     * @throws Exception
     */
    public static void upZipFile(File zipFile, String folderPath)
            throws Exception {
        // public static void upZipFile() throws Exception{
        try {
            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                // if (ze.isDirectory()) {
                // String dirstr = folderPath + ze.getName();
                // // dirstr.trim();
                // dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                // File f = new File(dirstr);
                // f.mkdir();
                // continue;
                // }
                OutputStream os = new BufferedOutputStream(
                        new FileOutputStream(folderPath + "/" + ze.getName()));
                InputStream is = new BufferedInputStream(
                        zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
            }
            zfile.close();
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    // substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                // substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            return ret;
        }
        return ret;
    }

    /**
     * 获取SD剩余空间大小
     *
     * @return 剩余空间大小
     */
    public static int readAvSize() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            return (int) (availCount * blockSize / 1024 / 1024);//MB
        }
        return 0;
    }

    /**
     * <p/>
     *
     * @param file    待压缩文件的名称 例如,src/zip/文件1.txt
     * @param zipFile 压缩后文件的名称 例如,src/zip/单个文件压缩.zip
     * @return boolean
     * @throws :IOException
     * @Function: zipSingleFile
     * @Description:单个文件的压缩 </p>
     */
    public static boolean zipSingleFile(String file, String zipFile)
            throws IOException {
        boolean bf = true;
        File f = new File(file);
        if (!f.exists()) {
            bf = false;
        } else {
            File ff = new File(zipFile);
            if (!f.exists()) {
                ff.createNewFile();
            }
            // 创建文件输入流对象
            FileInputStream in = new FileInputStream(file);
            // 创建文件输出流对象
            FileOutputStream out = new FileOutputStream(zipFile);
            // 创建ZIP数据输出流对象
            ZipOutputStream zipOut = new ZipOutputStream(out);
            // 得到文件名称
            String fileName = file.substring(file.lastIndexOf('/') + 1, file.length());
            // 创建指向压缩原始文件的入口
            ZipEntry entry = new ZipEntry(fileName);
            zipOut.putNextEntry(entry);
            // 向压缩文件中输出数据
            int number = 0;
            byte[] buffer = new byte[512];
            while ((number = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, number);
            }
            zipOut.close();
            out.close();
            in.close();
        }
        return bf;
    }

    /**
     * <p/>
     *
     * @param files   待压缩的文件列表 例如,src/zip/文件1.txt, src/zip/file2.txt
     * @param zipfile 压缩后的文件名称 例如,src/zip/多个文件压缩.zip
     * @return boolean
     * @throws :Exception
     * @Function: zipFiles
     * @Description:多个文件的ZIP压缩 </p>
     */
    public static boolean zipFiles(String[] files, String zipfile)
            throws Exception {
        boolean bf = true;

        // 根据文件路径构造一个文件实例
        File ff = new File(zipfile);
        // 判断目前文件是否存在,如果不存在,则新建一个
        if (!ff.exists()) {
            ff.createNewFile();
        }
        // 根据文件路径构造一个文件输出流
        FileOutputStream out = new FileOutputStream(zipfile);
        // 传入文件输出流对象,创建ZIP数据输出流对象
        ZipOutputStream zipOut = new ZipOutputStream(out);

        // 循环待压缩的文件列表
        for (int i = 0; i < files.length; i++) {
            File f = new File(files[i]);
            if (!f.exists()) {
                bf = false;
            }
            try {
                // 创建文件输入流对象
                FileInputStream in = new FileInputStream(files[i]);
                // 得到当前文件的文件名称
                String fileName = files[i].substring(
                        files[i].lastIndexOf('/') + 1, files[i].length());
                // 创建指向压缩原始文件的入口
                ZipEntry entry = new ZipEntry(fileName);
                zipOut.putNextEntry(entry);
                // 向压缩文件中输出数据
                int nNumber = 0;
                byte[] buffer = new byte[512];
                while ((nNumber = in.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, nNumber);
                }
                // 关闭创建的流对象
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                bf = false;
            }
        }
        zipOut.close();
        out.close();
        return bf;
    }

    /**
     * 可用狀態
     *
     * @author dingzhongchang
     * @version 1.0.0, 2012-1-12
     */
    enum State {
        /**
         * 可读可写
         */
        W,
        /**
         * 只读
         */
        R,

        /**
         * 不可用
         */
        N,
        /**
         * 未知
         */
        U
    }

}
