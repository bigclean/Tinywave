package com.github.bigclean.model;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboFavoriteTag {
	private int    id;
	private String tag;
	private int    count;
	
	WeiboFavoriteTag(JSONObject jsonWeiboFavoriteTag) throws Exception {
		init(jsonWeiboFavoriteTag);
	}
	
	private void init(JSONObject jsonWeiboFavoriteTag) throws Exception {
		try {
			id    = jsonWeiboFavoriteTag.getInt("id");
			tag   = jsonWeiboFavoriteTag.getString("tag");
			count = jsonWeiboFavoriteTag.getInt("count");
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}