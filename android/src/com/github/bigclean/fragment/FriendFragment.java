package com.github.bigclean.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.activity.UserAboutActivity;
import com.github.bigclean.adapter.FriendsListAdapter;
import com.github.bigclean.model.WeiboUser;

public class FriendFragment extends ListFragment {
	private int type;
	private static final int DEFAULT_TYPE = WeiboUser.USER_TYPE_FOLLOWING;
	
	private ArrayList<WeiboUser> friends  = null;
	
	private FriendsListAdapter adapter;
	
	public static FriendFragment newInstance(Integer type) {
		FriendFragment fragment = new FriendFragment();
		
		Bundle args = new Bundle();
		args.putInt("type", type);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		type     = getArguments().getInt("type", DEFAULT_TYPE);
		friends = TinywaveApp.getInstance().getWeiboManager().getWeiboUsers(type);
		
		adapter  = new FriendsListAdapter(friends, getActivity());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent viewFriendIntent = new Intent();
		viewFriendIntent.setClass(getActivity().getApplicationContext(), UserAboutActivity.class);
		
		// position should be decreased by 1 to be index
		//System.out.println("id is " + id);
		//System.out.println("position is " + position);
		// FIXME Why 'id' just works, why not position?
		//WeiboStatus status = (WeiboStatus) adapter.getItem(position - 1);
		WeiboUser friend = (WeiboUser) adapter.getItem((int) id);
		
		viewFriendIntent.putExtra("user_id", friend.getUid());
		
		startActivity(viewFriendIntent);
	}
}