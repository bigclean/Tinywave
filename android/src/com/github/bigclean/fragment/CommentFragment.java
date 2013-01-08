package com.github.bigclean.fragment;

import java.util.ArrayList;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.adapter.CommentsListAdapter;
import com.github.bigclean.loader.WeiboCommentLoader;
import com.github.bigclean.model.WeiboComment;
import com.github.bigclean.model.WeiboStatus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

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

public class CommentFragment extends ListFragment
            implements LoaderCallbacks<ArrayList<WeiboComment>>{
	public int type;
	public static final int DEFAULT_TYPE = WeiboComment.COMMENT_TYPE_TO_ME;
	
	private int refreshCount = 0;
	private ArrayList<WeiboComment> comments = null;
	private static final int        LOADER_ID = 0;
	
	private CommentsListAdapter    adapter;
	private PullToRefreshListView  listView;
	
	public static CommentFragment newInstance(Integer type) {
		CommentFragment fragment = new CommentFragment();
		
		Bundle args = new Bundle();
		args.putInt("type", type);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		type = getArguments().getInt("type", DEFAULT_TYPE);
		comments = TinywaveApp.getInstance().getWeiboManager().getWeiboComments(type);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return inflater.inflate(R.layout.comments_list, container, false);

		listView = new PullToRefreshListView(getActivity());
		adapter  = new CommentsListAdapter(comments, getActivity());
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
		Loader<ArrayList<WeiboComment>> loader = getLoaderManager().getLoader(LOADER_ID);
		
		if (loader != null) {
			((AsyncTaskLoader<ArrayList<WeiboComment>>) loader).cancelLoad();
		}
	}
	
	// LoaderCallbacks
	@Override
	public Loader<ArrayList<WeiboComment>> onCreateLoader(int id, Bundle args) {
		return new WeiboCommentLoader(getActivity(), type);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<WeiboComment>> loader, ArrayList<WeiboComment> data) {
		comments.clear();
		for (WeiboComment updatedComment : data) {
			comments.add(updatedComment);
			adapter.notifyDataSetChanged();
		}
		
		Toast.makeText(getActivity(), "Successfully update comments.", Toast.LENGTH_SHORT).show();
		listView.onRefreshComplete();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<WeiboComment>> loader) {
	}

}