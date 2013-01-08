package com.github.bigclean.activity;

import java.util.ArrayList;

import com.github.bigclean.R;
import com.github.bigclean.adapter.CommentFragmentsListAdapter;
import com.github.bigclean.fragment.CommentFragment;
import com.github.bigclean.model.WeiboComment;
import com.markupartist.android.widget.ActionBar;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ExploreCommentActivity extends FragmentActivity {
	private ArrayList<CommentFragment> fragments = new ArrayList<CommentFragment>();
	
	private CommentFragmentsListAdapter adapter;
	private ViewPager pager;
	private TitlePageIndicator indicator;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explore_comment);
		
		final ActionBar commentActionBar = (ActionBar) findViewById(R.id.comment_action_bar);
		commentActionBar.setTitle("Comments");
		
		adapter = new CommentFragmentsListAdapter(getSupportFragmentManager(), fragments);
		
		pager = (ViewPager) findViewById(R.id.comments_pager);
		pager.setAdapter(adapter);
		
		indicator = (TitlePageIndicator) findViewById(R.id.comments_indicator);
		indicator.setViewPager(pager);
		
		CommentFragment toMeCommentFragment = CommentFragment.newInstance(WeiboComment.COMMENT_TYPE_TO_ME);
		fragments.add(toMeCommentFragment);
		
		CommentFragment byMeCommentFragment = CommentFragment.newInstance(WeiboComment.COMMENT_TYPE_BY_ME);
		fragments.add(byMeCommentFragment);
		
		adapter.notifyDataSetChanged();
	}

}