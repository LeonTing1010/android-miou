package com.datang.adc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by dingzhongchang on 13-5-27.
 */
 class MsgDecoder extends ByteToMessageDecoder {
//    private static final Logger L = Logger.getLogger(MsgDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        // Wait until the length prefix is available.
        if (in.readableBytes() < 2) {
            return;
        }
        in.markReaderIndex();
        // Wait until the whole data is available.
        int dataLength = in.readUnsignedShort();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        // Convert the received data into a new Msg.
        byte[] decoded = new byte[dataLength];
        in.readBytes(decoded);
        out.add(new DownMsg(decoded));
    }
}
