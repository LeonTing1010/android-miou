package com.datang.adc;

import java.util.LinkedHashMap;

/**
 * Created by dingzhongchang on 13-5-24.
 */
public class UpMsg extends Message {

//    private static final Logger L = Logger.getLogger(UpMsg.class);

    /**
     * 构造发送消息
     *
     * @param name   消息名字
     * @param msgMap 消息列表 Command=Login
     */
    public UpMsg(String type, String name, LinkedHashMap<String, String> msgMap) {
        this.name = name;
        if (msgMap==null||msgMap.isEmpty()) {//纯数据包
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (type!=null&&!type.isEmpty()) {
            builder.append("[");
            builder.append(type);
            builder.append("]");
            builder.append(B);
        }
        for (String key : msgMap.keySet()) {
            if (key==null||key.isEmpty()) {
                continue;
            }
            builder.append(key + "=" + msgMap.get(key));
            builder.append(B);
        }

        builder.append(B);
        body = builder.toString().getBytes();
//        L.warn("ENCODE"+builder.toString());
    }

}
