package com.github.bigclean.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A data class representing one single status of a user
 * @author Bigclean Cheng
 */
public class WeiboStatus {
	public static final int STATUS_TYPE_PUBLIC   = 1;
	public static final int STATUS_TYPE_HOME     = 2;
	public static final int STATUS_TYPE_USER     = 3;
	public static final int STATUS_TYPE_MENTIONS = 4;
	// extra fields to handle databases
	public static final int STATUS_TYPE_COMMENT  = 5;
	public static final int STATUS_TYPE_FAVORITE = 6;
	public static final int STATUS_TYPE_RETWEET  = 7;
	
	private int     type; // extra field to present status type
	
	private long    id;
	private long    mid;
	
	private String  createdAt;
	private String  text;
	private String  source;
	
	private boolean favorited;
	private boolean truncated;
	
	private String  inReplyToStatusId;
	private String  inReplyToUserId;
	private String  inReplyToScreenName;
	
	private String thumbnailPic;
	private String bmiddlePic;
	private String originalPic;
	
	//private ArrayList<String> annotations;
	private int repostsCount;
	private int commentsCount;
	
	// 'user' field is available in timeline
	private WeiboUser   user = null;
	private WeiboStatus retweetedStatus = null;
	
	public WeiboStatus() { }
	
	public WeiboStatus(JSONObject jsonWeiboStatus) throws Exception {
		init(jsonWeiboStatus);
	}
	
	private void init(JSONObject jsonWeiboStatus) throws Exception {
		try {
			id        = jsonWeiboStatus.getLong("id");
			mid       = jsonWeiboStatus.getLong("mid");

			createdAt = jsonWeiboStatus.getString("created_at");
			text      = jsonWeiboStatus.getString("text");
			source    = jsonWeiboStatus.getString("source");
			
			favorited = jsonWeiboStatus.getBoolean("favorited");
			truncated = jsonWeiboStatus.getBoolean("truncated");
			
			inReplyToStatusId   = jsonWeiboStatus.getString("in_reply_to_status_id");
			inReplyToUserId     = jsonWeiboStatus.getString("in_reply_to_user_id");
			inReplyToScreenName = jsonWeiboStatus.getString("in_reply_to_screen_name");
			
			if (!jsonWeiboStatus.isNull("thumbnail_pic")) {
				thumbnailPic = jsonWeiboStatus.getString("thumbnail_pic");
			}
			
			if (!jsonWeiboStatus.isNull("bmiddle_pic")) {
				bmiddlePic   = jsonWeiboStatus.getString("bmiddle_pic");
			}
			
			if (!jsonWeiboStatus.isNull("original_pic")) {
				originalPic  = jsonWeiboStatus.getString("original_pic");
			}
			
			repostsCount  = jsonWeiboStatus.getInt("reposts_count");
			commentsCount = jsonWeiboStatus.getInt("comments_count");
			
			if (!jsonWeiboStatus.isNull("user")) {
				user      = new WeiboUser(jsonWeiboStatus.getJSONObject("user"));
			}
			
			if (!jsonWeiboStatus.isNull("retweeted_status")) {
				retweetedStatus = new WeiboStatus(jsonWeiboStatus.getJSONObject("retweeted_status"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static WeiboStatusWrapper initWeiboStatusWrapper(JSONObject jsonResponse) throws Exception {
		JSONArray          jsonStatuses    = null;
		WeiboStatusWrapper wrapper         = null;
		
		try {
			if (!jsonResponse.isNull("statuses")) {
				jsonStatuses = jsonResponse.getJSONArray("statuses");
			}
			
			int size = jsonStatuses.length();
			ArrayList<WeiboStatus> statuses = new ArrayList<WeiboStatus>(size);
			for(int i = 0; i < size; i++) {
				statuses.add(new WeiboStatus(jsonStatuses.getJSONObject(i)));
			}
			
			long previousCursor = jsonResponse.getLong("previous_cursor");
			long nextCursor     = jsonResponse.getLong("next_cursor");
			int  totalNumber    = jsonResponse.getInt("total_number");
			
			wrapper = new WeiboStatusWrapper(statuses, previousCursor, nextCursor, totalNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return wrapper;
	}
	
	public static void setWeiboStatusesType(ArrayList<WeiboStatus> statuses, int type) {
		for (WeiboStatus status : statuses) {
			status.setType(type);
			if (status.getRetweetedStatus() != null) {
				status.getRetweetedStatus().setType(STATUS_TYPE_RETWEET);
			}
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public String getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(String inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public String getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(String inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	public String getThumbnailPic() {
		return thumbnailPic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public String getBmiddlePic() {
		return bmiddlePic;
	}

	public void setBmiddlePic(String bmiddlePic) {
		this.bmiddlePic = bmiddlePic;
	}

	public String getOriginalPic() {
		return originalPic;
	}

	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	public int getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(int repostsCount) {
		this.repostsCount = repostsCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public WeiboUser getUser() {
		return user;
	}

	public void setUser(WeiboUser user) {
		this.user = user;
	}

	public WeiboStatus getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(WeiboStatus retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}
	
}