package com.github.bigclean.adapter;

import java.util.ArrayList;

import com.github.bigclean.fragment.TimelineFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TimelineFragmentsListAdapter extends FragmentPagerAdapter {
	private static final String[] TIMELINE_TITLES = new String[] { "Home", "Me", "Public", "Mentions" };
	private ArrayList<TimelineFragment> fragments = null;
	
	public TimelineFragmentsListAdapter(FragmentManager fm, ArrayList<TimelineFragment> fragments) {
		super(fm);
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
		return TimelineFragmentsListAdapter.TIMELINE_TITLES[position];
	}

}