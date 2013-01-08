package com.github.bigclean.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiboIDs {
	private int[] ids;
	
	private long previousCursor;
	private long nextCursor;
	
	WeiboIDs(JSONObject jsonWeiboIDs) throws Exception {
		init(jsonWeiboIDs);
	}
	
	private void init(JSONObject jsonWeiboIDs) throws Exception {
		try {
			if (!jsonWeiboIDs.isNull("ids")) {
				JSONArray list = jsonWeiboIDs.getJSONArray("ids");
				int size = list.length();
				ids = new int[size];
				for (int i = 0; i < size; i++) {
					ids[i] = list.getInt(i);
				}
			}
			
			previousCursor = jsonWeiboIDs.getLong("previous_cursor");
			nextCursor     = jsonWeiboIDs.getLong("next_cursor");
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public long getPreviousCursor() {
		return previousCursor;
	}

	public void setPreviousCursor(long previousCursor) {
		this.previousCursor = previousCursor;
	}

	public long getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(long nextCursor) {
		this.nextCursor = nextCursor;
	}

}
