package com.ex.apptool.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbUtil extends SQLiteOpenHelper{
    public SQLiteDatabase db;

    private static final String name = "excache"; //数据库名称
    private static final int version = 1; //数据库版本
    public static final String TAG_AUTH="100";
	public DbUtil(Context context) {
		super(context, name, null, version);

		// TODO Auto-generated constructor stub
	}
    public SQLiteDatabase getDb(){
    	return getReadableDatabase();
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		   
		     
		   db.execSQL("CREATE TABLE IF NOT EXISTS JsonList (id integer primary key autoincrement, jsonId text UNIQUE, json text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
    public void SaveCacheByTag(String str,String TAG){
    	try {
			db=getReadableDatabase();
			db.execSQL("REPLACE INTO JsonList(jsonId,json) values(?,?)",new Object[]{TAG,str});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    public String GetCacheByTag(String TAG){
    	try {
			db=getReadableDatabase();
			Cursor Row = db.rawQuery("select * from JsonList where jsonId = ?", new String[]{TAG} );
			if(Row.moveToFirst()){
				String json=Row.getString(2);
				
				db.close();
				return json;
			}else{
			
				db.close();
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
    }
	
}
