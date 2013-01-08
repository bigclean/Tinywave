package com.github.bigclean.adapter;

import java.util.ArrayList;
import java.util.List;

import com.github.bigclean.R;
import com.github.bigclean.model.WeiboStatus;
import com.github.bigclean.util.AsyncImageLoader;
import com.github.bigclean.util.ImageCallbackImpl;
import com.github.bigclean.util.FormatString;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO update using View Holder mode
 * @author Bigclean Cheng
 *
 */
public class TimelineAdapter extends BaseAdapter {
	private List<WeiboStatus>  statuses = null;
	private SparseArray<View>  rowViews = new SparseArray<View>();
	private AsyncImageLoader   loader   = new AsyncImageLoader();
	private Context context = null;
	
	public TimelineAdapter() {
	}
	
	public TimelineAdapter(ArrayList<WeiboStatus> statuses, Context context) {
		this.statuses = statuses;
		this.context  = context;
	}
	
	@Override
	public int getCount() {
		return statuses.size();
	}

	@Override
	public Object getItem(int position) {
		return statuses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (rowViews == null) {
			rowViews = new SparseArray<View>(getCount());
		}
		
		View rowView = rowViews.get(position);
		if (rowView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.timeline_row, null);	
			
			ImageView userAvatarView     = (ImageView) rowView.findViewById(R.id.status_user_avatar);
			TextView  userNameView       = (TextView ) rowView.findViewById(R.id.status_user_name);
			
			TextView  statusTweetView         = (TextView ) rowView.findViewById(R.id.status_text);
			TextView  statusTimestampView     = (TextView ) rowView.findViewById(R.id.status_timestamp);
			TextView  statusSourceView        = (TextView ) rowView.findViewById(R.id.status_source);
			TextView  statusRepostsCountView  = (TextView ) rowView.findViewById(R.id.status_reposts_count);
			TextView  statusCommentsCountView = (TextView ) rowView.findViewById(R.id.status_comments_count);
			ImageView statusThumbnailView     = (ImageView) rowView.findViewById(R.id.status_thumbnail);
			
			TextView  retweetedStatusTweetView     = (TextView ) rowView.findViewById(R.id.status_retweeted_status_text);
		    ImageView retweetedStatusThumbnailView = (ImageView) rowView.findViewById(R.id.status_retweeted_status_thumbnail);
		
			WeiboStatus currentStatus  = (WeiboStatus) getItem(position);
		
			// Weibo status header layout
			// It should be evaluated whether 'user' field exists in case of weibo status
			// has been removed by weibo server because of status contents or user.
			// TODO user avatar images should be cached in database.
			if (currentStatus.getUser() != null) {
				String userAvatarUrl = currentStatus.getUser().getProfileImageUrl();
				ImageCallbackImpl userAvatarCallbackImpl = new ImageCallbackImpl(userAvatarView);
				Drawable userAvatarCacheImage = loader.loadDrawable(userAvatarUrl, userAvatarCallbackImpl);
				if (userAvatarCacheImage != null) {
					// user avatar image has been exists in cache already
					userAvatarView.setImageDrawable(userAvatarCacheImage);
				}
				
				userNameView.setText(currentStatus.getUser().getName());
				userNameView.setTextColor(Color.BLUE);
			}
			
			// status layout
			statusTweetView.setText(currentStatus.getText());
			statusTweetView.setTextColor(Color.DKGRAY);
			
			// XXX hard hacks about timestamp
			String statusCreatedAt = currentStatus.getCreatedAt();
			statusTimestampView.setText("On " + statusCreatedAt.substring(0, 3) + " " + statusCreatedAt.substring(11, 19));
			statusTimestampView.setTextColor(Color.BLUE);
			
			// Weibo status extra information
			// fetch client using regexp pattern
			if (currentStatus.getSource() != null) {
				statusSourceView.setText("From " + FormatString.formatWeiboSource(currentStatus.getSource()));
			}
			
			statusRepostsCountView.setText("Repost" + "(" + currentStatus.getRepostsCount() + ")" + "  ");
			statusRepostsCountView.setTextColor(Color.BLUE);
			
			statusCommentsCountView.setText("Comment" + "(" + currentStatus.getCommentsCount() + ")");
			statusCommentsCountView.setTextColor(Color.BLUE);
			
			if (currentStatus.getThumbnailPic() != null) {
				statusThumbnailView.setVisibility(View.VISIBLE);
				
				String statusThumbnailPicUrl = currentStatus.getThumbnailPic();
				ImageCallbackImpl statusThumbnailCallbackImpl = new ImageCallbackImpl(statusThumbnailView);
				Drawable statusThumbnailCacheImage = loader.loadDrawable(statusThumbnailPicUrl, statusThumbnailCallbackImpl);
				if (statusThumbnailCacheImage != null) {
					statusThumbnailView.setImageDrawable(statusThumbnailCacheImage);
				}
			}
			
			// retweeted status layout
			if (currentStatus.getRetweetedStatus() != null) {
				retweetedStatusTweetView.setVisibility(View.VISIBLE);
				retweetedStatusTweetView.setTextColor(Color.LTGRAY);
				
				if (currentStatus.getRetweetedStatus().getUser() != null) {
					retweetedStatusTweetView.setText(currentStatus.getRetweetedStatus().getUser().getName()
							+ ": " + currentStatus.getRetweetedStatus().getText());
				} else {
					retweetedStatusTweetView.setText(currentStatus.getRetweetedStatus().getText());
				}
				
				if (currentStatus.getRetweetedStatus().getThumbnailPic() != null) {
					retweetedStatusThumbnailView.setVisibility(View.VISIBLE);
					
					String retweetedStatusThumbnailPicUrl = currentStatus.getRetweetedStatus().getThumbnailPic();
					ImageCallbackImpl retweetedStatusThumbnailCallbackImpl = new ImageCallbackImpl(retweetedStatusThumbnailView);
					Drawable retweetedStatusThumbnailCacheImage = loader.loadDrawable(retweetedStatusThumbnailPicUrl, retweetedStatusThumbnailCallbackImpl);
					if (retweetedStatusThumbnailCacheImage != null) {
						retweetedStatusThumbnailView.setImageDrawable(retweetedStatusThumbnailCacheImage);
					}
				}
			}
			
			rowViews.put(position, rowView);
		}
		return rowView;
	}
	
	public void clearCache() {
		rowViews.clear();
	}
	
	@Override
	public void notifyDataSetChanged() {
		clearCache();
		super.notifyDataSetChanged();
	}
	
}
