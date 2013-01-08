package com.github.bigclean.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiboComment {
	public static final int COMMENT_TYPE_BY_ME    = 1;
	public static final int COMMENT_TYPE_TO_ME    = 2;
	public static final int COMMENT_TYPE_MENTIONS = 3;
	// extra fields
	public static final int COMMENT_TYPE_REPLY_TO = 4;
	
	private int    type;
	private long   id;
	private String mid;
	
	private String createdAt;
	private String text; // comment text
	private String source;
	
	private WeiboUser    user;
	private WeiboStatus  status; // commented tweet
	private WeiboComment replyComment;
	
	public WeiboComment() { }
	
	public WeiboComment(JSONObject jsonWeiboComment) throws Exception {
		init(jsonWeiboComment);
	}
	
	private void init(JSONObject jsonWeiboComment) throws Exception {
		try {
			id        = jsonWeiboComment.getLong("id");
			mid       = jsonWeiboComment.getString("mid");
			
			createdAt = jsonWeiboComment.getString("created_at");
			text      = jsonWeiboComment.getString("text");
			source    = jsonWeiboComment.getString("source");
			
			if (!jsonWeiboComment.isNull("user")) {
				user      = new WeiboUser(jsonWeiboComment.getJSONObject("user"));
			}
			
			if (!jsonWeiboComment.isNull("status")) {
				status    = new WeiboStatus(jsonWeiboComment.getJSONObject("status"));
				status.setType(WeiboStatus.STATUS_TYPE_COMMENT);
			}
			
			if (!jsonWeiboComment.isNull("reply_comment")) {
				replyComment = new WeiboComment(jsonWeiboComment.getJSONObject("reply_comment"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
	
	public static WeiboCommentWrapper initWeiboCommentWrapper(JSONObject jsonResponse) throws Exception {
		JSONArray      jsonComments    = null;
		WeiboCommentWrapper wrapper    = null;
		
		try {
			if (!jsonResponse.isNull("comments")) {
				jsonComments = jsonResponse.getJSONArray("comments");
			}
			
			int size = jsonComments.length();
			ArrayList<WeiboComment> comments = new ArrayList<WeiboComment>(size);
			for(int i = 0; i < size; i++) {
				comments.add(new WeiboComment(jsonComments.getJSONObject(i)));
			}
			
			long previousCursor = jsonResponse.getLong("previous_cursor");
			long nextCursor     = jsonResponse.getLong("next_cursor");
			int  totalNumber    = jsonResponse.getInt("total_number");
			
			wrapper = new WeiboCommentWrapper(comments, previousCursor, nextCursor, totalNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return wrapper;
	}

	public static void setWeiboCommentsType(ArrayList<WeiboComment> comments, int type) {
		for (WeiboComment comment : comments) {
			comment.setType(type);
			if (comment.getReplyComment() != null) {
				comment.getReplyComment().setType(type);
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
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

	public WeiboUser getUser() {
		return user;
	}

	public void setUser(WeiboUser user) {
		this.user = user;
	}

	public WeiboStatus getStatus() {
		return status;
	}

	public void setStatus(WeiboStatus status) {
		this.status = status;
	}

	public WeiboComment getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(WeiboComment replyComment) {
		this.replyComment = replyComment;
	}
}