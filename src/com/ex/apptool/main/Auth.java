package com.ex.apptool.main;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.util.Log;

import com.ex.app_tool.Configs;
import com.ex.app_tool.model.Model_auth;
import com.ex.apptool.util.AjaxUtil;
import com.ex.apptool.util.AjaxUtil.PostCallback;
import com.ex.apptool.util.DbUtil;
import com.ex.apptool.util.DeviceFun;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Auth {
	private AuthCallback authcallback=null;
	public DbUtil db;
    private Context mContext;
    public Auth(Context context,AuthCallback authcallback){
      	db=new DbUtil(context);
      	mContext=context;
      	this.authcallback=authcallback;
    	startAuth();
  
    }
    public interface AuthCallback{
        void Success(String msg);
        void Failure(String msg);
       
    }
	private void CheckAuth(Model_auth auth) {
		// TODO Auto-generated method stub
		if(auth.getCode().equals("0") ){
			if(authcallback!=null){
				authcallback.Success(auth.getMsg());
			}
		}else{
			authcallback.Failure(auth.getMsg());
		}
	}
	public void getFromNet(){
		getFromNet(true);
	}
	public void UpCacheFromNet(){
		getFromNet(false);
	}
	public void getFromNet(final boolean isCheck){
		AjaxParams params=new AjaxParams();
		params.put("mac", Configs.MAC);
		params.put("cpuid", DeviceFun.GetCpuId(mContext));
		new AjaxUtil().post(Configs.URL.Auth,params,new PostCallback() {
			
			@Override
			public void Success(String t) {
				// TODO Auto-generated method stub
//				Log.d("authre",t.toString());
				try {
					Gson g=new Gson();
					Model_auth auth=g.fromJson(t, new TypeToken<Model_auth>(){}.getType());
					if(isCheck==true){
						CheckAuth(auth);
					}
					db.SaveCacheByTag(t, DbUtil.TAG_AUTH);
				} catch (Exception e) {
					// TODO: handle exception
					
					authcallback.Failure("ERROR:301");
				}
			}
			
		

			@Override
			public void Failure() {
				// TODO Auto-generated method stub
				authcallback.Failure("ERROR:404");
			}
		});
	}
	public void startAuth() {
		// TODO Auto-generated method stub
		String Cache=db.GetCacheByTag(DbUtil.TAG_AUTH);
		if(Cache==null){
			getFromNet(true);	
		}else{
			Log.d("authfromcache",Cache);
			try {
				Gson g=new Gson();
				Model_auth auth=g.fromJson(Cache, new TypeToken<Model_auth>(){}.getType());
				CheckAuth(auth);
				
			} catch (Exception e) {
				// TODO: handle exception
				
				authcallback.Failure("ERROR:301");
			}
			UpCacheFromNet();
		}
		
	}
}
