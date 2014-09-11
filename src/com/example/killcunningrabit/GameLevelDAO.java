package com.example.killcunningrabit;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameLevelDAO {
	MySQLiteOpenHelper helpler= null;
	public GameLevelDAO(Context context){
		helpler = MySQLiteOpenHelper.getInstance(context);
	}
	/** 
     * 当Activity中调用此构造方法，传入一个版本号时，系统会在下一次调用数据库时调用Helper中的onUpgrade()方法进行更新 
     * @param context
     * @param databaseVersion 
     */
	public GameLevelDAO(Context context, int databaseVersion){
		MySQLiteOpenHelper.getInstance(context,databaseVersion);
	}
	
	/** 
     * @return List<GameLevel>：已解锁关卡列表
     * @return null：没有已解锁关卡(正常情况下不会出现)
     */
	public List<GameLevel>queryUnlockedGameLevels(){
		List<GameLevel> ls = new LinkedList<GameLevel>();
		SQLiteDatabase db = helpler.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM GAME_LEVEL WHERE LEVEL_LOCK_FLAG='N'", null);
		if (cursor!=null) {
			while (cursor.moveToNext()) {
				ls.add( new GameLevel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
			}
			return ls;
		}
		return null;
		
		
	}

}
