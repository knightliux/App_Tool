package com.ex.apptool.main;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.ex.app_tool.Configs;
import com.ex.app_tool.model.Model_up;
import com.ex.apptool.util.AjaxUtil;
import com.ex.apptool.util.AjaxUtil.PostCallback;
import com.ex.apptool.util.MD5Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView.FindListener;

public class AppUp {
	  private Context mContext;
	  private Model_up mUp;
	  private static String FILE_PATH;
	  private static String FILE_FULL_PATH=null;
	  private UpCallback mUpCallback;
      public AppUp(Context context){
    	  mContext=context;
    	  FILE_PATH= Environment.getExternalStorageDirectory() +"/AppUpdate/";
      }
      public interface UpCallback {
  		void CheckUp(String path, int code);
  		void CompelUp(String path,int code);
  		void Failure(String msg,int errorCode);
  		

  	  }
  	  private void downApk() {
		// TODO Auto-generated method stub
		  Log.d("path",FILE_PATH);
		  FinalHttp fn=new FinalHttp();
		  String FileName=MD5Util.getStringMD5_16(mUp.getUrl());
		  FILE_FULL_PATH=FILE_PATH+FileName+".apk";
		  File filePath = new File(FILE_PATH);  
          if(!filePath.exists()) {  
              filePath.mkdir();  
          }  
		  fn.download(mUp.getUrl(), FILE_FULL_PATH,true, new AjaxCallBack<File>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Log.d("Downfail",strMsg);
				if (errorNo == 416) {
					DownOver();
				} else{
					if(mUpCallback!=null){
						mUpCallback.Failure("", errorNo);
					}
				}
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
				super.onLoading(count, current);
			//	Log.d("info", "count:" + count + "--current:" + current);
				int progress = 0;
				if (current != count && current != 0) {
					progress = (int) (current / (float) count * 100);
				} else {
					progress = 100;
				}
			}

			@Override
			public void onSuccess(File t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				DownOver();
			}
			  
		});
	  }

      private void DownOver() {
		// TODO Auto-generated method stub
    	  //installApl();
    	  if(mUpCallback!=null){
    		   if(mUp.getType().equals("0")){
    			   mUpCallback.CompelUp(FILE_FULL_PATH, 200);
    		   }else{
    			   mUpCallback.CheckUp(FILE_FULL_PATH, 200);
    		   }
    	  }
	  }
	public void CheckUp(String Version,final UpCallback upcallback){
    	  AjaxParams params=new AjaxParams();
    	  mUpCallback=upcallback;
    	  params.put("mac", Configs.MAC);
//    	  params.put("appid", "2003");
    	  params.put("versin",Version);
    	  new AjaxUtil().post(Configs.URL.AppUp,params,new PostCallback() {
			
			@Override
			public void Success(String t) {
				// TODO Auto-generated method stub
				Log.d("success",t);
				try {
					Gson g=new Gson();
					mUp=g.fromJson(t, new TypeToken<Model_up>(){}.getType());
					if(!mUp.getUrl().equals("")){
						downApk();
					}else{
						if(upcallback!=null){
							upcallback.Failure("", 302);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					if(upcallback!=null){
						upcallback.Failure("", 301);
					}
				}
			}
			
		
			@Override
			public void Failure() {
				// TODO Auto-generated method stub
				if(upcallback!=null){
					upcallback.Failure("", 404);
				}
			}
		});
      }
      public void installApl(){
  		final String path=FILE_FULL_PATH;
  		try {
  			Log.d("installpath", path);
  			 Intent intent = new Intent();
  		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  		        intent.setAction(android.content.Intent.ACTION_VIEW);
  		        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
  		        mContext.startActivity(intent);
//  			Intent intent = new Intent(Intent.ACTION_VIEW);
//  			intent.setDataAndType(Uri.fromFile(new File(path)),
//  					"application/vnd.android.package-archive");
//  			mActivity.startActivity(intent);
  		         
  		} catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
  	}
}
