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
import android.util.SparseArray;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.data.WeiboManager;
import com.github.bigclean.model.WeiboComment;

public class WeiboCommentLoader extends AsyncTaskLoader<ArrayList<WeiboComment>> {
	private int          updatedType;
	private String       updatedCommentsLiteral;
	private WeiboManager manager = TinywaveApp.getInstance().getWeiboManager();
	
	private ArrayList<WeiboComment> updatedComments;
	private SparseArray<String>     commentArray = new SparseArray<String>();

	public WeiboCommentLoader(final Context context, int type) {
		super(context);
		
		commentArray.put(WeiboComment.COMMENT_TYPE_BY_ME,    "by_me");
		commentArray.put(WeiboComment.COMMENT_TYPE_TO_ME,    "to_me");
		commentArray.put(WeiboComment.COMMENT_TYPE_MENTIONS, "mentions");
		
		this.updatedType = type;
	}
	
	@Override
	protected void onStartLoading() {
		if (updatedComments != null) {
			// when loading data exists already
			deliverResult(updatedComments);
		} else {
			// Loader should be force loaded in support library v4
			forceLoad();
		}
	}

	@Override
	public ArrayList<WeiboComment> loadInBackground() {
		SharedPreferences weiboPrefs = PreferenceManager.getDefaultSharedPreferences(TinywaveApp.getInstance().getApplicationContext());
		String accessToken = weiboPrefs.getString("access_token", "");
		
		Weibo weibo = new Weibo();
		weibo.setToken(accessToken);
		
		try {
			updatedCommentsLiteral  = Weibo.client.get(WeiboConstants.BASE_URL + "comments/" + commentArray.get(updatedType)+ ".json").asString();
			updatedComments = WeiboComment.initWeiboCommentWrapper(new JSONObject(updatedCommentsLiteral)).getComments();
			
			// update fetched status timeline to database
			manager.refreshWeiboComments(updatedComments, updatedType);
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
		
		return  manager.getWeiboComments(updatedType);
	}

	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		
		if (updatedComments != null) {
			updatedComments = null;
		}
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		
		cancelLoad();
	}

}