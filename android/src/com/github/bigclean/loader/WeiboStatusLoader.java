package com.github.bigclean.loader;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import weibo4j.Weibo;
import weibo4j.constants.WeiboConstants;
import weibo4j.model.WeiboException;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.data.WeiboManager;
import com.github.bigclean.model.WeiboStatus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.SparseArray;

public class WeiboStatusLoader extends AsyncTaskLoader<ArrayList<WeiboStatus>> {
	private int          updatedType;
	private String       updatedTimeline;
	private WeiboManager manager = TinywaveApp.getInstance().getWeiboManager();
	
	private ArrayList<WeiboStatus> updatedStatuses;
	private SparseArray<String>    timelineArray = new SparseArray<String>();

	public WeiboStatusLoader(final Context context, int type) {
		super(context);
		
		timelineArray.put(WeiboStatus.STATUS_TYPE_PUBLIC,   "public_timeline");
		timelineArray.put(WeiboStatus.STATUS_TYPE_HOME,     "home_timeline");
		timelineArray.put(WeiboStatus.STATUS_TYPE_USER,     "user_timeline");
		timelineArray.put(WeiboStatus.STATUS_TYPE_MENTIONS, "mentions");
		
		this.updatedType = type;
	}
	
	@Override
	protected void onStartLoading() {
		if (updatedStatuses != null) {
			// when loading data exists already
			deliverResult(updatedStatuses);
		} else {
			// Loader should be force loaded in support library v4
			forceLoad();
		}
	}

	@Override
	public ArrayList<WeiboStatus> loadInBackground() {
		SharedPreferences weiboPrefs = PreferenceManager.getDefaultSharedPreferences(TinywaveApp.getInstance().getApplicationContext());
		String accessToken = weiboPrefs.getString("access_token", "");
		
		Weibo weibo = new Weibo();
		weibo.setToken(accessToken);
		
		try {
			updatedTimeline = Weibo.client.get(WeiboConstants.BASE_URL + "statuses/" + timelineArray.get(updatedType)+ ".json").asString();
			updatedStatuses = WeiboStatus.initWeiboStatusWrapper(new JSONObject(updatedTimeline)).getStatuses();
			
			// update fetched status timeline to database
			manager.refreshWeiboTimeline(updatedStatuses, updatedType);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  manager.getWeiboStatuses(updatedType);
	}

	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		
		if (updatedStatuses != null) {
			updatedStatuses = null;
		}
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		
		cancelLoad();
	}
	
}