package com.github.bigclean.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.bigclean.R;
import com.github.bigclean.model.WeiboTweet;
import com.github.bigclean.model.WeiboTweetStream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimelineAdapter extends BaseAdapter {
	private List<WeiboTweet> weiboTweets = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	
	public TimelineAdapter() {
	}
	
	public TimelineAdapter(WeiboTweetStream stream, Context context) {
		this.weiboTweets = stream.getTweets();
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return weiboTweets.size();
	}

	@Override
	public Object getItem(int position) {
		return weiboTweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		// Maybe message id could be better to act as id?
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = rowViews.get(position);
		if (rowView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.timeline, null); // using timeline.xml to generate View dynamically
			TextView nameView  = (TextView) rowView.findViewById(R.id.nameView);
			TextView tweetView = (TextView) rowView.findViewById(R.id.tweetView);
			
			WeiboTweet currentTweet = (WeiboTweet) getItem(position);
			nameView.setText(currentTweet.getUser().getName());
			if (currentTweet.getRetweetedTweet() != null) {
				String originalText = currentTweet.getRetweetedTweet().getText();
				String assembledText = "转载：" + originalText;
				tweetView.setText(assembledText);
			} else {
				tweetView.setText(currentTweet.getText());
			}
			
			rowViews.put(position, rowView);
		}
		return rowView;
	}

}
