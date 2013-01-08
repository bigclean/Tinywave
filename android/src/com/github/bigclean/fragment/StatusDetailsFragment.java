package com.github.bigclean.fragment;

import java.util.ArrayList;

import com.github.bigclean.R;
import com.github.bigclean.TinywaveApp;
import com.github.bigclean.adapter.StatusCommentsListAdapter;
import com.github.bigclean.model.WeiboComment;
import com.github.bigclean.model.WeiboStatus;
import com.github.bigclean.util.AsyncImageLoader;
import com.github.bigclean.util.FormatString;
import com.github.bigclean.util.ImageCallbackImpl;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class StatusDetailsFragment extends Fragment {
	private long id;
	private int  type;
	
	private WeiboStatus status;
	private ArrayList<WeiboComment> statusComments  = null;
	
	private AsyncImageLoader loader = new AsyncImageLoader();
	
	private ImageView userAvatarView;
	private TextView  userNameView;
	private TextView  userEssentialInfoView;
	
	private TextView  statusTweetView;
	private TextView  statusTimestampView;
	private TextView  statusSourceView;
	private TextView  statusRepostsCountView;
	private TextView  statusCommentsCountView;
	private ImageView statusThumbnailView;
	
	private TextView  retweetedStatusTweetView;
    private TextView  retweetedStatusTimestampView;
    private TextView  retweetedStatusSourceView;
    private ImageView retweetedStatusThumbnailView;

    private ListView  listView;
	private StatusCommentsListAdapter adapter;

	public static StatusDetailsFragment newInstance(Long id, Integer type) {
		StatusDetailsFragment fragment = new StatusDetailsFragment();
		
		Bundle args = new Bundle();
		args.putLong("status_id", id);
		args.putInt("status_type", type);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id       = getArguments().getLong("status_id");
		type     = getArguments().getInt("status_type");
		status   = TinywaveApp.getInstance().getWeiboManager().getWeiboStatusById(id, type);
		statusComments = TinywaveApp.getInstance().getWeiboManager().getWeiboComments(WeiboComment.COMMENT_TYPE_TO_ME);
		
		adapter = new StatusCommentsListAdapter(statusComments, getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.status_details, container, false);
		
		userAvatarView        = (ImageView) v.findViewById(R.id.status_details_user_avatar);
		userNameView          = (TextView ) v.findViewById(R.id.status_details_user_name);
		userEssentialInfoView = (TextView ) v.findViewById(R.id.status_details_user_essential_info);
		
		statusTweetView         = (TextView ) v.findViewById(R.id.status_details_text);
		statusTimestampView     = (TextView ) v.findViewById(R.id.status_details_timestamp);
		statusSourceView        = (TextView ) v.findViewById(R.id.status_details_source);
		statusRepostsCountView  = (TextView ) v.findViewById(R.id.status_details_reposts_count);
		statusCommentsCountView = (TextView ) v.findViewById(R.id.status_details_comments_count);
		statusThumbnailView     = (ImageView) v.findViewById(R.id.status_details_thumbnail);
		
		retweetedStatusTweetView     = (TextView ) v.findViewById(R.id.status_details_retweeted_status_text);
	    retweetedStatusTimestampView = (TextView ) v.findViewById(R.id.status_details_retweeted_status_timestamp);
	    retweetedStatusSourceView    = (TextView ) v.findViewById(R.id.status_details_retweeted_status_source);
	    retweetedStatusThumbnailView = (ImageView) v.findViewById(R.id.status_details_retweeted_status_thumbnail);
	
	    listView = (ListView) v.findViewById(R.id.status_details_comments_list);
	
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setupUserLayout();
		setupStatusLayout();
		setupRetweetedStatusLayout();

		// list view
		listView.setAdapter(adapter);
	}
	
	private void setupUserLayout() {
		if (status.getUser() != null) {
			String userAvatarUrl = status.getUser().getProfileImageUrl();
			ImageCallbackImpl userAvatarCallbackImpl = new ImageCallbackImpl(userAvatarView);
			Drawable userAvatarCacheImage = loader.loadDrawable(userAvatarUrl, userAvatarCallbackImpl);
			if (userAvatarCacheImage != null) {
				// user avatar image has been exists in cache already
				userAvatarView.setImageDrawable(userAvatarCacheImage);
			}
			
			userNameView.setText(status.getUser().getName());
			userNameView.setTextColor(Color.BLUE);
			
			if (status.getUser().getGender().equals("f")) {
				userEssentialInfoView.setText("Female" + " " + status.getUser().getLocation());
			} else {
				userEssentialInfoView.setText("Male" + " " + status.getUser().getLocation());
			}
		}
	}
	
	private void setupStatusLayout() {
		statusTweetView.setText(status.getText());
		statusTweetView.setTextColor(Color.DKGRAY);
		
		// XXX hard hacks about timestamp
		String statusCreatedAt = status.getCreatedAt();
		statusTimestampView.setText("On " + statusCreatedAt.substring(0, 3) + " " + statusCreatedAt.substring(11, 19));
		
		// Weibo status extra information
		if (status.getSource() != null) {
			statusSourceView.setText("From " + FormatString.formatWeiboSource(status.getSource()));
		}
		
		statusRepostsCountView.setText("Repost" + "(" + status.getRepostsCount() + ")" + "  ");
		statusRepostsCountView.setTextColor(Color.BLUE);
		
		statusCommentsCountView.setText("Comment" + "(" + status.getCommentsCount() + ")");
		statusCommentsCountView.setTextColor(Color.BLUE);
		
		if (status.getThumbnailPic() != null) {
			statusThumbnailView.setVisibility(View.VISIBLE);
			
			String statusThumbnailPicUrl = status.getThumbnailPic();
			ImageCallbackImpl statusThumbnailCallbackImpl = new ImageCallbackImpl(statusThumbnailView);
			Drawable statusThumbnailCacheImage = loader.loadDrawable(statusThumbnailPicUrl, statusThumbnailCallbackImpl);
			if (statusThumbnailCacheImage != null) {
				statusThumbnailView.setImageDrawable(statusThumbnailCacheImage);
			}
		}
	}
	
	private void setupRetweetedStatusLayout() {
		if (status.getRetweetedStatus() != null) {
			retweetedStatusTweetView.setVisibility(View.VISIBLE);
			retweetedStatusTweetView.setTextColor(Color.LTGRAY);
			
			if (status.getRetweetedStatus().getUser() != null) {
				retweetedStatusTweetView.setText(status.getRetweetedStatus().getUser().getName()
						+ ": " + status.getRetweetedStatus().getText());
			} else {
				retweetedStatusTweetView.setText(status.getRetweetedStatus().getText());
			}
			
			retweetedStatusTimestampView.setVisibility(View.VISIBLE);
			String retweetedStatusCreatedAt = status.getRetweetedStatus().getCreatedAt();
			retweetedStatusTimestampView.setText("On " + retweetedStatusCreatedAt.substring(0, 3) + " " + retweetedStatusCreatedAt.substring(11, 19));
			
			if (status.getRetweetedStatus().getSource() != null) {
				retweetedStatusSourceView.setVisibility(View.VISIBLE);
				retweetedStatusSourceView.setText("From " + FormatString.formatWeiboSource(status.getRetweetedStatus().getSource()));
			}
			
			if (status.getRetweetedStatus().getThumbnailPic() != null) {
				retweetedStatusThumbnailView.setVisibility(View.VISIBLE);
				
				String retweetedStatusThumbnailPicUrl = status.getRetweetedStatus().getThumbnailPic();
				ImageCallbackImpl retweetedStatusThumbnailCallbackImpl = new ImageCallbackImpl(retweetedStatusThumbnailView);
				Drawable retweetedStatusThumbnailCacheImage = loader.loadDrawable(retweetedStatusThumbnailPicUrl, retweetedStatusThumbnailCallbackImpl);
				if (retweetedStatusThumbnailCacheImage != null) {
					retweetedStatusThumbnailView.setImageDrawable(retweetedStatusThumbnailCacheImage);
				}
			}
		}
	}
	
}
