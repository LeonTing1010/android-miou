package com.datang.adc;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by dingzhongchang on 13-5-27.
 */
 class ClientInitializer extends ChannelInitializer<SocketChannel> {

    InHandler msgHandler;
    ClientInitializer(InHandler msgHandler){
        this.msgHandler = msgHandler;

    }
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("decoder", new MsgDecoder());

        pipeline.addLast("encoder", new MsgEncoder());

        // and then business logic.
        pipeline.addLast("handler", msgHandler);


    }
}
