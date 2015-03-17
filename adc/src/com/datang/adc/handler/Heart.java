package com.datang.adc.handler;//package com.datang.client.handler;
//
//import android.location.Location;
//import com.datang.client.IMsg;
//import com.datang.client.IOnLineStatus;
//import com.datang.client.UpMsg;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.com.datang.business.util.LinkedHashMap;
//import java.com.datang.business.util.concurrent.TimeUnit;
//
///**
// * Created by dingzhongchang on 13-6-5.
// */
//public final class Heart implements IHandler {
////	private static final Logger L = Logger.getLogger(Heart.class);
//	private static Heart ourInstance = new Heart();
//	// private ScheduledExecutorService Heart = Executors.newSingleThreadScheduledExecutor(new AppThreadFactory("Heart"));
//	private double batteryT = 0.0;
//	private String powermode = "E";
//	private int filesleft = 0;
//	private int cVer = 0;
//
//	// private ScheduledFuture<?> future;
//	private IOnLineStatus status;
//
//	private Heart() {
//	}
//
//	public static Heart getInstance() {
//		return ourInstance;
//	}
//
//	@Override
//	public void handle(final ChannelHandlerContext ctx, IMsg msg, final String session) {
//		if (StringUtils.isEmpty(session)) {
//			if (status != null) {
//				status.isOnline(false);
//			}
//			return;
//		}
//		// 告警信息
//		ctx.executor().schedule(new Runnable() {
//			@Override
//			public void run() {
//				// GPS未打开告警
//				// 没有sim卡告警
//
//			}
//		}, 0, TimeUnit.SECONDS);
//
//		// 获取小区表
//		/**
//		 * [Request] Command= CellTAB Session=Session ID Cver=config version #终端侧最新版本
//		 */
//		ctx.executor().schedule(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					String value = Config.getInstance().readValue("celltab.version");
//					if (value != null) {
//						cVer = Integer.parseInt(value);
//					}
//					LinkedHashMap<String, String> cellMap = new LinkedHashMap<String, String>();
//					cellMap.put("Command", "CellTAB");
//					cellMap.put("Session", session);
//					cellMap.put("Cver", cVer + "");
//					IMsg cellMsg = new UpMsg("Request", "CellTAB", cellMap);
//					ctx.writeAndFlush(cellMsg);
//				} catch (Exception e) {
//					L.warn(e.getMessage(), e);
//				}
//			}
//		}, 180, TimeUnit.SECONDS);
//
//		// 启动线程发送心跳消息
//		/**
//		 * Command=Status Session=Session ID Temp=temperature #前端的工作温度，浮点数 powermode=I or E #I表示使用内置电池供电，E表示外电供电 Spaceleft=N #前端剩余磁盘空间 Filesleft=N #前端未传的文件数
//		 */
//		ctx.executor().scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				heart(session, ctx);
//			}
//		}, 0, 120, TimeUnit.SECONDS);
//		// 启动线程发送GPS状态信息
//		ctx.executor().scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				gps(session, ctx);
//			}
//		}, 0, 1, TimeUnit.SECONDS);
//
//	}
//
//	/**
//	 * Command=Gps Session=Session ID Sec=seconds #秒 Usec=useconds #微秒数 Lon=Longitude #经度，Float，统一采用度表示 Lat=Latitude #纬度，Float，统一采用度表示 Altitude=Altitude #海拔高度，单位：米 Speed=speed #移动速度，单位：公里/小时
//	 */
//	private void gps(String session, ChannelHandlerContext ctx) {
//		long time = System.currentTimeMillis();
//		// GPSHelper helper = GPSHelper.getGPSHelper();
//		Location loc = null;// helper.getLocation();
//		if (loc == null) {
//			return;
//		}
//		try {
//			LinkedHashMap<String, String> gpsMap = new LinkedHashMap<String, String>();
//			gpsMap.put("Command", "Gps");
//			gpsMap.put("Session", session);
//			long sec = time / 1000;
//			long usec = (time - sec * 1000) * 1000;
//			gpsMap.put("Sec", String.valueOf(sec));
//			gpsMap.put("Usec", String.valueOf(usec));
//			gpsMap.put("Lon", loc.getLongitude() + "");
//			gpsMap.put("Lat", loc.getLatitude() + "");
//			gpsMap.put("Altitude", loc.getAltitude() + "");
//			gpsMap.put("Speed", loc.getSpeed() + "");
//			IMsg statusMsg = new UpMsg("", "Gps", gpsMap);
//			ctx.writeAndFlush(statusMsg);
//		} catch (Exception e) {
//			L.warn(e.getMessage(), e);
//		}
//	}
//
//	public void heart(String session, ChannelHandlerContext ctx) {
//		try {
//			LinkedHashMap<String, String> statusMap = new LinkedHashMap<String, String>();
//			statusMap.put("Command", "Status");
//			statusMap.put("Session", session);
//			statusMap.put("Temp", batteryT * 0.1 + "");
//			statusMap.put("Powermode", powermode);
//			statusMap.put("Spaceleft", String.valueOf(SDCardUtils.readAvSize()));
//			statusMap.put("Filesleft", filesleft + "");
//			IMsg statusMsg = new UpMsg("Request", "Status", statusMap);
//			ctx.writeAndFlush(statusMsg);
//		} catch (Exception e) {
//			L.warn(e.getMessage(), e);
//			if (status != null) {
//				status.isOnline(false);
//			}
//			ctx.executor().shutdownGracefully();
//		}
//	}
//
//	public int getcVer() {
//		return cVer;
//	}
//
//	public void setcVer(int cVer) {
//		this.cVer = cVer;
//	}
//
//	public void setFilesleft(int filesleft) {
//		this.filesleft = filesleft;
//	}
//
//	public void setBatteryT(double batteryT) {
//		this.batteryT = batteryT;
//	}
//
//	public void setPowermode(String powermode) {
//		this.powermode = powermode;
//	}
//
//	public void heart(IOnLineStatus status) {
//		this.status = status;
//	}
//
//}
