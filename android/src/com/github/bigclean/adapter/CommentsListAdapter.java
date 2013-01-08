package com.github.bigclean.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bigclean.R;
import com.github.bigclean.model.WeiboComment;
import com.github.bigclean.util.AsyncImageLoader;
import com.github.bigclean.util.ImageCallbackImpl;

public class CommentsListAdapter extends BaseAdapter {
	private List<WeiboComment> comments = null;
	private AsyncImageLoader   loader   = new AsyncImageLoader();
	private Context context = null;
	
	public CommentsListAdapter(ArrayList<WeiboComment> comments, Context context) {
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
		CommentViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.comment_row, null);
			
			holder = new CommentViewHolder();
			
			holder.userAvatarView    = (ImageView) convertView.findViewById(R.id.comment_user_avatar);
			holder.userNameView      = (TextView)  convertView.findViewById(R.id.comment_user_name);
			
			holder.commentTweetView   = (TextView)  convertView.findViewById(R.id.comment_text);
			holder.commentTimestampView = (TextView)  convertView.findViewById(R.id.comment_timestamp);
			
			holder.statusTweetView     = (TextView) convertView.findViewById(R.id.comment_status_text);
			holder.statusThumbnailView = (ImageView) convertView.findViewById(R.id.comment_status_thumbnail);
			
			holder.replyCommentTweetView    = (TextView) convertView.findViewById(R.id.comment_reply_comment_text);
			
			convertView.setTag(holder);
		} else {
			holder = (CommentViewHolder) convertView.getTag();
		}
			
		WeiboComment currentComment = (WeiboComment) getItem(position);
		
		setupUserLayout(holder, currentComment);
		setupCommentLayout(holder, currentComment);
		setupStatusLayout(holder, currentComment);
		setupReplyCommentLayout(holder, currentComment);
		
		return convertView;
	}
	
	private void setupUserLayout(CommentViewHolder holder, WeiboComment comment) {
		if (comment.getUser() != null) {
			// async image loader
			String userAvatarUrl = comment.getUser().getProfileImageUrl();
			ImageCallbackImpl userAvatarCallbackImpl = new ImageCallbackImpl(holder.userAvatarView);
			Drawable userAvatarCacheImage = loader.loadDrawable(userAvatarUrl, userAvatarCallbackImpl);
			if (userAvatarCacheImage != null) {
				holder.userAvatarView.setImageDrawable(userAvatarCacheImage);
			}
			
			holder.userNameView.setText(comment.getUser().getName());
			holder.userNameView.setTextColor(Color.BLUE);
		}
	}
	
	private void setupCommentLayout(CommentViewHolder holder, WeiboComment comment) {
		holder.commentTweetView.setText(comment.getText());
		holder.commentTweetView.setTextColor(Color.DKGRAY);
		// XXX hard hacks about timestamp
		String createdAt = comment.getCreatedAt();
		holder.commentTimestampView.setText("On " + createdAt.substring(0, 3) + " " + createdAt.substring(11, 19));
		holder.commentTimestampView.setTextColor(Color.BLUE);
	}
	
	private void setupStatusLayout(CommentViewHolder holder, WeiboComment comment) {
		if (comment.getStatus() != null) {
			holder.statusTweetView.setVisibility(View.VISIBLE);
			holder.statusTweetView.setTextColor(Color.LTGRAY);
			holder.statusTweetView.setText(comment.getStatus().getText());
			
			if (comment.getStatus().getThumbnailPic() != null) {
				holder.statusThumbnailView.setVisibility(View.VISIBLE);
				
				String thumbnailPicUrl = comment.getStatus().getThumbnailPic();
				ImageCallbackImpl thumbnailCallbackImpl = new ImageCallbackImpl(holder.statusThumbnailView);
				Drawable thumbnailCacheImage = loader.loadDrawable(thumbnailPicUrl, thumbnailCallbackImpl);
				if (thumbnailCacheImage != null) {
					holder.statusThumbnailView.setImageDrawable(thumbnailCacheImage);
				}
			}
		}
	}
	
	private void setupReplyCommentLayout(CommentViewHolder holder, WeiboComment comment) {
		if (comment.getReplyComment() != null) {
			holder.replyCommentTweetView.setTextColor(Color.GREEN);
			holder.replyCommentTweetView.setText(comment.getReplyComment().getText());
		}
	}
	
	private static class CommentViewHolder {
		public ImageView userAvatarView;
		public TextView  userNameView;
		
		public TextView  commentTweetView;
		public TextView  commentTimestampView;
		
		public TextView  statusTweetView;
		public ImageView statusThumbnailView;
		
		public TextView  replyCommentTweetView;
	}
	
}