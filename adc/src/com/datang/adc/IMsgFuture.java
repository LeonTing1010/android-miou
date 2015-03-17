package com.datang.adc;

/**
 * Created by dingzhongchang on 13-5-28.
 */
public interface IMsgFuture {
    //发送成功
    void onSuccess();

    //发送失败
    void onFail();
}
