package com.github.bigclean.fragment;

import java.util.ArrayList;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.activity.StatusDetailsActivity;
import com.github.bigclean.adapter.TimelineAdapter;
import com.github.bigclean.loader.WeiboStatusLoader;
import com.github.bigclean.model.WeiboStatus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineFragment extends ListFragment
            implements LoaderCallbacks<ArrayList<WeiboStatus>>{
	private int type;
	private static final int DEFAULT_TYPE = WeiboStatus.STATUS_TYPE_HOME;
	
	private int refreshCount = 0;
	private ArrayList<WeiboStatus> statuses  = null;
	private static final int       LOADER_ID = 0;
	
	private TimelineAdapter        adapter;
	private PullToRefreshListView  listView;
	
	public static TimelineFragment newInstance(Integer type) {
		TimelineFragment fragment = new TimelineFragment();
		
		Bundle args = new Bundle();
		args.putInt("type", type);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		type     = getArguments().getInt("type", DEFAULT_TYPE);
		statuses = TinywaveApp.getInstance().getWeiboManager().getWeiboStatuses(type);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = new PullToRefreshListView(getActivity());
		adapter  = new TimelineAdapter(statuses, getActivity());
		listView.setAdapter(adapter);
		
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshCount > 0) {
					load(true);
				} else {
				load(false);
				}
				refreshCount++;
			}
		});
		
		return listView;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//Intent viewFriendIntent = new Intent();
		Intent viewStatusIntent = new Intent();
		//viewFriendIntent.setClass(getActivity().getApplicationContext(), UserAboutActivity.class);
		viewStatusIntent.setClass(getActivity().getApplicationContext(), StatusDetailsActivity.class);
		
		// position should be decreased by 1 to be index
		//System.out.println("id is " + id);
		//System.out.println("position is " + position);
		// FIXME Why 'id' just works, why not position?
		//WeiboStatus status = (WeiboStatus) adapter.getItem(position - 1);
		WeiboStatus status = (WeiboStatus) adapter.getItem((int) id);
		
		//viewFriendIntent.putExtra("status_id", status.getId());
		viewStatusIntent.putExtra("status_id", status.getId());
		viewStatusIntent.putExtra("status_type", status.getType());
		//viewFriendIntent.putExtra("user_id", status.getUser().getUid());
		//viewFriendIntent.putExtra("status_thumbnail", status.getThumbnailPic());
		//viewFriendIntent.putExtra("status_text", status.getText());
		
		//startActivity(viewFriendIntent);
		startActivity(viewStatusIntent);
	}

	public void load(boolean forceLoad) {
		if (forceLoad) {
			Loader<ArrayList<WeiboStatus>> loader = getLoaderManager().getLoader(LOADER_ID);
			if (loader != null) {
				loader.forceLoad();
			}
		} else {
			getLoaderManager().initLoader(LOADER_ID, null, this);
		}
	}
	
	public void loadCancel() {
		Loader<ArrayList<WeiboStatus>> loader = getLoaderManager().getLoader(LOADER_ID);
		
		if (loader != null) {
			((AsyncTaskLoader<ArrayList<WeiboStatus>>) loader).cancelLoad();
		}
	}
	
	// LoaderCallbacks
	@Override
	public Loader<ArrayList<WeiboStatus>> onCreateLoader(int id, Bundle args) {
		return new WeiboStatusLoader(getActivity(), type);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<WeiboStatus>> loader, ArrayList<WeiboStatus> data) {
		// TODO evaluate 'statuses' is equal to new fetched data
		statuses.clear();
		for (WeiboStatus updatedStatus : data) {
			statuses.add(updatedStatus);
			adapter.notifyDataSetChanged();
		}
		
		Toast.makeText(getActivity(), "Successfully update timeline.", Toast.LENGTH_SHORT).show();
		listView.onRefreshComplete();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<WeiboStatus>> loader) {
	}

}