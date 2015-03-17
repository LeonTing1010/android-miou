package com.datang.adc.util;

import java.io.*;
import java.util.zip.*;

/**
 * Created by dingzhongchang on 13-6-28.
 */
public class ZlibUtil {


    /**
     * 单文件压缩
     *
     * @param src    源文件绝对路径
     * @param dest   目标文件绝对路径
     * @param encode 编码方式
     * @throws java.io.IOException 异常
     */
    public static void gzip(String src, String dest, String encode) throws IOException {
        //做准备压缩一个字符文件，注，这里的字符文件要是GBK编码方式的
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(src), encode));
        //使用GZIPOutputStream包装OutputStream流，使其具体压缩特性
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(dest + ".gz")));
        int c;
        try {
            while ((c = in.read()) != -1) {
                out.write(String.valueOf((char) c).getBytes(encode));
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @param os   输出流
     */
    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);

        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }

    /**
     * 解压缩
     *
     * @param is 输入流
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];

            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toByteArray();
    }
}