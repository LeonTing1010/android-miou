package com.datang.miou.api;

public class ProcessInterface {
	
	public static void RpGPS(double dbLon,double dbLat,int nAltitude,int nSpeed)
	{
		
	}
	public static void RpMOS(float fMosValue)
	{
		
	}
	public static void RpLab(char chBusinessType, boolean bStart)
	{
		
	}
	public static void RpAppEVT(int nEventID, String strEventInfo)
	{
		
	}
	public static void RpApplicationInfo(int nAppRecTotal,int nAppSendTotal,
			int nApp_currentThroughput_dl,int nApp_currentThroughput_ul,String strKey)
	{

	}

	//write log interface
	public static boolean StartLogWrite()
	{
		return true;
	}
	public static boolean StopLogWrite()
	{
		return true;
	}
	public static void SetLogMaxLen(int nBufferTime, int nMaxLen)
	{
		return;
	}
	
	//read log interface
	public static boolean StartLogRead()
	{
		return true;
	}
	public static boolean StopLogRead()
	{
		return true;
	}
	public static boolean PauseLogRead()
	{
		return true;
	}
	public static boolean SeekLogPosition(char chPercent)
	{
		return true;
	}
	public static boolean SetReadSpeed(char chSpeed)
	{
		return true;
	}
	//read hd interface
	public static boolean StartHdConnect()
	{
		return true;
	}
	public static boolean StopHdConnect()
	{
		return true;
	}

	//query interface
	public static String GetIEByID(int nIEID)
	{
		return "test";
	}
	public static boolean GetIEColByCallBack()
	{
		return true;
	}
	
	public static boolean CallBackIEByID(int nIEID,String strValue)
	{
		return true;
	}
}
