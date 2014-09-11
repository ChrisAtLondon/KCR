package com.example.killcunningrabit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
public final static int DATABASE_VERSION = 1;// 版本号  
public static final String T_GAME_PROPS = "GAME_PROPS";
public static final String T_GAME_LEVEL= "GAME_LEVEL";
public static final String T_GAME_SCORE= "GAME_SCORE";
public static final String DATABASE_NAME = "Chris.db";
private static MySQLiteOpenHelper mySQLiteOpenHelper =null;

	private MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private MySQLiteOpenHelper(Context context,int databaseVersion) {
		super(context, DATABASE_NAME, null, databaseVersion);
	}
	
	//简单单例模式，不用考虑多线程安全
	public static MySQLiteOpenHelper getInstance(Context context){
		if (mySQLiteOpenHelper==null) {
			mySQLiteOpenHelper= new MySQLiteOpenHelper(context);
		}
		return mySQLiteOpenHelper;
	}
	
	/** 
     * 传入一个版本号时，系统会在下一次调用数据库时调用Helper中的onUpgrade()方法进行更新 
     * @param context 
     * @param version 
     */
	public static MySQLiteOpenHelper getInstance(Context context, int databaseVersion) {
		mySQLiteOpenHelper= new MySQLiteOpenHelper(context,databaseVersion);	
	return mySQLiteOpenHelper;
}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//init DB
		String createTGameProps = "CREATE TABLE GAME_PROPS  (PROPS_ID  INTEGER PRIMARY KEY, PROPS_NAME TEXT, PROPS_TYPE TEXT, PROPS_LOCK_FLAG TEXT);";  
		//String createTGameProps = "CREATE TABLE GAME_PROPS  (PROPS_ID  INTEGER PRIMARY KEY AUTOINCREMENT, PROPS_NAME TEXT, PROPS_TYPE TEXT, PROPS_LOCK_FLAG TEXT);";  
       String createTGameLevel="CREATE TABLE GAME_LEVEL  (LEVEL_ID  INTEGER PRIMARY KEY, LEVEL_NAME TEXT, LEVEL_LOCK_FLAG TEXT,LEVEL_THUMB TEXT);";  
       String createTGameScore="CREATE TABLE GAME_SCORE  (LEVEL_ID  INTEGER PRIMARY KEY, HIGHEST_SCORE INTEGER);";  
        db.execSQL(createTGameProps); 
        db.execSQL(createTGameLevel);
        db.execSQL(createTGameScore);
        
        db.execSQL("INSERT INTO GAME_LEVEL VALUES('1','LEVEL 1','N','lvl1thumb.png')");
        db.execSQL("INSERT INTO GAME_LEVEL VALUES('2','LEVEL 2','N','lvl2thumb.png')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}



}
