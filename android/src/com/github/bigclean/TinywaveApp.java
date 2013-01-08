package com.github.bigclean;

import com.github.bigclean.data.WeiboDatabase;
import com.github.bigclean.data.WeiboManager;

import android.app.Application;

public class TinywaveApp extends Application {
	private WeiboDatabase db = null;
	private WeiboManager  manager = null;
	private static TinywaveApp instance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		db = new WeiboDatabase(getApplicationContext());
		manager = new WeiboManager();
	}
	
	public static TinywaveApp getInstance() {
		return instance;
	}

	public WeiboDatabase getDatabase() {
		return db;
	}
	
	public WeiboManager getWeiboManager() {
		return manager;
	}
	
}