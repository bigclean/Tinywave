package com.github.bigclean.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeiboDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_TABLE_STATUSES   = "create table statuses ("
			                                           + "_id integer primary key autoincrement, "
			                                           + "status_type integer, "
			                                           + "status_id text not null, status_mid text not null, "
			                                           + "status_created_at text, status_text text not null, status_source text, "
			                                           + "status_favorited text, status_truncated, "
			                                           + "status_in_reply_to_status_id text, status_in_reply_to_user_id text, status_in_reply_to_screen_name text, "
			                                           + "status_thumbnail_pic text, status_bmiddle_pic text, status_original_pic text, "
			                                           + "status_reposts_count integer, status_comments_count integer, "
			                                           + "user_uid text, "
			                                           + "status_retweeted_status_id text"
			                                           +")";
	
	public static final String CREATE_TABLE_USERS      = "create table users ("
                                                       + "_id integer primary key autoincrement, "
                                                       + "user_uid text not null, "
                                                       + "user_screen_name text, user_name text not null, "
                                                       + "user_province integer, user_city integer, user_location text, "
                                                       + "user_description text, user_url text, user_profile_image_url text, "
                                                       + "user_avatar_large text, user_domain text, user_gender text, "
                                                       + "user_followers_count integer, user_friends_count integer, "
                                                       + "user_statuses_count integer, user_favourites_count integer, "
                                                       + "user_bi_followers_count integer, user_following text, user_follow_me text, "
                                                       + "user_created_at text, user_verified text, user_verified_reason text, "
                                                       + "user_geo_enabled text, user_allow_all_act_msg text, user_allow_all_comment text, "
                                                       + "user_online_status integer"
                                                       +")";
	
	public static final String CREATE_TABLE_COMMENTS   = "create table comments ("
			                                           + "_id integer primary key autoincrement, "
			                                           + "comment_type integer, "
			                                           + "comment_id text not null, comment_mid text not null, "
			                                           + "comment_created_at text, comment_text text, comment_source text, "
			                                           + "user_uid text not null, "
			                                           + "status_id text, "
			                                           + "comment_reply_comment_id text"
			                                           + ")";
	
	public static  final String CREATE_TABLE_FAVORITES = "create table favorites ("
			                                           + "_id integer primary key autoincrement, "
			                                           + "status_id text not null, "
			                                           + "favorited_time text not null"
			                                           + ")";
	
	private static final String DATABASE_NAME        = "weibo.db";
	private static final int    DATABASE_VERSION     = 1;
	
	public WeiboDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_STATUSES);
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_COMMENTS);
		db.execSQL(CREATE_TABLE_FAVORITES);
		System.out.println("create tables successfully");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE statuses");
		db.execSQL("DROP TABLE users");
		db.execSQL("DROP TABLE comments");
		db.execSQL("DROP TABLE favorites");
		onCreate(db);
		System.out.println("upgrade tables successfully");
	}

}