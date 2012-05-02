package com.github.bigclean.weibo;

import org.apache.http.client.methods.HttpGet;

import android.net.Uri;

public class Timeline {
	public static final String STATUSES_SLEF_TIMELINE_URL    = "http://api.t.sina.com.cn/statuses/user_timeline.json";
	public static final String STATUSES_FRIENDS_TIMELINE_URL = "http://api.t.sina.com.cn/statuses/friends_timeline.json";

	public static HttpGet getSelfTimeline() {
		return getSelfTimelineAtomic(0, 0, 0);
	}
	
	public static HttpGet getSelfTimelineAtomic(long sinceId, long maxId, int page) {
		return getTimeline(STATUSES_SLEF_TIMELINE_URL, sinceId, maxId, page);
	}
	
	public static HttpGet getFriendsTimeline() {
		return getFriendsTimelineAtomic(0, 0, 0);
	}
	
	public static HttpGet getFriendsTimelineAtomic(long sinceId, long maxId, int page) {
		return getTimeline(STATUSES_FRIENDS_TIMELINE_URL, sinceId, maxId, page);
	}
	
	private static HttpGet getTimeline(String url,
			long sinceId, long maxId, int page) {
		Uri uri= Uri.parse(url);
		Uri.Builder builder = uri.buildUpon();
		
		if (sinceId != 0) {
			builder.appendQueryParameter("since_id", String.valueOf(sinceId));
		} else if (maxId != 0) {
			builder.appendQueryParameter("max_id", String.valueOf(maxId));
		}
		
		if (page != 0) {
			builder.appendQueryParameter("page", String.valueOf(page));
		}
		
		return new HttpGet(builder.build().toString());
	}
	
}
