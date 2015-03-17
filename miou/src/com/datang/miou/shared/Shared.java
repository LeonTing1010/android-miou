package com.datang.miou.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * 
 * @author suntongwei
 */
public class Shared {

	public static final String SharedName = "MIOU_SHARED";
	
	/**
	 * IMEI
	 */
	private static final String IMEI = "imei";
	public static void setImei(Context ctx, String imei) {
		getSharedPreferences(ctx).edit().putString(IMEI, imei).commit();
	}
	public static String getImei(Context ctx) {
		return getSharedPreferences(ctx).getString(IMEI, null);
	}
	
	/**
	 * Email
	 */
	private static final String EMail = "email";
	public static void setEmail(Context ctx, String email) {
		getSharedPreferences(ctx).edit().putString(EMail, email).commit();
	}
	public static String getEmail(Context ctx) {
		return getSharedPreferences(ctx).getString(EMail, null);
	}
	
	protected static SharedPreferences getSharedPreferences(Context ctx) {
		return ctx.getSharedPreferences(SharedName, 0);
	}
}
