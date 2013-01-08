package com.github.bigclean.data;

import java.util.ArrayList;

import com.github.bigclean.data.table.CommentsTable;
import com.github.bigclean.data.table.FavoritesTable;
import com.github.bigclean.data.table.StatusesTable;
import com.github.bigclean.data.table.UsersTable;
import com.github.bigclean.model.WeiboComment;
import com.github.bigclean.model.WeiboFavorite;
import com.github.bigclean.model.WeiboStatus;
import com.github.bigclean.model.WeiboUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WeiboDatabase {
	private static final String USERS_TABLE     = "users";
	private static final String STATUSES_TABLE  = "statuses";
	private static final String COMMENTS_TABLE  = "comments";
	private static final String FAVORITES_TABLE = "favorites";
	
	// global variable
	private SQLiteDatabase db;
	private WeiboDatabaseHelper helper;
	
	public WeiboDatabase(Context context) {
		try {
			helper = new WeiboDatabaseHelper(context);
			db = helper.getWritableDatabase();
		} catch (RuntimeException e) {
			System.out.println(e.toString());
		}
	}
	
	public void close() {
		helper.close();
	}
	
	public boolean isWeiboStatusExists(Long id, Integer type) {
		boolean result = false;
	
		Cursor cursor = db.query(STATUSES_TABLE, new String[] { StatusesTable.STATUS_ID, StatusesTable.STATUS_TYPE },
				 StatusesTable.STATUS_ID + "=" + id.toString() + " AND " + StatusesTable.STATUS_TYPE + "=" + type.toString(),
				null, null, null, null);
		
		if (cursor != null && cursor.getCount() > 0) {
			result = true;
		}
		cursor.close();
		
		return result;
	}
	
	public boolean insertWeiboStatus(WeiboStatus status) {
		return insertWeiboStatus(status.getType(), status.getId(), status.getMid(),
				status.getCreatedAt(), status.getText(), status.getSource(),
				status.isFavorited(), status.isTruncated(),
				status.getInReplyToStatusId(), status.getInReplyToUserId(), status.getInReplyToScreenName(),
				status.getThumbnailPic(), status.getBmiddlePic(), status.getOriginalPic(),
				status.getRepostsCount(), status.getCommentsCount(),
				status.getUser(),
				status.getRetweetedStatus());
	}
	
	private boolean insertWeiboStatus(int type, long id, long mid,
			String createdAt, String text, String source,
			boolean favorited, boolean truncated,
			String inReplyToStatusId, String inReplyToUserId, String inReplyToScreenName,
			String thumbnailPic, String bmiddlePic, String originalPic,
			int repostsCount, int commentsCount,
			WeiboUser user,
			WeiboStatus retweetedStatus) {
		ContentValues values = new ContentValues();
		
		values.put(StatusesTable.STATUS_TYPE, type);
		values.put(StatusesTable.STATUS_ID, id);
		values.put(StatusesTable.STATUS_MID, mid);
		
		values.put(StatusesTable.CREATED_AT, createdAt);
		values.put(StatusesTable.TEXT, text);
		values.put(StatusesTable.SOURCE, source);
		
		values.put(StatusesTable.FAVORITED, favorited);
		values.put(StatusesTable.TRUNCATED, truncated);
		
		values.put(StatusesTable.IN_REPLY_TO_STATUS_ID, inReplyToStatusId);
		values.put(StatusesTable.IN_REPLY_USER_ID, inReplyToUserId);
		values.put(StatusesTable.IN_REPLY_TO_SCREEN_NAME, inReplyToScreenName);
		
		values.put(StatusesTable.THUMBNAIL_PIC, thumbnailPic);
		values.put(StatusesTable.BMIDDLE_PIC, bmiddlePic);
		values.put(StatusesTable.ORIGINAL_PIC, originalPic);
		
		values.put(StatusesTable.REPOSTS_COUNT, repostsCount);
		values.put(StatusesTable.COMMENTS_COUNT, commentsCount);
		
		if (user != null) {
			values.put(UsersTable.USER_UID, user.getUid());
			insertWeiboUser(user);
		}
		
		if (retweetedStatus != null) {
			values.put(StatusesTable.RETWEETED_STATUS_ID, retweetedStatus.getId());
			insertWeiboStatus(retweetedStatus);
		}
		/*
		else {
			// XXX default value should be specified when creating database
			values.put(STATUS_RETWEETED_STATUS_ID, 0);
		}
		*/
		
		return (db.insert(STATUSES_TABLE, null, values) > 0);
	}
	
	public void updateWeiboStatuses(ArrayList<WeiboStatus> updatedStatuses, Integer type) {
		// TODO it should be evaluated that if status is existed already?
		// TODO Timestamp would be a better flag to choose which statuses should be replaced.
		/*
		ArrayList<WeiboStatus> statuses = selectWeiboStatuses(type);
		
		if (statuses == null) {
			for (WeiboStatus updatedStatus : updatedStatuses) {
				System.out.println("Updating ...");
				insertWeiboStatus(updatedStatus);
			}
		} else {
			for (WeiboStatus updatedStatus : updatedStatuses) {
				for (WeiboStatus status : statuses) {
					if (updatedStatus.getId() != status.getId()) {
						System.out.println("Updating database ...");
						System.out.println("Updating status id is  ..." + updatedStatus.getId());
						deleteWeiboStatus(status.getId(), type);
						insertWeiboStatus(updatedStatus);
					}
				}
			}
		}
		*/
		deleteWeiboStatuses(type);
		for (WeiboStatus updatedStatus : updatedStatuses) {
			insertWeiboStatus(updatedStatus);
		}
	}
	
	public boolean deleteWeiboStatuses(Integer type) {
		return (db.delete(STATUSES_TABLE, StatusesTable.STATUS_TYPE + "=" + type.toString(), null) > 0);
	}
	
	public boolean deleteWeiboStatus(Long id, Integer type) {
		// XXX whether delete related user information from 'users' table
		WeiboStatus status = selectWeiboStatus(id, type);
		if (status.getUser() != null) {
			deleteWeiboUser(selectWeiboStatus(id, type).getUser().getUid());
		}
		
		String whereSelection = StatusesTable.STATUS_ID + "=" + id.toString()
				+ " AND " + StatusesTable.STATUS_TYPE + "=" + type.toString();
		return (db.delete(STATUSES_TABLE, whereSelection, null) > 0);
	}
	
	
	// 'user' table
	public boolean isWeiboUserExists(Long uid) {
		boolean result = false;
		
		Cursor cursor = db.query(USERS_TABLE, new String[] { UsersTable.USER_UID },
				UsersTable.USER_UID + "=" + uid.toString(),
				null, null, null, null);
		
		if (cursor != null && cursor.getCount() > 0) {
			result = true;
		}
		cursor.close();
		
		return result;
	}
	
	public boolean insertWeiboUser(WeiboUser user) {
		return insertWeiboUser(user.getUid(),
				user.getScreenName(), user.getName(),
				user.getProvince(), user.getCity(), user.getLocation(),
				user.getDescription(), user.getUrl(), user.getProfileImageUrl(),
				user.getAvatarLarge(), user.getDomain(), user.getGender(),
				user.getFollowersCount(), user.getFriendsCount(), user.getStatusesCount(),
				user.getFavouritesCount(), user.getBiFollowersCount(),
				user.isFollowing(), user.isFollowMe(),
				user.getCreatedAt(), user.isVerified(), user.getVerifiedReason(), user.isGeoEnabled(),
				user.isAllowAllActMsg(), user.isAllowAllComment(),
				user.getOnlineStatus());
	}
	
	private boolean insertWeiboUser(long uid,
			String screenName, String  name,
			int province, int city, String location,
			String description, String url, String profileImageUrl, String avatarLarge, String domain, String gender,
			int followersCount, int friendsCount, int statusesCount, int favouritesCount, int biFollowersCount,
			boolean following, boolean followMe,
			String createdAt, boolean verified, String verifiedReason, boolean geoEnabled,
			boolean allowAllActMsg, boolean allowAllComment,
			int onlineStatus) {
		// Evaluate whether inserted user is existed in 'users' table already.
		boolean isSuccessed = false;
		
		if (!isWeiboUserExists(uid)) {
			ContentValues values = new ContentValues();
			
			values.put(UsersTable.USER_UID, uid);
			
			values.put(UsersTable.SCREEN_NAME, screenName);
			values.put(UsersTable.NAME, name);
			
			values.put(UsersTable.PROVINCE, province);
			values.put(UsersTable.CITY, city);
			values.put(UsersTable.LOCATION, location);
			
			values.put(UsersTable.DESCRIPTION, description);
			values.put(UsersTable.URL, url);
			values.put(UsersTable.PROFILE_IMAGE_URL, profileImageUrl);
			values.put(UsersTable.AVATAR_LARGE, avatarLarge);
			values.put(UsersTable.DOMAIN, domain);
			values.put(UsersTable.GENDER, gender);
			
			values.put(UsersTable.FOLLOWERS_COUNT, followersCount);
			values.put(UsersTable.FRIENDS_COUNT, friendsCount);
			values.put(UsersTable.STATUSES_COUNT, statusesCount);
			values.put(UsersTable.FAVOURITES_COUNT, favouritesCount);
			values.put(UsersTable.BI_FOLLOWERS_COUNT, biFollowersCount);
			values.put(UsersTable.FOLLOW_ME, followMe);
			values.put(UsersTable.FOLLOWING, following);
			
			values.put(UsersTable.CREATED_AT, createdAt);
			values.put(UsersTable.VERIFIED, verified);
			values.put(UsersTable.VERIFIED_REASON, verifiedReason);
			values.put(UsersTable.GEO_ENABLED, geoEnabled);
			
			values.put(UsersTable.ALLOW_ALL_ACT_MSG, allowAllActMsg);
			values.put(UsersTable.ALLOW_ALL_COMMENT, allowAllComment);
			
			values.put(UsersTable.ONLINE_STATUS, onlineStatus);
			
			isSuccessed = (db.insert(USERS_TABLE, null, values) > 0);
		}
		return isSuccessed;
	}
	
	public void updateWeiboUser(WeiboUser updatedUser) {
		// XXX better way to update user information
		deleteWeiboUser(updatedUser.getUid());
		insertWeiboUser(updatedUser);
	}
	
	public boolean deleteWeiboUser(Long uid) {
		return (db.delete(USERS_TABLE, UsersTable.USER_UID + "=" + uid.toString(), null) > 0);
	}
	
	// 'comment' table
	public boolean insertWeiboComment(WeiboComment comment) {
		return insertWeiboComment(comment.getType(), comment.getId(), comment.getMid(),
				comment.getCreatedAt(), comment.getText(), comment.getSource(),
				comment.getUser(), comment.getStatus(), comment.getReplyComment());
	}
	
	private boolean insertWeiboComment(int type, long id, String mid,
			String createdAt, String text, String source,
			WeiboUser user,
			WeiboStatus status,
			WeiboComment replyComment) {
		ContentValues values = new ContentValues();
		
		values.put(CommentsTable.COMMENT_TYPE, type);
		values.put(CommentsTable.COMMENT_ID, id);
		values.put(CommentsTable.COMMENT_MID, mid);
		
		values.put(CommentsTable.CREATED_AT, createdAt);
		values.put(CommentsTable.TEXT, text);
		values.put(CommentsTable.SOURCE, source);
		
		if (user != null) {
			values.put(UsersTable.USER_UID, user.getUid());
			insertWeiboUser(user);
		}
		
		if (status != null) {
			values.put(StatusesTable.STATUS_ID, status.getId());
			insertWeiboStatus(status);
		}
		
		if (replyComment != null) {
			values.put(CommentsTable.REPLY_COMMENT_ID, replyComment.getId());
			insertWeiboComment(replyComment);
		}
		
		return (db.insert(COMMENTS_TABLE, null, values) > 0);
	}
	
	
	public void updateWeiboComments(ArrayList<WeiboComment> updatedComments, Integer type) {
		deleteWeiboComments(type);
		for (WeiboComment updatedComment : updatedComments) {
			insertWeiboComment(updatedComment);
		}
	}
	
	public boolean deleteWeiboComments(Integer type) {
		ArrayList<WeiboComment>  comments = selectWeiboComments(type);
		ArrayList<WeiboStatus>   statuses = selectWeiboStatuses(WeiboStatus.STATUS_TYPE_COMMENT);
		
		for (WeiboComment comment : comments) {
			for (WeiboStatus status : statuses) {
				if (status == comment.getStatus()) {
					deleteWeiboStatus(status.getId(), WeiboStatus.STATUS_TYPE_COMMENT);
				}
			}
		}
		
		return (db.delete(COMMENTS_TABLE, CommentsTable.COMMENT_TYPE + "=" + type.toString(), null) > 0);
	}
	
	public void deleteWeiboComment(Long id) {
		Long sid = selectWeiboComment(id).getStatus().getId();
		String whereSelection = StatusesTable.STATUS_ID + "=" + sid.toString()
				    + " AND " + StatusesTable.STATUS_TYPE + "=" + WeiboStatus.STATUS_TYPE_COMMENT;
		db.delete(STATUSES_TABLE, whereSelection, null);
		db.delete(COMMENTS_TABLE, CommentsTable.COMMENT_ID + "=" + id.toString(), null);
	}
	
	// 'favorites' table
	public boolean insertWeiboFavorite(WeiboFavorite favorite) {
		return insertWeiboFavorite(favorite.getStatus(), favorite.getFavoritedTime());
	}
	
	private boolean insertWeiboFavorite(WeiboStatus status, String favoritedTime) {
		ContentValues values = new ContentValues();
		
		if (status != null) {
			values.put(StatusesTable.STATUS_ID, status.getId());
			insertWeiboStatus(status);
		}
		
		values.put(FavoritesTable.FAVORITED_TIME, favoritedTime);
		
		return (db.insert(FAVORITES_TABLE, null, values) > 0);
	}
	
	public void updateWeiboFavorites(ArrayList<WeiboFavorite> updatedFavorites) {
		deleteWeiboFavorites();
		for (WeiboFavorite updatedFavorite : updatedFavorites) {
			insertWeiboFavorite(updatedFavorite);
		}
	}
	
	public void deleteWeiboFavorites() {
		for (WeiboFavorite favorite : selectWeiboFavorites()) {
			deleteWeiboFavorite(favorite.getStatus().getId());
		}
	}
	
	public void deleteWeiboFavorite(Long id) {
		String whereSelection = StatusesTable.STATUS_ID + "=" + id.toString()
				+ " AND " + StatusesTable.STATUS_TYPE + "=" + WeiboStatus.STATUS_TYPE_FAVORITE;
		db.delete(STATUSES_TABLE, whereSelection, null);
		
		for (WeiboFavorite favorite : selectWeiboFavorites()) {
				if (favorite.getStatus().getId() == id) {
					db.delete(FAVORITES_TABLE, StatusesTable.STATUS_ID + "=" + id.toString(), null);
				}
		}
	}
	
	public ArrayList<WeiboStatus> selectWeiboStatuses(Integer type) {
		ArrayList<WeiboStatus> statuses = new ArrayList<WeiboStatus>();
		
		try {
			Cursor cursor = db.query(STATUSES_TABLE, StatusesTable.QUERY_COLUMNS,
					StatusesTable.STATUS_TYPE + "=" + type.toString(),
					null, null, null, null);
			
			while (cursor.moveToNext()) {
				WeiboStatus status = new WeiboStatus();
				
				long uid = cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID));
				if (uid != 0) {
					WeiboUser user = selectWeiboUser(uid);
					status.setUser(user);
				}
				
				long retweetedStatusId = cursor.getLong(cursor.getColumnIndex(StatusesTable.RETWEETED_STATUS_ID));
				
				if (retweetedStatusId != 0) {
					WeiboStatus retweetedStatus = selectWeiboStatus(retweetedStatusId, WeiboStatus.STATUS_TYPE_RETWEET);
					status.setRetweetedStatus(retweetedStatus);
				}
				
				status.setType(cursor.getInt(cursor.getColumnIndex(StatusesTable.STATUS_TYPE)));
				status.setId(cursor.getLong(cursor.getColumnIndex(StatusesTable.STATUS_ID)));
				
				status.setCreatedAt(cursor.getString(cursor.getColumnIndex(StatusesTable.CREATED_AT)));
				status.setText(cursor.getString(cursor.getColumnIndex(StatusesTable.TEXT)));
				
				String thumbnailPic = cursor.getString(cursor.getColumnIndex(StatusesTable.THUMBNAIL_PIC));
				if (thumbnailPic != null) {
					status.setThumbnailPic(thumbnailPic);
				}
				
				status.setSource(cursor.getString(cursor.getColumnIndex(StatusesTable.SOURCE)));
				
				status.setRepostsCount(cursor.getInt(cursor.getColumnIndex(StatusesTable.REPOSTS_COUNT)));
				status.setCommentsCount(cursor.getInt(cursor.getColumnIndex(StatusesTable.COMMENTS_COUNT)));
				
				statuses.add(status);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return statuses;
	}
	
	public WeiboStatus selectWeiboStatus(Long id, Integer type) {
		WeiboStatus status = null;
		String whereSelection = StatusesTable.STATUS_ID + "=" + id.toString()
				+ " AND " + StatusesTable.STATUS_TYPE + "=" + type.toString();
		
		try {
			Cursor cursor = db.query(STATUSES_TABLE, StatusesTable.QUERY_COLUMNS, whereSelection,
					null, null, null, null);
			
			if(!cursor.moveToFirst()) {
				cursor.close();
				return status;
			}
			
			status = new WeiboStatus();
			
			long uid = cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID));
			if (uid != 0) {
				WeiboUser user = selectWeiboUser(uid);
				status.setUser(user);
			}
			
			long retweetedStatusId = cursor.getLong(cursor.getColumnIndex(StatusesTable.RETWEETED_STATUS_ID));
			if (retweetedStatusId != 0) {
				WeiboStatus retweetedStatus = selectWeiboStatus(retweetedStatusId, WeiboStatus.STATUS_TYPE_RETWEET);
				status.setRetweetedStatus(retweetedStatus);
			}
			
			status.setType(cursor.getInt(cursor.getColumnIndex(StatusesTable.STATUS_TYPE)));
			status.setId(cursor.getLong(cursor.getColumnIndex(StatusesTable.STATUS_ID)));
			
			status.setCreatedAt(cursor.getString(cursor.getColumnIndex(StatusesTable.CREATED_AT)));
			status.setText(cursor.getString(cursor.getColumnIndex(StatusesTable.TEXT)));
			
			String thumbnailPic = cursor.getString(cursor.getColumnIndex(StatusesTable.THUMBNAIL_PIC));
			if (thumbnailPic != null) {
				status.setThumbnailPic(thumbnailPic);
			}
			
			status.setSource(cursor.getString(cursor.getColumnIndex(StatusesTable.SOURCE)));
			
			status.setRepostsCount(cursor.getInt(cursor.getColumnIndex(StatusesTable.REPOSTS_COUNT)));
			status.setCommentsCount(cursor.getInt(cursor.getColumnIndex(StatusesTable.COMMENTS_COUNT)));
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return status;
	}
	
	public ArrayList<WeiboUser> selectWeiboUsers(Integer type) {
		ArrayList<WeiboUser> users = new ArrayList<WeiboUser>();
		String whereSelection = null;
		if (type == WeiboUser.USER_TYPE_FOLLOWING) {
			whereSelection = UsersTable.FOLLOWING + "=" + "1";
		} else if (type == WeiboUser.USER_TYPE_FOLLOWME) {
			whereSelection = UsersTable.FOLLOW_ME + "=" + "1";
		} else if (type == WeiboUser.USER_TYPE_BI_FOLLOWING) {
			whereSelection = UsersTable.FOLLOWING + "=" + "1" + " AND " + UsersTable.FOLLOW_ME + "=" + "1";
		}
		
		try {
			Cursor cursor = db.query(USERS_TABLE, UsersTable.QUERY_COLUMNS, whereSelection,
					null, null, null, null);
			
			while (cursor.moveToNext()) {
				WeiboUser user = new WeiboUser();
				
				user.setUid(cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID)));
				
				user.setScreenName(cursor.getString(cursor.getColumnIndex(UsersTable.SCREEN_NAME)));
				user.setName(cursor.getString(cursor.getColumnIndex(UsersTable.NAME)));
				
				user.setDescription(cursor.getString(cursor.getColumnIndex(UsersTable.DESCRIPTION)));
				user.setUrl(cursor.getString(cursor.getColumnIndex(UsersTable.URL)));
				user.setProfileImageUrl(cursor.getString(cursor.getColumnIndex(UsersTable.PROFILE_IMAGE_URL)));
				
				user.setGender(cursor.getString(cursor.getColumnIndex(UsersTable.GENDER)));
				user.setLocation(cursor.getString(cursor.getColumnIndex(UsersTable.LOCATION)));
				
				user.setFollowersCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FOLLOWERS_COUNT)));
				user.setFriendsCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FRIENDS_COUNT)));
				user.setStatusesCount(cursor.getInt(cursor.getColumnIndex(UsersTable.STATUSES_COUNT)));
				user.setFavouritesCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FAVOURITES_COUNT)));
				
				// XXX better ways to set social status
				if (cursor.getString(cursor.getColumnIndex(UsersTable.FOLLOWING)).equals("1")) {
					user.setFollowing(true);
				} else {
					user.setFollowing(false);
				}
				
				if (cursor.getString(cursor.getColumnIndex(UsersTable.FOLLOW_ME)).equals("1")) {
					user.setFollowMe(true);
				} else {
					user.setFollowMe(false);
				}
				
				users.add(user);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return users;
	}
	
	public WeiboUser selectWeiboUser(Long uid) {
		WeiboUser user = null;
		
		try {
			Cursor cursor = db.query(USERS_TABLE, UsersTable.QUERY_COLUMNS,
					UsersTable.USER_UID + "=" + uid.toString(),
					null, null, null, null);
			
			if(!cursor.moveToFirst()) {
				cursor.close();
				return user;
			}
			
			user = new WeiboUser();
			
			user.setUid(cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID)));
			
			user.setScreenName(cursor.getString(cursor.getColumnIndex(UsersTable.SCREEN_NAME)));
			user.setName(cursor.getString(cursor.getColumnIndex(UsersTable.NAME)));
			
			user.setDescription(cursor.getString(cursor.getColumnIndex(UsersTable.DESCRIPTION)));
			user.setUrl(cursor.getString(cursor.getColumnIndex(UsersTable.URL)));
			user.setProfileImageUrl(cursor.getString(cursor.getColumnIndex(UsersTable.PROFILE_IMAGE_URL)));
			
			user.setGender(cursor.getString(cursor.getColumnIndex(UsersTable.GENDER)));
			user.setLocation(cursor.getString(cursor.getColumnIndex(UsersTable.LOCATION)));
			
			user.setFollowersCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FOLLOWERS_COUNT)));
			user.setFriendsCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FRIENDS_COUNT)));
			user.setStatusesCount(cursor.getInt(cursor.getColumnIndex(UsersTable.STATUSES_COUNT)));
			user.setFavouritesCount(cursor.getInt(cursor.getColumnIndex(UsersTable.FAVOURITES_COUNT)));
			
			// XXX better ways to set social status
			if (cursor.getString(cursor.getColumnIndex(UsersTable.FOLLOWING)).equals("1")) {
				user.setFollowing(true);
			} else {
				user.setFollowing(false);
			}
			
			if (cursor.getString(cursor.getColumnIndex(UsersTable.FOLLOW_ME)).equals("1")) {
				user.setFollowMe(true);
			} else {
				user.setFollowMe(false);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return user;
	}
	
	public ArrayList<WeiboComment> selectWeiboComments(Integer type) {
		ArrayList<WeiboComment> comments = new ArrayList<WeiboComment>();
		
		try {
			Cursor cursor = db.query(COMMENTS_TABLE, CommentsTable.QUERY_COLUMNS,
					CommentsTable.COMMENT_TYPE + "=" + type.toString(),
					null, null, null, null);
			
			while (cursor.moveToNext()) {
				WeiboComment comment = new WeiboComment();
				
				comment.setType(cursor.getInt(cursor.getColumnIndex(CommentsTable.COMMENT_TYPE)));
				comment.setId(cursor.getLong(cursor.getColumnIndex(CommentsTable.COMMENT_ID)));
				
				comment.setCreatedAt(cursor.getString(cursor.getColumnIndex(CommentsTable.CREATED_AT)));
				comment.setText(cursor.getString(cursor.getColumnIndex(CommentsTable.TEXT)));
				comment.setSource(cursor.getString(cursor.getColumnIndex(CommentsTable.SOURCE)));
				
				long uid = cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID));
				WeiboUser user = selectWeiboUser(uid);
				comment.setUser(user);
				
				long id = cursor.getLong(cursor.getColumnIndex(StatusesTable.STATUS_ID));
				WeiboStatus status = selectWeiboStatus(id, WeiboStatus.STATUS_TYPE_COMMENT);
				comment.setStatus(status);
				
				long commentId = cursor.getLong(cursor.getColumnIndex(CommentsTable.REPLY_COMMENT_ID));
				if (commentId != 0) {
					WeiboComment replyComment = selectWeiboComment(commentId);
					comment.setReplyComment(replyComment);
				}
				
				comments.add(comment);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return comments;
	}
	
	public WeiboComment selectWeiboComment(Long id) {
		WeiboComment comment = null;
		
		try {
			Cursor cursor = db.query(COMMENTS_TABLE, CommentsTable.QUERY_COLUMNS,
					CommentsTable.COMMENT_ID + "=" + id.toString(),
					null, null, null, null);
			
			if(!cursor.moveToFirst()) {
				cursor.close();
				return comment;
			}
			
			comment = new WeiboComment();
			
			comment.setType(cursor.getInt(cursor.getColumnIndex(CommentsTable.COMMENT_TYPE)));
			comment.setId(cursor.getLong(cursor.getColumnIndex(CommentsTable.COMMENT_ID)));
			
			comment.setCreatedAt(cursor.getString(cursor.getColumnIndex(CommentsTable.CREATED_AT)));
			comment.setText(cursor.getString(cursor.getColumnIndex(CommentsTable.TEXT)));
			comment.setSource(cursor.getString(cursor.getColumnIndex(CommentsTable.SOURCE)));
			
			long uid = cursor.getLong(cursor.getColumnIndex(UsersTable.USER_UID));
			WeiboUser user = selectWeiboUser(uid);
			comment.setUser(user);
			
			long statusId = cursor.getLong(cursor.getColumnIndex(StatusesTable.STATUS_ID));
			WeiboStatus status = selectWeiboStatus(statusId, WeiboStatus.STATUS_TYPE_COMMENT);
			comment.setStatus(status);
			
			long commentId = cursor.getLong(cursor.getColumnIndex(CommentsTable.REPLY_COMMENT_ID));
			if (commentId != 0) {
				WeiboComment replyComment = selectWeiboComment(commentId);
				comment.setReplyComment(replyComment);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return comment;
	}
	
	public ArrayList<WeiboFavorite> selectWeiboFavorites() {
		ArrayList<WeiboFavorite> favorites = new ArrayList<WeiboFavorite>();
		
		try {
			Cursor cursor = db.query(FAVORITES_TABLE, FavoritesTable.FAVORITES_TABLE_QUERY_COLUMNS,
					null, null, null, null, null);
			
			while (cursor.moveToNext()) {
				WeiboFavorite favorite = new WeiboFavorite();
				
				long id = cursor.getLong(cursor.getColumnIndex(StatusesTable.STATUS_ID));
				WeiboStatus status = selectWeiboStatus(id, WeiboStatus.STATUS_TYPE_FAVORITE);
				favorite.setStatus(status);
				
				favorite.setFavoritedTime(cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITED_TIME)));
				
				favorites.add(favorite);
			}
			
			cursor.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return favorites;
	}
	
}