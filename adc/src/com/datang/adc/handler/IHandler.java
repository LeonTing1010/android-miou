package com.datang.adc.handler;

import com.datang.adc.IMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by dingzhongchang on 13-6-5.
 */
public interface IHandler {

    void handle(ChannelHandlerContext ctx, IMsg msg, String session);
}
