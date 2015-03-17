package com.datang.adc;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * Created by dingzhongchang on 13-6-8.
 */
public class LoginCallback implements ICallIn {

    private IOnLineStatus loginStatus;


    public void login(IOnLineStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public void active(ChannelHandlerContext ctx) {

    }

    @Override
    public void received(ChannelHandlerContext ctx, IMsg msg) {
        if ("Login".equalsIgnoreCase(msg.name())) {//登陆响应
            //解析响应，发送心跳消息
            final Map<String, Object> mapLogin = msg.toMap();
            if (loginStatus != null) {
                loginStatus.isOnline(mapLogin.get("result").toString().equalsIgnoreCase("AC"));
            }
        } else {//解析其他消息
            return;
        }
    }

    @Override
    public void caught(ChannelHandlerContext ctx, Throwable cause) {
        if (loginStatus != null) {
            loginStatus.isOnline(false);
        }
    }


}
