package com.github.bigclean.activity;

import org.json.JSONArray;
import org.json.JSONException;

import com.github.bigclean.R;
import com.github.bigclean.adapter.TimelineAdapter;
import com.github.bigclean.model.WeiboTweetStream;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class TimelineActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_basic);
		
		Intent receivedIntent = getIntent();
		String timelineTweets = receivedIntent.getStringExtra("friends_timeline");
		
		TimelineAdapter timelineAdapter;
		try {
			timelineAdapter = new TimelineAdapter(
					new WeiboTweetStream(new JSONArray(timelineTweets)),
					this
					);
			ListView listView = getListView();
			listView.setAdapter(timelineAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
