package com.datang.adc.handler;

import android.util.Log;
import com.datang.adc.IMsg;
import com.datang.adc.UpMsg;
import com.datang.adc.util.SDCardUtils;
import com.datang.adc.util.Util;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dingzhongchang on 13-6-28.
 */
public class EofHandler implements IHandler {
    public static final String TAG = "EofHandler";
    //    private static final Logger L = Logger.getLogger(UploadHandler.class);
//    private static final Config C = Config.getInstance();
    private final MsgHandler msgHandler;

    public EofHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    /**
     * [Request]
     * Command=Eof
     * Session=Session ID
     * Fname=file name
     * Size=file length    （表示文件大小，单位字节）
     * <p/>
     * <p/>
     * [Response]
     * Command=Eof
     * Session=Session ID
     * Fname=file name          #上传的文件名，加后缀
     * Result=AC/NAC            #代表Request处理结果,Accept和Not accept
     * Code=A Code              #如果结果为NAC时的处理结果代码
     */


    @Override
    public void handle(ChannelHandlerContext ctx, IMsg msg, String session) {
        final Map<String, Object> map = msg.toMap();
        String type = map.get("type").toString();
        if (!Util.isEmpty(type) && type.equalsIgnoreCase("[Response]")) { //响应
            if (map.get("result").toString().equalsIgnoreCase("AC")) {
                msgHandler.remove(map.get("fname").toString());
                //上传成功，移动文件夹
                String fname = File.separator + map.get("fname");
                
                String strprojpath = msgHandler.getProj();                    
                    
                String strFullPath = strprojpath + fname;
                File destfile = new File(strFullPath);
                
                
                //File srcfile = new File(SDCardUtils.getLogFilePath() + fname);
                //SDCardUtils.copy(srcfile, destfile);
                //srcfile.delete();
                //String fileName = destfile.getAbsolutePath();
                if(destfile.exists()){
                	try {
                        SDCardUtils.zipSingleFile(strFullPath, strFullPath + ".zip");
                        destfile.delete();
                    } catch (IOException e) {
                        Log.e("EofHandler", e.getMessage());
                    }
                }
                return;
            }

        }
        Log.w(TAG, "EOF Fail!");
        //上传失败
        String Fname = map.get("fname").toString();
        LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
        msgMap.put("Command", "Upload");
        msgMap.put("Session", session);
        msgMap.put("Filename", Fname);
        msgMap.put("Newfile", "N");
        UpMsg Up = new UpMsg("Request", "Upload", msgMap);
        ctx.writeAndFlush(Up);
    }
}
