package com.datang.adc;

import java.nio.ByteBuffer;

/**
 * Created by dingzhongchang on 13-6-28.
 */
public class UpData extends Message {
//    private static final Logger L = Logger.getLogger(UpData.class);
    /**
      字段	类型	长度(Byte)	备注
     Flag	char	 29	数据包归属文件的标识
     Session ID	Int	 4	会话ID
     Lenth	Int	 2	数据包长度
     Pure Data	char		N	纯数据包
     */
    //29个字节	设备ID+测试文件生成时间+测试模块号+文件后缀
    public UpData(String Flag,String Session,byte[] Data){
        int Lenth = Data.length;
        ByteBuffer buf = ByteBuffer.allocate(4+29+4+2+Lenth);//指令数据分隔符+flag+数据
        buf.put(S);
        buf.put(Flag.getBytes());
        buf.putInt(Integer.parseInt(Session));
        buf.putShort((short) Lenth);
        buf.put(Data);
        buf.flip();
        body = buf.array();
    }

}