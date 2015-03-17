package com.datang.adc.handler;//package com.datang.client.handler;
//
//import com.datang.outum.client.IMsg;
//import com.datang.outum.config.Config;
//import com.datang.outum.trace.AppThreadFactory;
//import com.datang.outum.utils.SDCardUtils;
//import com.datang.outum.utils.StringUtils;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//
//import java.io.File;
//import java.com.datang.business.util.Map;
//import java.com.datang.business.util.concurrent.ExecutorService;
//import java.com.datang.business.util.concurrent.Executors;
//
///**
// * Created by dingzhongchang_dev_MiniOu_view User: Leo Year: 2013 Date: 13-12-25 Time: 上午9:45
// */
//public class CellTABHandler implements IHandler {
//
//	private static final Logger L = Logger.getLogger(CellTABHandler.class);
//	static ExecutorService cellTAB = Executors.newSingleThreadExecutor(new AppThreadFactory("CellTAB"));
//	private final MsgHandler msgHandler;
//	private ByteBuf CellTabBuf = Unpooled.buffer();
//
//	public CellTABHandler(MsgHandler msgHandler) {
//		this.msgHandler = msgHandler;
//	}
//
//	/**
//	 * [Response] Command= CellTAB Session=Session ID Cver=config version #平台的最新配置版本 Result=AC/NAC Code=A Code #如果结果为NAC时的处理结果代码 PacketCount=N #将配置更新包拆分成N个包下发 PacketNo=n #当前是第n个包（n=1,2,……）
//	 *
//	 * @param ctx
//	 * @param msg
//	 * @param session
//	 */
//	@Override
//	public void handle(ChannelHandlerContext ctx, IMsg msg, String session) {
//		final Map<String, Object> map = msg.toMap();
//		String type = map.get("type").toString();
//		if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("[Response]")) { // 小区表获取响应
//			if (map.get("result").toString().equalsIgnoreCase("NAC")) {
//				L.error("CellTAB NAC code:" + map.get("code"));
//				return;
//			}
//		} else {// 小区表获取
//
//		}
//		final String cver = map.get("cver").toString();
//
//		String packetno = map.get("packetno").toString();
//		if ("1".equals(packetno)) {
//			CellTabBuf.clear();
//            String[] split = cver.split("_");
//            if (split != null && split.length > 1) {
//                Integer cVer = Integer.parseInt(split[1]);
//                Config.getInstance().writeProperties("celltab.version", cVer.toString());
//            }
//		}
//		CellTabBuf.writeBytes((byte[]) map.get("data"));
//		if (packetno.equals(map.get("packetcount"))) {
//			cellTAB.submit(new Runnable() {
//				@Override
//				public void run() {
//					SDCardUtils.writeReadOnly(SDCardUtils.getProjectFilePath() + File.separator + "CellTAB", cver + ".csv", CellTabBuf.array());
//					CellTabBuf.clear();
//				}
//			});
//		}
//	}
//}
