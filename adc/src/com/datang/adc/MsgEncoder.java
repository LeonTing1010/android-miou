package com.datang.adc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * Created by dingzhongchang on 13-5-27.
 */
 class MsgEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // Write a message.
        out.writeShort((int) msg.length());  // data length
        out.writeBytes(msg.toBytes());      // data
    }
}