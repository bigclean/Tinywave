package com.github.bigclean.fragment;

import java.util.ArrayList;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.adapter.TimelineAdapter;
import com.github.bigclean.loader.WeiboFavoriteLoader;
import com.github.bigclean.model.WeiboFavorite;
import com.github.bigclean.model.WeiboStatus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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

public class FavoriteFragment extends ListFragment
            implements LoaderCallbacks<ArrayList<WeiboFavorite>>{
	private int refreshCount = 0;
	private ArrayList<WeiboFavorite> favorites  = null;
	private ArrayList<WeiboStatus>   favoritedsStatuses = null;
	private static final int       LOADER_ID = 0;
	
	private TimelineAdapter        adapter;
	private PullToRefreshListView  listView;
	
	public static FavoriteFragment newInstance() {
		return new FavoriteFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		favorites = TinywaveApp.getInstance().getWeiboManager().getWeiboFavorites();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = new PullToRefreshListView(getActivity());
		favoritedsStatuses = WeiboFavorite.getFavoritedStatuses(favorites);
		
		adapter  = new TimelineAdapter(favoritedsStatuses, getActivity());
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
		Loader<ArrayList<WeiboFavorite>> loader = getLoaderManager().getLoader(LOADER_ID);
		
		if (loader != null) {
			((AsyncTaskLoader<ArrayList<WeiboFavorite>>) loader).cancelLoad();
		}
	}
	
	// LoaderCallbacks
	@Override
	public Loader<ArrayList<WeiboFavorite>> onCreateLoader(int id, Bundle args) {
		return new WeiboFavoriteLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<WeiboFavorite>> loader, ArrayList<WeiboFavorite> data) {
		favoritedsStatuses.clear();
		for (WeiboFavorite updatedFavorite : data) {
			favoritedsStatuses.add(updatedFavorite.getStatus());
			adapter.notifyDataSetChanged();
		}
		
		Toast.makeText(getActivity(), "Successfully update favorites.", Toast.LENGTH_SHORT).show();
		listView.onRefreshComplete();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<WeiboFavorite>> loader) {
	}

}