/**
 * Copyright 2013 Datang Mobile Co.,Ltd. All rights reserved.
 * DTM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.datang.adc;

import java.util.List;

/**
 * @author dingzhongchang
 * @version 1.0.0
 */
public interface IClient {

    String getHost();

    void setOption(String boxid, String sver, String cver);

    int getPort();

    //连接服务器，<code>TRUE</code>成功,启动所有处理器
    boolean connect();

    boolean isConnected();

    //upload files
    void upload(String projPath, List<String> files, IMsgFuture future);

    //是否在线
    void online(IOnLineStatus status);

    //文件上传是否结束
    void eof(EofCallback eof);

   // 发送消息,异步调用future
    void send(IMsg msg, IMsgFuture future);

    //接受消息，接受服务器主动推送和响应的消息，callback处理
    void received(ICallIn callback);

    //关闭
    void shutdown();
}
