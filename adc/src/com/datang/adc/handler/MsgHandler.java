package com.datang.adc.handler;

import android.util.Log;
import com.datang.adc.*;
import com.datang.adc.util.Util;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by dingzhongchang on 13-6-5.
 */
public final class MsgHandler {
    //    private static final Logger L = Logger.getLogger(MsgHandler.class);
//    private static final Config C = Config.getInstance();
    private static final Map<String, IHandler> Handlers = new HashMap<String, IHandler>();
    private static final Set<String> LogUploadStatus = new ConcurrentSkipListSet<String>();
    private static final ClientManager MANAGER = ClientManager.getManager();
    //    private static final Heart HEART = Heart.getInstance();
    private static final String TAG = "MsgHandler";
    //    private final TestPlanFactory PLAN_FACTORY = TestPlanFactory.getTestPlanFactory();
    private String User;
    private String Sver = "1.0";
    private String Cver = "0";
    private String Session = "";
    private String host;
    private String proj;

    private EofCallback eof;


    /**
     * 构造方法
     */
    private MsgHandler() {
        String logfiles = Util.readValue("logfiles");
        if (!Util.isEmpty(logfiles)) {
            String[] logs = logfiles.split("#");
            for (String logfile : logs) {
                if (Util.isEmpty(logfile)) continue;
                LogUploadStatus.add(logfile);
            }
        }
//        Handlers.put("Config", new ConfigHandler(this));
        Handlers.put("Upload", new UploadHandler(this));
        Handlers.put("Eof", new EofHandler(this));
//        Handlers.put("Supgrade", new SupgradeHandler(this));
//        Handlers.put("CellTAB", new CellTABHandler(this));
//        Handlers.put("CellTAB",new CellTABHandler(this));

    }

    public static MsgHandler getHandler() {
        return HandlerManagerHolder.Manager;
    }


    public void setEof(EofCallback eof) {
        this.eof = eof;
    }

    public boolean isUploading(String boxId) {
        for (String logfile : LogUploadStatus) {
            if (Util.isEmpty(logfile)) continue;
            return logfile.startsWith(boxId);
        }
        return false;
    }

    public boolean isUploaded(String fileName) {
        return !LogUploadStatus.contains(fileName);
    }

    public void remove(String fileName) {
    	String strfullname = getProj() + File.separator + fileName;
        LogUploadStatus.remove(strfullname);
        if (eof != null) {
            eof.onEof(fileName);
        }
        for (String log : LogUploadStatus) {
            Util.writeValue("logfiles", log + "#");
        }
        upload(host, getUser());
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        this.Session = session;
    }

    public String getCver() {
        return Cver;
    }

    public void setCver(String cver) {
        Cver = cver;
    }

    public void setSver(String sver) {
        Sver = sver;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public void register(String command, IHandler handler) {
        Handlers.put(command, handler);
    }

    public void handler(String host) {
        final IClient client = ClientManager.getManager().get(host);
        if (client == null) return;
        client.received(new ICallIn() {

            @Override
            public void active(final ChannelHandlerContext ctx) {
                LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
                msgMap.put("Command", "Login");
                msgMap.put("User", User);
                msgMap.put("Pass", Util.byteReverse(msgMap.get("User")));
                msgMap.put("Sver", Sver);
                msgMap.put("Cver", Cver);
                ChannelFuture f = ctx.writeAndFlush(new UpMsg("Request", "login", msgMap));
//                String logfiles = Util.readValue("logfiles");
//                if (!Util.isEmpty(logfiles)) {
//                    String[] logs = logfiles.split("#");
//                    for (String logfile : logs) {
//                        if (Util.isEmpty(logfile)) continue;
//                        LogUploadStatus.add(logfile);
//                    }
//                }
            }

            @Override
            public void received(final ChannelHandlerContext ctx, IMsg msg) {
                if (msg.name().equalsIgnoreCase("Logout")) {//退出
                    MANAGER.release(client.getHost());
                } else if ("Login".equalsIgnoreCase(msg.name())) {//登陆响应
                    MsgHandler.this.Session = handleLogin(ctx, msg, client);
//                    HEART.handle(ctx, null, MsgHandler.this.Session);
                } else {//解析其他消息
                    IHandler handler = Handlers.get(msg.name());
                    if (handler != null) {
                        handler.handle(ctx, msg, MsgHandler.this.Session);
                    } else {
                        Log.w(TAG, "NOT Found Handler For " + msg.name());
                    }
                    return;
                }
            }

            @Override
            public void caught(ChannelHandlerContext ctx, Throwable cause) {
                MsgHandler.this.Session = "";
            }
        });

    }

    public void upload(String host, List<String> fileNames, IMsgFuture future) {
        this.host = host;
        LogUploadStatus.addAll(fileNames);

        //for (int i = 0; i < fileNames.size(); i++) {
            /**
             [Request]
             Command=Upload
             Session=Session ID
             Filename=123456ABC      #上传的文件名，加后缀
             Newfile=Y or N          #由前端确定,上传文件是否为最新文件
             */
            LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
            msgMap.put("Command", "Upload");
            msgMap.put("Session", MsgHandler.getHandler().getSession());

            //msgMap.put("Filename", fileNames.get(0));
            String strfilepath = fileNames.get(0);
            String truename = "";
            int nnokindex = strfilepath.lastIndexOf("/");
            if (nnokindex < strfilepath.length()) {
                truename = strfilepath.substring(nnokindex + 1, strfilepath.length());
            }

            if (truename != "") {
                msgMap.put("Filename", truename);
            } else {
                return;
            }

            msgMap.put("Newfile", "Y");
            UpMsg msg = new UpMsg("Request", "Upload", msgMap);

            for (String log : LogUploadStatus) {
                Util.writeValue("logfiles", log + "#");
            }
            send(host, msg, future);

    }


    public void upload(String host, String boxID) {
        for (String fileName : LogUploadStatus) {
            if (fileName == null){
                continue;
            }
            /**
            [Request]
            Command=Upload
            Session=Session ID
            Filename=123456ABC      #上传的文件名，加后缀
            Newfile=Y or N          #由前端确定,上传文件是否为最新文件
            */
           LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
           msgMap.put("Command", "Upload");
           msgMap.put("Session", MsgHandler.getHandler().getSession());
           //msgMap.put("Filename", fileName);
           
           String strfilepath = fileName;
           String truename = "";
           int nnokindex = strfilepath.lastIndexOf("/");
           if (nnokindex < strfilepath.length()) {
               truename = strfilepath.substring(nnokindex + 1, strfilepath.length());
           }

           if (truename != "") {
               msgMap.put("Filename", truename);
           } else {
               return;
           }
           
           msgMap.put("Newfile", "Y");
           UpMsg msg = new UpMsg("Request", "Upload", msgMap);
           send(host, msg, null);
           break;         
        }
    }

    public void send(String host, Message msg, IMsgFuture future) {
        final IClient client = ClientManager.getManager().get(host);
        if (client == null) return;
        client.send(msg, future);
    }

    private String handleLogin(ChannelHandlerContext ctx, IMsg msg, IClient client) {
        /**
         *
         [Response]
         Command=Login
         Session=Session ID       #服务器返回本次会话的ID号
         Result=AC/NAC            #代表Request处理结果,Accept和Not accept
         Code=A Code              #如果结果为NAC时的处理结果代码
         SverU=Y or N             #前端单元盒软件是否需要升级
         CverU=Y or N             #前端配置是否需要升级
         */

        //解析响应，发送心跳消息
        final Map<String, Object> mapLogin = msg.toMap();
        if (mapLogin.get("result").toString().equalsIgnoreCase("NAC")) {
            MANAGER.release(client.getHost());
            return "";
        }
        String session = mapLogin.get("session").toString();
        /**
         [Request]
         Command=Supgrade
         Session=Session ID
         Sver=software version       #当前的软件版本号
         */
        if (mapLogin.get("sveru").toString().equalsIgnoreCase("Y")) {//版本升级，发送升级指令
            LinkedHashMap<String, String> supgradeMap = new LinkedHashMap<String, String>();
            supgradeMap.put("Command", "Supgrade");
            supgradeMap.put("Session", session);
            supgradeMap.put("Sver", this.Sver);
            IMsg supgradeMsg = new UpMsg("Request", "Supgrade", supgradeMap);
            ctx.writeAndFlush(supgradeMsg);
            ctx.flush();
        }


        /**
         *配置请求
         [Request]
         Command=Config
         Session=Session ID
         */
        if (mapLogin.get("cveru").toString().equalsIgnoreCase("Y")) {//测试计划升级，发送配置指令
            LinkedHashMap<String, String> configMap = new LinkedHashMap<String, String>();
            configMap.put("Command", "Config");
            configMap.put("Session", session);
            configMap.put("Tver", Cver);
            IMsg configMsg = new UpMsg("Request", "Config", configMap);
            ctx.writeAndFlush(configMsg);
            ctx.flush();
        } else {
//            String planPath = Config.getInstance().readValue("plan");
//            if (!StringUtils.isEmpty(planPath)) {
//                byte[] plan = SDCardUtils.read(SDCardUtils.getSystemPath() +
//                        File.separator + Config.getInstance().readValue("plan"));
//                PLAN_FACTORY.decode(new String(plan));
//            }

        }
        return session;
    }

    public void setProj(String proj) {
        this.proj = proj;
    }

    public String getProj() {
        return proj;
    }

    static class HandlerManagerHolder {
        public static MsgHandler Manager = new MsgHandler();
    }


}
