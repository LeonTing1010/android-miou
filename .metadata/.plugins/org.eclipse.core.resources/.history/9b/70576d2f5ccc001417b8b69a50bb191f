package com.datang.adc.handler;

import android.os.SystemClock;
import android.util.Log;
import com.datang.client.IMsg;
import com.datang.client.Message;
import com.datang.client.UpData;
import com.datang.client.UpMsg;
import com.datang.client.util.TripleDesUtil;
import com.datang.client.util.Util;
import com.datang.client.util.ZlibUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.File;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by dingzhongchang on 13-6-26.
 */
class UploadHandler implements IHandler {
    public static final String TAG = "UploadHandler";
    //    private static final Logger L = Logger.getLogger(UploadHandler.class);
    private static Deque<Message> msgDeque = new LinkedBlockingDeque<Message>();
    private final MsgHandler msgHandler;
    Future deque = null;
    Future upload = null;
    private ExecutorService Upload = Executors.newSingleThreadExecutor(new DefaultThreadFactory("Upload"));

    public UploadHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    /**
     * [Request]
     * Command=Upload
     * Session=Session ID
     * Filename=123456ABC      #上传的文件名，加后缀
     * Newfile=Y or N          #由前端确定,上传文件是否为最新文件
     * <p/>
     * [Response]
     * Command=Upload
     * Session=Session ID
     * Filename=123456ABC       #上传的文件名，与请求指令对应
     * Result=AC/NAC            #代表Request处理结果,Accept和Not accept
     * Code=A Code              #如果结果为NAC时的处理结果代码
     * Size=Filesize            #请求上传的文件已经传送的大小，如果该文件已经 传送完成，则返回-1
     * TriDES= triple-DES         #加密密钥，16进制表示，不包含0X，总共128bit，
     * 其中前64bit作为k1和k3，后64bit作为k2
     */
    @Override
    public void handle(final ChannelHandlerContext ctx, IMsg msg, final String session) {
        final Map<String, Object> map = msg.toMap();
        String type = map.get("type").toString();
        final String fileName = map.get("filename").toString();
        if (!Util.isEmpty(type) && type.equalsIgnoreCase("[Response]")) { //响应
            if (map.get("result").toString().equalsIgnoreCase("NAC")) {
                Log.w(TAG, "NAC:" + fileName + " Code:" + map.get("code"));
                return;
            }
        }
        ctx.flush();
        if (upload != null) {
            upload.cancel(true);
        }
        if (deque != null) {
            deque.cancel(true);
            msgDeque.clear();
        }
        final String Size = map.get("size").toString();
        if ("-1".equals(Size)) {
            Log.w(TAG, fileName + "Size=-1");
            LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
            msgMap.put("Command", "Eof");
            msgMap.put("Session", session);
            msgMap.put("Fname", fileName);
            msgMap.put("Size", String.valueOf(LogHelper.getFileSize(msgHandler.getProj() + File.separator + fileName)));
            UpMsg Eof = new UpMsg("Request", "Eof", msgMap);
            ctx.writeAndFlush(Eof);
            return;
        }
        final String trides = map.get("trides").toString();
        deque = Upload.submit(new Runnable() {
            @Override
            public void run() {
                int size = Integer.parseInt(Size);
                int count = 0;
                LogHelper helper = new LogHelper(msgHandler.getProj() + File.separator + fileName, size);
                final long fileSize = helper.getFileSize();
                try {
                    while (size != fileSize) {
                        byte[] data = null;
                        if (fileName.endsWith(".ssv")) {
                            data = helper.readBytes(LogHelper.MAX);
                        } else {
                            data = helper.readBytes(4 * LogHelper.MAX);
                        }
                        if (data.length <= 0) {
                            break;
                        }
                        byte[] bytes = TripleDesUtil.encryptByte(
                                TripleDesUtil.hexString2Bytes(TripleDesUtil.k1k2Tok1k2k3(trides)), ZlibUtil.compress(data));
                        msgDeque.offerLast(new UpData(fileName, session, bytes));
                        size += data.length;
                        count++;
                    }
                    Log.w(TAG, msgHandler.getProj() + File.separator + fileName
                            + " C:" + count + " S:" + size + " Tri:" + trides);
                    LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
                    msgMap.put("Command", "Eof");
                    msgMap.put("Session", session);
                    msgMap.put("Fname", fileName);
                    msgMap.put("Size", String.valueOf(fileSize));
                    UpMsg Eof = new UpMsg("Request", "Eof", msgMap);
                    msgDeque.offerLast(Eof);
                } catch (Exception e) {
                    Log.w(TAG, e.getMessage(), e);
                } finally {
                    helper.closeQuietly();
                }
            }
        });
        upload = ctx.executor().submit(new Runnable() {
            @Override
            public void run() {
                uploadMsg(ctx, fileName);
            }
        });

    }

    private void uploadMsg(final ChannelHandlerContext ctx, final String fileName) {
        ChannelFuture future = null;
        try {
            Message msg = null;
            while (msg == null) {
                msg = msgDeque.pollFirst();
                if (msg == null) {
                    SystemClock.sleep(1500);
                } else {
                    break;
                }
            }
            final String command = msg.name();

            future = ctx.writeAndFlush(msg);
            future.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        if (command.equalsIgnoreCase("eof")) {
                            Log.w(TAG, fileName + " SEND MSG:" + command);
                            return;
                        }
                        uploadMsg(ctx, fileName);
                    } else {
                        Log.w(TAG, fileName + " ERROR SEND MSG!");
                    }
                }
            });

        } catch (Exception e) {
            Log.w(TAG, e.getMessage(), e);
        }
    }

}
