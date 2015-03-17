package com.datang.adc.handler;//package com.datang.client.handler;
//
//import com.datang.outum.client.IMsg;
//import com.datang.outum.trace.AppThreadFactory;
//import com.datang.outum.utils.SDCardUtils;
//import com.datang.outum.utils.StringUtils;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//
//import java.com.datang.business.util.Map;
//import java.com.datang.business.util.concurrent.ExecutorService;
//import java.com.datang.business.util.concurrent.Executors;
//
///**
// * Created by dingzhongchang_dev_MiniOu_view
// * User: Leo
// * Year: 2013
// * Date: 13-12-24
// * Time: 下午3:06
// */
//public class SupgradeHandler implements IHandler {
//    private static final Logger L = Logger.getLogger(SupgradeHandler.class);
//    static ExecutorService supgrade = Executors.newSingleThreadExecutor(new AppThreadFactory("SUPGRADE"));
//    private final MsgHandler msgHandler;
//    private ByteBuf SoftwareBuf = Unpooled.buffer();
//
//    public SupgradeHandler(MsgHandler msgHandler) {
//        this.msgHandler = msgHandler;
//    }
//
//
//    /**
//     * [Response]
//     * Command=Supgrade
//     * Session=Session ID
//     * Result=AC/NAC            #代表Request处理结果,Accept和Not accept
//     * Code=A Code              #如果结果为NAC时的处理结果代码
//     * FileN=File name          #升级软件名
//     * PacketCount=N            #将软件拆分成N个包下发
//     * PacketNo=n               #当前是第n个包 （n=1,2,……）
//     * <p/>
//     * 说明：软件包命名规则：从Vendor.V_1.sw编起
//     * 根据根目录保存的升级版本，周期扫描，发现新版本就提示升级
//     *
//     * @param ctx
//     * @param msg
//     * @param session
//     */
//    @Override
//    public void handle(ChannelHandlerContext ctx, IMsg msg, String session) {
//        final Map<String, Object> map = msg.toMap();
//        String type = map.get("type").toString();
//        if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("[Response]")) { //配置响应
//            if (map.get("result").toString().equalsIgnoreCase("NAC")) {
//                L.error("Sugrade NAC code:" + map.get("code"));
//                return;
//            }
//        } else {//升级指令
//
//        }
//        String packetno = map.get("packetno").toString();
//        if ("1".equals(packetno)) {
//            SoftwareBuf.clear();
//        }
//        SoftwareBuf.writeBytes((byte[]) map.get("data"));
//        if (packetno.equals(map.get("packetcount"))) {
//            supgrade.submit(new Runnable() {
//                @Override
//                public void run() {
//                    String filen = map.get("filen").toString();
//                    if (filen != null) {
//                        SDCardUtils.writeReadOnly(SDCardUtils.getSystemPath(), filen, SoftwareBuf.array());
//                        SoftwareBuf.clear();
//                    }
//                }
//            });
//        }
//    }
//}
