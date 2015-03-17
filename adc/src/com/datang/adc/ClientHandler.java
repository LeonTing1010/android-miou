package com.datang.adc;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingzhongchang on 13-5-27.
 */
 class ClientHandler extends SimpleChannelInboundHandler<DownMsg> {
    ChannelHandlerContext ctx;
    private List<ICallback> callbacks = new ArrayList<ICallback>();

    public void handle(ICallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        for (ICallback callback : callbacks) {
            callback.active(ctx);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DownMsg downMsg) throws Exception {
        for (ICallback callback : callbacks) {
            callback.received(channelHandlerContext, downMsg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        for (ICallback callback : callbacks) {
            callback.caught(ctx, cause);
        }
        ctx.close();
    }

    public void handle(IMsg msg, final IMsgFuture future) {
        ChannelFuture f =  ctx.writeAndFlush(msg);
        f.addListener(new GenericFutureListener<Future<Void>>() {
            @Override
            public void operationComplete(Future<Void> voidFuture) throws Exception {
                if (future != null) {
                    if (voidFuture.isSuccess()) {
                        future.onSuccess();
                    } else {
                        future.onFail();
                    }
                }
            }
        });

    }
}
