package com.github.bigclean.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.bigclean.R;
import com.github.bigclean.model.WeiboComment;

public class StatusCommentsListAdapter extends BaseAdapter {
	private List<WeiboComment> comments = null;
	private Context context = null;
	
	public StatusCommentsListAdapter(ArrayList<WeiboComment> comments, Context context) {
		this.comments = comments;
		this.context  = context;
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StatusCommentViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.status_comment_row, null);
			
			holder = new StatusCommentViewHolder();
			
			holder.userNameView         = (TextView)  convertView.findViewById(R.id.status_comment_user_name);
			holder.commentTimestampView = (TextView)  convertView.findViewById(R.id.status_comment_timestamp);
			holder.commentTweetView     = (TextView)  convertView.findViewById(R.id.status_comment_text);
			
			convertView.setTag(holder);
		} else {
			holder = (StatusCommentViewHolder) convertView.getTag();
		}
			
		WeiboComment currentComment = (WeiboComment) getItem(position);
			
		holder.userNameView.setText(currentComment.getUser().getName());
		holder.userNameView.setTextColor(Color.BLUE);
		
		// XXX hard hacks about timestamp
		String commentCreatedAt = currentComment.getCreatedAt();
		holder.commentTimestampView.setText("On " + commentCreatedAt.substring(0, 3) + " " + commentCreatedAt.substring(11, 19));
		holder.commentTimestampView.setTextColor(Color.BLUE);
		
		holder.commentTweetView.setText(currentComment.getText());
		holder.commentTweetView.setTextColor(Color.DKGRAY);
		
		return convertView;
	}
	
	private static class StatusCommentViewHolder {
		public TextView  userNameView;
		public TextView  commentTimestampView;
		public TextView  commentTweetView;
	}

}