package com.github.bigclean.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class WeiboTweetStream {
	private List<WeiboTweet> tweets = new ArrayList<WeiboTweet>();
	
	public WeiboTweetStream() {
		super();
	}
	
	public WeiboTweetStream(JSONArray jsonWeiboTweetStream) {
		for (int i = 0; i < jsonWeiboTweetStream.length(); i++) {
			WeiboTweet tweet;
			try {
				tweet = new WeiboTweet(jsonWeiboTweetStream.getJSONObject(i));
				tweets.add(tweet);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<WeiboTweet> getTweets() {
		return tweets;
	}
	
}
