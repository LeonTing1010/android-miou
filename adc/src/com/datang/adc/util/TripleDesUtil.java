package com.datang.adc.util;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

/**
 * Created by dingzhongchang on 13-6-26.
 */
public class TripleDesUtil {

    //    private static final Logger LOGGER = Logger.getLogger(TripleDesUtil.class);
    private static final String Algorithm = "DESede";
    public static final String TAG="TripleDesUtil";

    /**
     * 解密
     * @param srcBytes
     * @return
     */
    public static byte[] decryptByte(byte[] keybyte,byte[] srcBytes) throws Exception{
//		try {
        //生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

        //解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(srcBytes);
//        } catch (java.security.NoSuchAlgorithmException e1) {
//        	LOGGER.error(e1.getMessage(),e1);
//        } catch (javax.crypto.NoSuchPaddingException e2) {
//        	LOGGER.error(e2.getMessage(),e2);
//        } catch (java.lang.Exception e3) {
//        	LOGGER.error(e3.getMessage(),e3);
//        }
//        return null;
    }

    /**
     * 加密
     * @param srcBytes
     * @return
     */
    public static byte[] encryptByte(byte[] keybyte,byte[] srcBytes){
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(srcBytes);
        } catch (java.security.NoSuchAlgorithmException e1) {
            Log.e(TAG, e1.getMessage(), e1);
        } catch (javax.crypto.NoSuchPaddingException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        } catch (Exception e3) {
            Log.e(TAG, e3.getMessage(), e3);
        }
        return null;
    }

    /**
     * 获取128位随机的Triple-DES密钥字符串，例如：
     * E7AA083BBEA64506AF2D75A88C568099
     * 其中前16位为k1和k3（共16*4=64bit），后16位为k2（共16*4=64bit）
     * @return  产生的密钥字符串
     */
    public static String generateTripleDesKey(){
        String uuid = UUID.randomUUID().toString();
        return uuid.toUpperCase().replaceAll("-", "");
    }

    /**
     * 根据k1+k2(k1=k3)获取完整的k1+k2+k3密钥字符串，例如：
     * k1k2Tok1k2k3(E7AA083BBEA64506AF2D75A88C568099)=E7AA083BBEA64506AF2D75A88C568099E7AA083BBEA64506
     * @param srcK1K2HexStr  k1+k2的密钥
     * @return  在尾部加上k3的完整的k1+k2+k3的Triple-DES密钥
     */
    public static String k1k2Tok1k2k3(String srcK1K2HexStr){
        int kLength = srcK1K2HexStr.length()/2;
        String k3EqualsK1 = srcK1K2HexStr.substring(0, kLength);
        return srcK1K2HexStr + k3EqualsK1;
    }

    /**
     * 将k1k2k1组成的字符串转化为k1k2组成的字符串，和k1k2Tok1k2k3相反，例如：
     * k1k2k12k1k2(E7AA083BBEA64506AF2D75A88C568099E7AA083BBEA64506)=E7AA083BBEA64506AF2D75A88C568099
     * @param k1k2k1
     * @return
     */
    public static String k1k2k1Tok1k2(String k1k2k1){
        int span = k1k2k1.length()/3;
        return k1k2k1.substring(0, span*2);
    }

    /**
     *
     * @param hexStr
     * @return
     */
    public static byte[] keyString2Byte(String hexStr) {
        return hexString2Bytes(hexStr);
    }

    /**
     * 根据随即生成的密钥字符串得到字节数组
     * @return
     */
    public static byte[] getTripleDesKeyByteFromKeyStr(String keyStr){
        return TripleDesUtil.keyString2Byte(TripleDesUtil.k1k2Tok1k2k3(keyStr));
    }

    /**
     * 根据字节数组得到密钥
     * @param keybyte
     * @return
     */
    public static String getKeyStrFromKeyByte(byte[] keybyte){
        return TripleDesUtil.k1k2k1Tok1k2(TripleDesUtil.byte2HexString(keybyte));
    }

    /**
     *
     * @param hexstr
     * @return
     */
    public static byte[] hexString2Bytes(String hexstr) {
        byte[] arrB = hexstr.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     *
     * @param src
     * @return
     */
    public static String byte2HexString(byte[] src) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < src.length; i++) {
            String s = (String.format("%x", (int) src[i] & 0xFF)).toUpperCase();
            if (s.length() < 2) {
                sb.append("0");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String str = "E7AA083BBEA64506AF2D75A88C568099";
        byte[] src = keyString2Byte(k1k2Tok1k2k3(str));
        String result = byte2HexString(src);
        System.out.println(k1k2k1Tok1k2(result));

    }

}
