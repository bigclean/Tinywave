package com.github.bigclean.data.table;

public class UsersTable {
	public static final String TABLE_NAME = "users";
	// Weibo user tags
	public static final String USER_UID   = "user_uid";
	
	public static final String SCREEN_NAME = "user_screen_name";
	public static final String NAME        = "user_name";
	
	public static final String PROVINCE = "user_province";
	public static final String CITY     = "user_city";
	public static final String LOCATION = "user_location";
	
	public static final String DESCRIPTION        = "user_description";
	public static final String URL                = "user_url";
	public static final String PROFILE_IMAGE_URL  = "user_profile_image_url";
	public static final String AVATAR_LARGE       = "user_avatar_large";
	public static final String DOMAIN             = "user_domain";
	public static final String GENDER             = "user_gender";
	
	public static final String FOLLOWERS_COUNT    = "user_followers_count";
	public static final String FRIENDS_COUNT      = "user_friends_count";
	public static final String STATUSES_COUNT     = "user_statuses_count";
	public static final String FAVOURITES_COUNT   = "user_favourites_count";
	public static final String BI_FOLLOWERS_COUNT = "user_bi_followers_count";
	public static final String FOLLOW_ME          = "user_follow_me";
	public static final String FOLLOWING          = "user_following";
	
	public static final String CREATED_AT         = "user_created_at";
	public static final String VERIFIED           = "user_verified";
	public static final String VERIFIED_REASON    = "user_verified_reason";
	public static final String GEO_ENABLED        = "user_geo_enabled";
	
	public static final String ALLOW_ALL_ACT_MSG  = "user_allow_all_act_msg";
	public static final String ALLOW_ALL_COMMENT  = "user_allow_all_comment";
	
	public static final String ONLINE_STATUS      = "user_online_status";
	
	public static final String[] QUERY_COLUMNS = new String[] { USER_UID,
		SCREEN_NAME, NAME,
		DESCRIPTION, URL, PROFILE_IMAGE_URL,
		GENDER, LOCATION,
		FOLLOWERS_COUNT, FRIENDS_COUNT, STATUSES_COUNT, FAVOURITES_COUNT,
		FOLLOWING, FOLLOW_ME };
}