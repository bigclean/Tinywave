package com.github.bigclean.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.github.bigclean.R;
import com.github.bigclean.fragment.FriendFragment;
import com.github.bigclean.model.WeiboUser;

public class ExploreFriendActivity extends FragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_container);
		
		FriendFragment friendFragment = FriendFragment.newInstance(WeiboUser.USER_TYPE_FOLLOWING);
		getSupportFragmentManager().beginTransaction().add(R.id.friend_fragment_container, friendFragment).commit();
	}

}