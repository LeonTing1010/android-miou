package com.datang.adc;

import java.util.Map;

/**
 * Created by dingzhongchang on 13-5-24.
 */
 public class Message implements IMsg {
    protected long length;

    protected byte[] body = new byte[0];

    protected String name="";


    @Override
    public long length() {
        return body.length;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public byte[] toBytes() {
        return body;
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}
