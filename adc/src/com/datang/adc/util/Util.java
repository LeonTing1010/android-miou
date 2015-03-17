package com.datang.adc.util;

import java.util.Properties;

/**
 * Created by LEO on 14-5-7.
 */
public final class Util {
    static Properties props = new Properties();

    public static String byteReverse(String src) {
        if (src == null) {
            return "";
        }
        StringBuffer desBf = new StringBuffer();
        int length = src.length();
        char[] chars = new char[length];
        src.getChars(0, length, chars, 0);
        for (char c : chars) {
            desBf.append(Integer.toHexString(~c).substring(length - 1, length));
        }
        return desBf.toString().toUpperCase();
    }
    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty(&quot;&quot;)        = true
     * StringUtils.isEmpty(&quot; &quot;)       = false
     * StringUtils.isEmpty(&quot;bob&quot;)     = false
     * StringUtils.isEmpty(&quot;  bob  &quot;) = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in isBlank().
     * </p>
     *
     * @param cs the CharSequence to check, may be null
     * @return <code>true</code> if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    // 根据key读取value
    public static String readValue(String key) {
        return props.getProperty(key);
    }

    // 写入properties信息
    public static void writeValue(String parameterName, String parameterValue) {
        props.setProperty(parameterName, parameterValue);
    }
}
