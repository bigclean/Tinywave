package com.github.bigclean.data.table;

public class FavoritesTable {
	public static final String TABLE_NAME = "favorites";
	
	// Weibo favorite tags
	public static final String FAVORITED_TIME = "favorited_time";
	
	public static final String[] FAVORITES_TABLE_QUERY_COLUMNS = new String[] { StatusesTable.STATUS_ID,
		FAVORITED_TIME };

}