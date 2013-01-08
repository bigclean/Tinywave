package com.github.bigclean.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bigclean.R;
import com.github.bigclean.TinywaveApp;
import com.github.bigclean.model.WeiboUser;
import com.github.bigclean.util.AsyncImageLoader;
import com.github.bigclean.util.ImageCallbackImpl;

public class UserAboutFragment extends Fragment {
	private long      uid;
	private WeiboUser user;
	
	private AsyncImageLoader loader = new AsyncImageLoader();
	
	private ImageView userAvatarView;
	private TextView  userNameView;
	private TextView  userBasicInfoView;
	
	private TextView  userDescriptionView;
	
	private TextView  userFriendsCountView;
	private TextView  userFollowersCountView;
	private TextView  userStatusesCountView;
	
	private TextView  lastStatusTweetView;
	private ImageView lastStatusThumbnailView;
	
	public static UserAboutFragment newInstance(Long uid) {
		UserAboutFragment fragment = new UserAboutFragment();
		
		Bundle args = new Bundle();
		args.putLong("user_id", uid);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		uid = getArguments().getLong("user_id");
		
		user = TinywaveApp.getInstance().getWeiboManager().getWeiboUser(uid);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.user_about, container, false);
		
		userAvatarView     = (ImageView) v.findViewById(R.id.user_avatar);
		userNameView       = (TextView ) v.findViewById(R.id.user_name);
		userBasicInfoView  = (TextView ) v.findViewById(R.id.user_basic_info);
		
		userDescriptionView     = (TextView ) v.findViewById(R.id.user_description);
		
		userFriendsCountView    = (TextView ) v.findViewById(R.id.user_friends_count);
		userFollowersCountView  = (TextView ) v.findViewById(R.id.user_followers_count);
		userStatusesCountView   = (TextView ) v.findViewById(R.id.user_statuses_count);
		
		lastStatusTweetView     = (TextView ) v.findViewById(R.id.user_last_status_text);
		lastStatusThumbnailView = (ImageView) v.findViewById(R.id.user_last_status_picture);
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setupUserLayout();
		setupStatusLayout();
	}
	
	private void setupUserLayout() {
		String userAvatarUrl = user.getProfileImageUrl();
		ImageCallbackImpl userAvatarCallbackImpl = new ImageCallbackImpl(userAvatarView);
		Drawable userAvatarCacheImage = loader.loadDrawable(userAvatarUrl, userAvatarCallbackImpl);
		if (userAvatarCacheImage != null) {
			userAvatarView.setImageDrawable(userAvatarCacheImage);
		}
		
		userNameView.setText(user.getName());
		userNameView.setTextColor(Color.BLUE);
		if (user.getGender().equals("f")) {
			userBasicInfoView.setText("Female" + " " + user.getLocation());
		} else {
			userBasicInfoView.setText("Male" + " " + user.getLocation());
		}
		
		userDescriptionView.setText(user.getDescription());
		
		userFriendsCountView.setText(Integer.valueOf(user.getFriendsCount()).toString());
		userFollowersCountView.setText(Integer.valueOf(user.getFollowersCount()).toString());
		//userStatusesCountView.setText(user.getStatusesCount());
		userStatusesCountView.setText(Integer.valueOf(user.getStatusesCount()).toString());
	}
	
	private void setupStatusLayout() {
		if (user.getStatus() != null) {
			lastStatusTweetView.setVisibility(View.VISIBLE);
			lastStatusTweetView.setText(user.getStatus().getText());
			
			if (user.getStatus().getThumbnailPic() != null) {
				lastStatusThumbnailView.setVisibility(View.VISIBLE);
				
				String thumbnailPicUrl = user.getStatus().getThumbnailPic();
				ImageCallbackImpl thumbnailCallbackImpl = new ImageCallbackImpl(lastStatusThumbnailView);
				Drawable thumbnailCacheImage = loader.loadDrawable(thumbnailPicUrl, thumbnailCallbackImpl);
				if (thumbnailCacheImage != null) {
					lastStatusThumbnailView.setImageDrawable(thumbnailCacheImage);
				}
			}
		}
	}
	
}