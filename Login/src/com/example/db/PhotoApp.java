package com.example.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class PhotoApp extends Application {
	
	private SQLiteDatabase db = null;
	private UsersDB usersDb = null;
	
	public SQLiteDatabase getDB(){
		if(db == null){
			db = new DBHelper(getApplicationContext(), null, null, 0).open();			
		}
		return db;
	}
	
	public UsersDB getUserDB(){
		if(usersDb == null){
			usersDb = new UsersDB(getDB());
		}
		return usersDb;
	}
}