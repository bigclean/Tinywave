package com.github.bigclean.loader;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import weibo4j.Weibo;
import weibo4j.constants.WeiboConstants;
import weibo4j.model.WeiboException;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.data.WeiboManager;
import com.github.bigclean.model.WeiboFavorite;

public class WeiboFavoriteLoader extends AsyncTaskLoader<ArrayList<WeiboFavorite>>{
	private String       updatedFavoritesLieral;
	private ArrayList<WeiboFavorite> updatedFavorites;
	private WeiboManager manager = TinywaveApp.getInstance().getWeiboManager();

	public WeiboFavoriteLoader(final Context context) {
		super(context);
	}
	
	@Override
	protected void onStartLoading() {
		if (updatedFavorites != null) {
			// when loading data exists already
			deliverResult(updatedFavorites);
		} else {
			// Loader should be force loaded in support library v4
			forceLoad();
		}
	}

	@Override
	public ArrayList<WeiboFavorite> loadInBackground() {
		SharedPreferences weiboPrefs = PreferenceManager.getDefaultSharedPreferences(TinywaveApp.getInstance().getApplicationContext());
		String accessToken = weiboPrefs.getString("access_token", "");
		
		Weibo weibo = new Weibo();
		weibo.setToken(accessToken);
		
		try {
			updatedFavoritesLieral = Weibo.client.get(WeiboConstants.BASE_URL + "favorites.json").asString();
			updatedFavorites = WeiboFavorite.constructWeiboFavorites(new JSONObject(updatedFavoritesLieral));
			
			// update fetched status timeline to database
			manager.refreshWeiboFavorites(updatedFavorites);
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
		
		return  manager.getWeiboFavorites();
	}

	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		
		if (updatedFavorites != null) {
			updatedFavorites = null;
		}
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		
		cancelLoad();
	}

}