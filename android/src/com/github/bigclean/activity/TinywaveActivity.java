package com.github.bigclean.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import oauth.signpost.OAuth;

import com.github.bigclean.R;
import com.github.bigclean.Timeline;
import com.github.bigclean.Weibo;
import com.github.bigclean.oauth.WeiboOAuthKeys;

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

        Button timeline = (Button) findViewById(R.id.timeline);
        Button tweet = (Button) findViewById(R.id.tweet);

        Button btn=(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
	        	startActivity(new Intent(TinywaveActivity.this, LoginActivity.class));
			}
        });

        tweet.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Weibo weiboClient = new Weibo(
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN, ""),
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN_SECRET, "")
        				);
		
        		String postStatus = "Test tweet from tinywave...";
        		String updateStatusUrl = "http://api.t.sina.com.cn/statuses/update.json";
        		List<NameValuePair> updateStatus = new ArrayList<NameValuePair>();
        		updateStatus.add(new BasicNameValuePair("source", WeiboOAuthKeys.CONSUMER_SECRET));
        		updateStatus.add(new BasicNameValuePair("status", postStatus));
		
        		weiboClient.sendPostRequest(updateStatusUrl, updateStatus);
        	}
	
        });

        timeline.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Weibo weiboClient = new Weibo(
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN, ""),
        				weiboPrefs.getString(OAuth.OAUTH_TOKEN_SECRET, "")
        				);
		
        		Intent weiboTimelineIntent = new Intent();
        		weiboTimelineIntent.putExtra("friends_timeline",
        				weiboClient.sendGetRequest(Timeline.getFriendsTimeline()));
        		weiboTimelineIntent.setClass(TinywaveActivity.this, TimelineActivity.class);
		
        		TinywaveActivity.this.startActivity(weiboTimelineIntent);
        	}
        });
    }

}
