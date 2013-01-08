package com.github.bigclean.activity;

import java.util.ArrayList;

import com.github.bigclean.R;
import com.github.bigclean.adapter.TimelineFragmentsListAdapter;
import com.github.bigclean.fragment.TimelineFragment;
import com.github.bigclean.model.WeiboStatus;
import com.markupartist.android.widget.ActionBar;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ExploreTimelineActivity extends FragmentActivity {
	private ArrayList<TimelineFragment> fragments = new ArrayList<TimelineFragment>();
	
	private TimelineFragmentsListAdapter adapter;
	private ViewPager pager;
	private TitlePageIndicator indicator;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explore_timeline);
		
		final ActionBar timelineActionBar = (ActionBar) findViewById(R.id.timeline_action_bar);
		timelineActionBar.setTitle("Timeline");
		
		adapter = new TimelineFragmentsListAdapter(getSupportFragmentManager(), fragments);
		
		pager = (ViewPager) findViewById(R.id.timeline_pager);
		pager.setAdapter(adapter);
		
		indicator = (TitlePageIndicator) findViewById(R.id.timeline_indicator);
		indicator.setViewPager(pager);
		
		TimelineFragment homeTimelineFragment = TimelineFragment.newInstance(WeiboStatus.STATUS_TYPE_HOME);
		fragments.add(homeTimelineFragment);
		
		TimelineFragment userTimelineFragment = TimelineFragment.newInstance(WeiboStatus.STATUS_TYPE_USER);
		fragments.add(userTimelineFragment);
		
		TimelineFragment publicTimelineFragment = TimelineFragment.newInstance(WeiboStatus.STATUS_TYPE_PUBLIC);
		fragments.add(publicTimelineFragment);
		
		TimelineFragment mentionsTimelineFragment = TimelineFragment.newInstance(WeiboStatus.STATUS_TYPE_MENTIONS);
		fragments.add(mentionsTimelineFragment);
		
		adapter.notifyDataSetChanged();
	}

}