package com.github.bigclean.data.table;

public class CommentsTable {
	public static final String TABLE_NAME = "comments";
	
	// Weibo comment tags
	public static final String COMMENT_TYPE = "comment_type";
	public static final String COMMENT_ID   = "comment_id";
	public static final String COMMENT_MID  = "comment_mid";
	
	public static final String CREATED_AT = "comment_created_at";
	public static final String TEXT       = "comment_text";
	public static final String SOURCE     = "comment_source";
	
	public static final String REPLY_COMMENT_ID = "comment_reply_comment_id";
	
	public static final String[] QUERY_COLUMNS = new String[] { COMMENT_TYPE, COMMENT_ID, COMMENT_MID,
		CREATED_AT, TEXT, SOURCE,
		UsersTable.USER_UID, StatusesTable.STATUS_ID, REPLY_COMMENT_ID };
}