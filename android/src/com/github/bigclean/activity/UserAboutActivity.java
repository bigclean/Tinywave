package com.github.bigclean.activity;

import com.github.bigclean.R;
import com.github.bigclean.fragment.UserAboutFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class UserAboutActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_about_container);
		
		long uid = getIntent().getExtras().getLong("user_id");
		UserAboutFragment userAboutFragment = UserAboutFragment.newInstance(uid);
		getSupportFragmentManager().beginTransaction().add(R.id.user_about_fragment_container, userAboutFragment).commit();
	}
	
}