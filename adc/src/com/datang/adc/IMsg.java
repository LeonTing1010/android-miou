package com.datang.adc;

import java.util.Map;

/**
 * Created by dingzhongchang on 13-5-24.
 */
public interface IMsg {
    final String B = "\r\n";
    final byte[]  S= "\r\n\r\n".getBytes();
    final String CMD = "command";

    long length();

    String name();

    byte[] toBytes();

    Map<String, Object> toMap();
}
