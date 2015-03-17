package com.datang.adc;


import com.datang.adc.handler.MsgHandler;
import com.datang.adc.util.Util;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dingzhongchang on 13-5-28.
 */
public class ClientManager {

    public static final MsgHandler MSG_HANDLER = MsgHandler.getHandler();
    private ConcurrentHashMap<String, IClient> clientMap = new ConcurrentHashMap<String, IClient>();

    private ClientManager() {
    }

    public static ClientManager getManager() {
        return ClientManagerHolder.ClientManager;
    }

    /**
     * 注册成功返回Client 注册失败返回NULL，失败原因连接不成功
     *
     * @param host IP
     * @param port PORT
     * @return Client
     */
    public IClient register(String host, int port) {
        if (Util.isEmpty(host)) return null;
        if (clientMap.containsKey(host)) {
            release(host);
        }
        IClient client = new Client(host, port);
        clientMap.put(host, client);
        MSG_HANDLER.handler(host);
        return client;
    }

    public void setOption(String boxId, String sver, String cver) {
        MSG_HANDLER.setUser(boxId);
        MSG_HANDLER.setSver(sver);
        MSG_HANDLER.setCver(cver);
    }

    public IClient get(String host) {
        return clientMap.get(host);
    }

    public Collection<IClient> getAll() {
        return clientMap.values();
    }

    public void release(String host) {
        IClient c = clientMap.remove(host);
        if (c != null) {
            c.shutdown();
        }
    }

    public void releaseAll() {
        for (IClient c : clientMap.values()) {
            if (c != null) {
                c.shutdown();
            }
        }
        clientMap.clear();
    }

    static class ClientManagerHolder {
        static ClientManager ClientManager = new ClientManager();
    }

}
