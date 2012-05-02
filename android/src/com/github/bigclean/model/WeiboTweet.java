package com.github.bigclean.model;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboTweet {
	private String  createdAt;
	private long    mid;
	private String  text;
	
	private String  source;
	private boolean favorited;
	private boolean truncated;
	
	private String  inReplyToStatusId;
	private String  inReplyToUserId;
	private String  inReplyToScreenName;
	
	private String  geo;
	private WeiboTweetUser user;
	
	WeiboTweet() {
		super();
	}
	
	WeiboTweet(JSONObject jsonWeiboTweet) throws Exception {
		init(jsonWeiboTweet);
	}
	
	private void init(JSONObject jsonWeiboTweet) throws Exception {
		try {
			createdAt = jsonWeiboTweet.getString("created_at");
			mid = jsonWeiboTweet.getLong("id");
			text = jsonWeiboTweet.getString("text");

			source = jsonWeiboTweet.getString("source");
			favorited = jsonWeiboTweet.getBoolean("favorited");
			truncated = jsonWeiboTweet.getBoolean("truncated");
			
			inReplyToStatusId = jsonWeiboTweet.getString("in_reply_to_status_id");
			inReplyToUserId = jsonWeiboTweet.getString("in_reply_to_user_id");
			inReplyToScreenName = jsonWeiboTweet.getString("in_reply_to_screen_name");
			
			geo = jsonWeiboTweet.getString("geo");
			user = new WeiboTweetUser(jsonWeiboTweet.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "WeiboTweet [createdAt=" + createdAt + ", mid=" + mid
				+ ", text=" + text + ", source=" + source + ", favorited="
				+ favorited + ", truncated=" + truncated
				+ ", inReplyToStatusId=" + inReplyToStatusId
				+ ", inReplayToUserId=" + inReplyToUserId
				+ ", inReplyToScreenName=" + inReplyToScreenName + ", geo="
				+ geo + ", user=" + user + "]";
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public long getMid() {
		return mid;
	}

	public String getText() {
		return text;
	}

	public String getSource() {
		return source;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public String getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public String getInReplayToUserId() {
		return inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public String getGeo() {
		return geo;
	}

	public WeiboTweetUser getUser() {
		return user;
	}

}
