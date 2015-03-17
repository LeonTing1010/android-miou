package com.datang.adc;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingzhongchang on 13-5-24.
 */
public class DownMsg extends Message {
//    private static final Logger L = Logger.getLogger(DownMsg.class);
    //关键字全部是小写
    private Map<String, Object> map = new HashMap<String, Object>();

    public DownMsg(byte[] msgBytes) {
        if (msgBytes == null || msgBytes.length == 0) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.wrap(msgBytes);
        //length
        int index = 0;
        boolean isS = false;
        while (buffer.remaining() > 4) {
            if (S[0] == buffer.get(index) && S[1] == buffer.get(index + 1) && S[2] == buffer.get(index + 2) && S[3] == buffer.get(index + 3)) {
                isS = true;
                break;
            }
            index++;
        }

        if (isS) {
            //指令
            byte[] cmdBytes = new byte[index];
            buffer.get(cmdBytes);
            String command = new String(cmdBytes);
//            L.warn("DECODE "+command);
            if (command==null||command.isEmpty()) {//纯数据包
                //TODO
            } else {//指令包
                String[] cs = command.split(B);

                for (String c : cs) {
                    if (c==null||c.isEmpty()) {
                        continue;
                    }
                    if (!c.contains("=")) {
                        map.put("type", c);
                    } else {
                        String[] entry = c.split("=");
                        if(entry != null && entry.length == 2) {
                        	map.put(entry[0].toLowerCase(), entry[1]);
                        }
                    }

                }
                this.name = map.get(CMD).toString();
            }
            //删除分隔符
            buffer.get(new byte[S.length]);

            if (buffer.remaining() > 0) {
                //数据
                byte[] dataBytes = new byte[buffer.remaining()];
                buffer.get(dataBytes);
                map.put("data", dataBytes);
            }

        } else {
            //数据包有问题
            Log.e("Msg", "Message Format Error");
        }
    }

    @Override
    public Map<String, Object> toMap() {
        return map;
    }
}
