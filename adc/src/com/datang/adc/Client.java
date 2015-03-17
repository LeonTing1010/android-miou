package com.datang.adc;

import android.util.Log;
import com.datang.adc.handler.MsgHandler;
import com.datang.adc.util.Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dingzhongchang on 13-5-24.
 */
public class Client implements IClient {
    private static final ClientManager MANAGER = ClientManager.getManager();
    private static final MsgHandler MSGHANDLER = MsgHandler.getHandler();
    //	private static final Logger L = Logger.getLogger(Client.class);
    Bootstrap bootstrap;
    EventLoopGroup group;
    LoginCallback login;
    InHandler handler;
    Channel c;
    private String host;
    private int port;
    boolean isConnected = false;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap = getBootstrap();
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setOption(String boxid, String sver, String cver) {
        MANAGER.setOption(boxid, sver, cver);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean connect() {
        boolean retry = true;
        // Make a new connection.
        int count = 5;
        while (count-- > 0 && retry) {
            Log.d("connect " + host + ":" + port, count + "");
            try {
                ChannelFuture f = bootstrap.connect(host, port);
                f.await(count * 500, TimeUnit.MILLISECONDS);
                c = f.channel();
                retry = !f.isSuccess();
            } catch (Exception e) {
                Log.w("connect " + host + ":" + port, e);
                shutdown();
            }
        }
        isConnected = !retry;
        return isConnected;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void upload(String projPath,List<String> files, IMsgFuture future) {
        MSGHANDLER.setProj(projPath);
        MSGHANDLER.upload(getHost(), files, future);
    }

    private Bootstrap getBootstrap() {
        if (group != null && !group.isShutdown()) {
            group.shutdownGracefully();
        }
        group = new NioEventLoopGroup();
        handler = new InHandler();
        login = new LoginCallback();
        Bootstrap b = new Bootstrap();
        b.option(ChannelOption.TCP_NODELAY, true);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120000);
        b.group(group).channel(NioSocketChannel.class).handler(new ClientInitializer(handler));
        handler.handle(login);
        return b;
    }

    @Override
    public void online(IOnLineStatus status) {
        if (login != null) {
            login.login(status);
        }
//		Heart.getInstance().heart(status);
    }

    @Override
    public void eof(EofCallback eof) {
        MSGHANDLER.setEof(eof);
    }

    @Override
    public void send(final IMsg msg, final IMsgFuture future) {
        if (c == null) {
            return;
        }
        c.eventLoop().submit(new Runnable() {
            @Override
            public void run() {
                ChannelFuture f = c.writeAndFlush(msg);
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
        });
    }

    @Override
    public void received(ICallIn callback) {
        if (handler != null) {
            handler.handle(callback);
        }
    }

    @Override
    public void shutdown() {
        final String session = MsgHandler.getHandler().getSession();
        if (!Util.isEmpty(session)) {
            c.eventLoop().submit(new Runnable() {
                @Override
                public void run() {
                    /**
                     * Command=Logout Session=Session ID
                     */
                    LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
                    msgMap.put("Command", "Logout");
                    msgMap.put("Session", session);
                    c.writeAndFlush(new UpMsg("", "Logout", msgMap));
                }
            });
        }
        isConnected= false;
        if (group != null) {
            group.shutdownGracefully();
        }

    }
}
