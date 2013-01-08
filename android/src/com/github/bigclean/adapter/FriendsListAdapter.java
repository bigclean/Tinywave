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
import com.github.bigclean.model.WeiboUser;
import com.github.bigclean.util.AsyncImageLoader;
import com.github.bigclean.util.ImageCallbackImpl;

public class FriendsListAdapter extends BaseAdapter {
	private List<WeiboUser>  friends = null;
	private AsyncImageLoader loader  = new AsyncImageLoader();
	private Context context = null;
	
	public FriendsListAdapter(ArrayList<WeiboUser> users, Context context) {
		this.friends = users;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FriendViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.friend_row, null);
			
			holder = new FriendViewHolder();
			
			holder.userAvatarView    = (ImageView) convertView.findViewById(R.id.friend_avatar);
			holder.userNameView      = (TextView)  convertView.findViewById(R.id.friend_name);
			holder.userBasicInfoView = (TextView)  convertView.findViewById(R.id.friend_basic_info);
			
			convertView.setTag(holder);
		} else {
			holder = (FriendViewHolder) convertView.getTag();
		}
		
		WeiboUser currentFriend = friends.get(position);
		
		String userAvatarUrl = currentFriend.getProfileImageUrl();
		ImageCallbackImpl userAvatarCallbackImpl = new ImageCallbackImpl(holder.userAvatarView);
		Drawable userAvatarCacheImage = loader.loadDrawable(userAvatarUrl, userAvatarCallbackImpl);
		if (userAvatarCacheImage != null) {
			holder.userAvatarView.setImageDrawable(userAvatarCacheImage);
		}
		
		holder.userNameView.setText(currentFriend.getName());
		holder.userNameView.setTextColor(Color.DKGRAY);
		if (currentFriend.getGender().equals("f")) {
			holder.userBasicInfoView.setText("Female" + " " + currentFriend.getLocation());
		} else {
			holder.userBasicInfoView.setText("Male" + " " + currentFriend.getLocation());
		}
		holder.userBasicInfoView.setTextColor(Color.LTGRAY);
		
		return convertView;
	}
	
	private static class FriendViewHolder {
		public ImageView userAvatarView;
		public TextView  userNameView;
		public TextView  userBasicInfoView;
	}

}