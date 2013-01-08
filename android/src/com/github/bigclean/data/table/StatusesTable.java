package com.github.bigclean.data.table;

public class StatusesTable {
	public static final String TABLE_NAME  = "statuses";
	
	// Weibo status tags
	public static final String STATUS_TYPE = "status_type";
	public static final String STATUS_ID   = "status_id";
	public static final String STATUS_MID  = "status_mid";
	
	public static final String CREATED_AT = "status_created_at";
	public static final String TEXT       = "status_text";
	public static final String SOURCE     = "status_source";
	
	public static final String FAVORITED  = "status_favorited";
	public static final String TRUNCATED  = "status_truncated";
	
	public static final String IN_REPLY_TO_STATUS_ID   = "status_in_reply_to_status_id";
	public static final String IN_REPLY_USER_ID        = "status_in_reply_to_user_id";
	public static final String IN_REPLY_TO_SCREEN_NAME = "status_in_reply_to_screen_name";
	
	public static final String THUMBNAIL_PIC = "status_thumbnail_pic";
	public static final String BMIDDLE_PIC   = "status_bmiddle_pic";
	public static final String ORIGINAL_PIC  = "status_original_pic";
	
	public static final String REPOSTS_COUNT  = "status_reposts_count";
	public static final String COMMENTS_COUNT = "status_comments_count";
	
	public static final String RETWEETED_STATUS_ID = "status_retweeted_status_id";
	
	public static final String[] QUERY_COLUMNS = new String[] { STATUS_TYPE, STATUS_ID,
		CREATED_AT, TEXT, THUMBNAIL_PIC, SOURCE,
		REPOSTS_COUNT, COMMENTS_COUNT,
		UsersTable.USER_UID, RETWEETED_STATUS_ID };
}
