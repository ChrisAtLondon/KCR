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
     * ��Activity�е��ô˹��췽��������һ���汾��ʱ��ϵͳ������һ�ε������ݿ�ʱ����Helper�е�onUpgrade()�������и��� 
     * @param context
     * @param databaseVersion 
     */
	public GameLevelDAO(Context context, int databaseVersion){
		MySQLiteOpenHelper.getInstance(context,databaseVersion);
	}
	
	/** 
     * @return List<GameLevel>���ѽ����ؿ��б�
     * @return null��û���ѽ����ؿ�(��������²������)
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
