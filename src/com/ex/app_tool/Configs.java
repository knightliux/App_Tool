package com.ex.app_tool;

import com.ex.apptool.util.DbUtil;
import com.ex.apptool.util.MACUtils;

import android.os.Environment;

public class Configs {
	//
	
	public static final String MAC = MACUtils.getMac();
	public static  String Appid = "22201";
	public static class URL {
		 
		public static String HOST1 = "http://23.89.145.178:9011/";
		public static String HOST2 = "http://replay2.yourepg.com:9011/";
		public static String HOST3 = "http://replay3.yourepg.com:9011/";
		public static String HOST = HOST1;
//		public static String HOST1 = "http://192.168.100.221:9016/";
//		public static String HOST2 = "http://192.168.100.221:9016/";
//		public static String HOST3 = "http://192.168.100.221:9016/";
//		public static String HOST = HOST1;
		public static String Auth=HOST+"Api/App/auth";
		public static String Msg=HOST+"Api/App/appmsg";
		public static String AppUp=HOST+"Api/App/upgrade";
	}

}
