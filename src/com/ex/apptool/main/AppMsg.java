package com.ex.apptool.main;


import java.sql.NClob;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.util.Log;

import com.ex.app_tool.Configs;
import com.ex.app_tool.model.Model_msg;
import com.ex.apptool.util.AjaxUtil;
import com.ex.apptool.util.AjaxUtil.PostCallback;
import com.ex.apptool.util.DbUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppMsg {
	private MsgCallback msgcallback=null;
	public DbUtil db;
	public Context mConText;
	public interface MsgCallback {
		void Success(String msg, int i);
		void Failure(String msg,int errorCode);
		

	}
	public AppMsg(Context context){
		mConText=context;
	}
	public void GetMsg(final MsgCallback msgcallback){
		this.msgcallback=msgcallback;
		AjaxParams params=new AjaxParams();
		params.put("mac", Configs.MAC);
		params.put("appid", Configs.Appid);
		new AjaxUtil().post(Configs.URL.Msg,params,new PostCallback() {
			
			@Override
			public void Success(String t) {
				// TODO Auto-generated method stub
				//Log.d("msgsu", t);
				try {
					Gson g=new Gson();
					Model_msg msg=g.fromJson(t, new TypeToken<Model_msg>(){}.getType());
					if(msgcallback!=null){
						msgcallback.Success(msg.getMsg(),200);
					}
				} catch (Exception e) {
					// TODO: handle exception
					msgcallback.Failure("",301);
				}
			}
			
			@Override
			public void Failure() {
				// TODO Auto-generated method stub
				//Log.d("msgfa", "---");
				msgcallback.Failure("",404);
			}
		});
	}
}
