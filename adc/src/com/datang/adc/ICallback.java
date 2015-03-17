package com.datang.adc;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by dingzhongchang on 13-5-24.
 */
public interface ICallback {

    //连接建立成功
    void active(ChannelHandlerContext ctx);

    //接收到服务器的消息
    void received(ChannelHandlerContext ctx, IMsg msg);

    //捕获到异常
    void caught(ChannelHandlerContext ctx, Throwable cause);

}
