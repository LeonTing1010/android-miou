package com.datang.adc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingzhongchang on 13-5-27.
 */
class InHandler extends SimpleChannelInboundHandler<DownMsg> {
    ChannelHandlerContext ctx;
    private List<ICallIn> callbacks = new ArrayList<ICallIn>();

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.flush().closeFuture();
        }
    }

    public void handle(ICallIn callback) {
        callbacks.add(callback);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        for (ICallIn callback : callbacks) {
            callback.active(ctx);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        for (ICallIn callback : callbacks) {
            callback.caught(ctx, cause);
        }
        closeOnFlush(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel() != null) {
            closeOnFlush(ctx.channel());
        }
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final DownMsg downMsg) throws Exception {
        for (final ICallIn callback : callbacks) {
            channelHandlerContext.executor().execute(new Runnable() {
                @Override
                public void run() {
                    callback.received(channelHandlerContext, downMsg);
                }
            });
        }
    }
}
