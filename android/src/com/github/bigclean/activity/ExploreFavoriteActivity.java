package com.github.bigclean.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.github.bigclean.R;
import com.github.bigclean.fragment.FavoriteFragment;

public class ExploreFavoriteActivity extends FragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		FavoriteFragment favoriteFragment = FavoriteFragment.newInstance();
		getSupportFragmentManager().beginTransaction().add(R.id.timeline_container, favoriteFragment).commit();
	}

}