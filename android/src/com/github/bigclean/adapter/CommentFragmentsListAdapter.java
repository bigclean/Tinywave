package com.github.bigclean.adapter;

import java.util.ArrayList;

import com.github.bigclean.fragment.CommentFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CommentFragmentsListAdapter extends FragmentPagerAdapter {
	private static final String[] COMMENT_TITLES = new String[] { "ToMe", "ByMe", "Mentions"};
	private ArrayList<CommentFragment> fragments = null;
	
	public CommentFragmentsListAdapter(FragmentManager manager, ArrayList<CommentFragment> fragments) {
		super(manager);
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CommentFragmentsListAdapter.COMMENT_TITLES[position];
	}
}