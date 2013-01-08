package com.github.bigclean.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import oauth.signpost.OAuth;

import com.github.bigclean.R;
import com.github.bigclean.oauth.WeiboOAuthKeys;
import com.github.bigclean.weibo.Weibo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TinywaveActivity extends Activity {
	private SharedPreferences weiboPrefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        weiboPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Button tweetButton    = (Button) findViewById(R.id.tweet_button);
        Button loginButton    = (Button) findViewById(R.id.login_button);
        Button exploreTimelineButton    = (Button) findViewById(R.id.explore_timeline_button);
        Button exploreCommentButton     = (Button) findViewById(R.id.explore_comment_button);
        Button exploreFavoriteButton    = (Button) findViewById(R.id.explore_favorite_button);
        Button exploreFriendButton      = (Button) findViewById(R.id.explore_friend_button);

        loginButton.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View arg0) {
        		startActivity(new Intent(TinywaveActivity.this, LoginActivity.class));
        	}
        });

        tweetButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Weibo weiboClient = new Weibo(
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN, ""),
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN_SECRET, "")
        				);
		
        		String postStatus = "Tweets from dummy Weibo androic client.";
        		String updateStatusUrl = "http://api.t.sina.com.cn/statuses/update.json";
        		List<NameValuePair> updateStatus = new ArrayList<NameValuePair>();
        		updateStatus.add(new BasicNameValuePair("source", WeiboOAuthKeys.CONSUMER_SECRET));
        		updateStatus.add(new BasicNameValuePair("status", postStatus));
		
        		weiboClient.sendPostRequest(updateStatusUrl, updateStatus);
        	}
	
        });

        exploreTimelineButton.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		startActivity(new Intent(TinywaveActivity.this, ExploreTimelineActivity.class));
        	}
        });

        exploreCommentButton.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		startActivity(new Intent(TinywaveActivity.this, ExploreCommentActivity.class));
        	}
        });

        exploreFavoriteButton.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		startActivity(new Intent(TinywaveActivity.this, ExploreFavoriteActivity.class));
        	}
        });

        exploreFriendButton.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		startActivity(new Intent(TinywaveActivity.this, ExploreFriendActivity.class));
        	}
        });

    }

}
