package com.github.bigclean.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiboFavorite {
	private WeiboStatus status;
	private List<WeiboFavoriteTag> tags;
	private String favoritedTime;
	
	public WeiboFavorite() { }
	
	public WeiboFavorite(JSONObject jsonWeiboFavorite) throws Exception {
		init(jsonWeiboFavorite);
	}
	
	private void init(JSONObject jsonWeiboFavorite) throws Exception {
		try {
			if (!jsonWeiboFavorite.isNull("status")) {
				status = new WeiboStatus(jsonWeiboFavorite.getJSONObject("status"));
				status.setType(WeiboStatus.STATUS_TYPE_FAVORITE);
			}
			
			if (!jsonWeiboFavorite.isNull("tags")) {
				JSONArray list = jsonWeiboFavorite.getJSONArray("tags");
				int size = list.length();
				List<WeiboFavoriteTag> tags = new ArrayList<WeiboFavoriteTag>(size);
				for (int i = 0; i < size; i++) {
					tags.add(new WeiboFavoriteTag(list.getJSONObject(i)));
				}
			}
			
			favoritedTime = jsonWeiboFavorite.getString("favorited_time");
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
	
	public static ArrayList<WeiboFavorite> constructWeiboFavorites(JSONObject jsonResponse)
			throws Exception {
		JSONArray jsonFavorites = null;
		ArrayList<WeiboFavorite> favorites = null;
		
		try {
			if (!jsonResponse.isNull("favorites")) {
				jsonFavorites = jsonResponse.getJSONArray("favorites");
			}
			
			int size = jsonFavorites.length();
			favorites = new ArrayList<WeiboFavorite>(size);
			for(int i = 0; i < size; i++) {
				favorites.add(new WeiboFavorite(jsonFavorites.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return favorites;
	}
	
	public static ArrayList<WeiboStatus> getFavoritedStatuses(ArrayList<WeiboFavorite> favorites) {
		int size = favorites.size();
		ArrayList<WeiboStatus> favoritedStatuses = new ArrayList<WeiboStatus>(size);
		for (int i = 0; i < size; i++) {
			WeiboStatus status = favorites.get(i).getStatus();
			//status.setType(WeiboStatus.STATUS_TYPE_FAVORITE);
			favoritedStatuses.add(status);
		}
		
		return favoritedStatuses;
	}
	
	public WeiboStatus getStatus() {
		return status;
	}

	public void setStatus(WeiboStatus status) {
		this.status = status;
	}

	public List<WeiboFavoriteTag> getTags() {
		return tags;
	}

	public void setTags(List<WeiboFavoriteTag> tags) {
		this.tags = tags;
	}

	public String getFavoritedTime() {
		return favoritedTime;
	}

	public void setFavoritedTime(String favoritedTime) {
		this.favoritedTime = favoritedTime;
	}

}