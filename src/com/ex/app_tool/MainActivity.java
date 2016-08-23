package com.ex.app_tool;

import com.ex.apptool.main.AppMsg;
import com.ex.apptool.main.AppMsg.MsgCallback;
import com.ex.apptool.main.AppUp;
import com.ex.apptool.main.AppUp.UpCallback;
import com.ex.apptool.main.Auth;
import com.ex.apptool.main.Auth.AuthCallback;
import com.ex.apptool.util.DeviceFun;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppUp mAppUp=new AppUp(this);
//		String dd=DeviceFun.printDeviceInf("dd",this);
//		Toast.makeText(this, dd, Toast.LENGTH_LONG).show();
		mAppUp.CheckUp("20160823",new UpCallback() {
			
			@Override
			public void Failure(String msg, int errorCode) {
				// TODO Auto-generated method stub
				Log.d("upFial",errorCode+"");
			}
			
			@Override
			public void CompelUp(String path, int code) {
				// TODO Auto-generated method stub
				Log.d("CompelUp",path);
			}
			
			@Override
			public void CheckUp(String Path, int code) {
				// TODO Auto-generated method stub
				Log.d("CheckUp",Path);
			}
		});
		
		
		Auth mAuth=new Auth(this,new AuthCallback() {
			
			@Override
			public void Success(String msg) {
				// TODO Auto-generated method stub
				Log.d("authsuccess,",msg);
			}
			
			

			@Override
			public void Failure(String msg) {
				// TODO Auto-generated method stub
				Log.d("authfail,",msg);
			}
		});
		AppMsg mMsg=new AppMsg(this);
		mMsg.GetMsg(new MsgCallback() {
			
			@Override
			public void Success(String msg,int code) {
				// TODO Auto-generated method stub
				Log.d("msgsuccess,",code+"");
			}
			
			@Override
			public void Failure(String msg,int code) {
				// TODO Auto-generated method stub
				Log.d("msgfail,",code+"");
			}
		});
		
	}
}
