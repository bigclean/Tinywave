package com.github.bigclean.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bigclean Cheng
 */
public class WeiboUser {
	public static final int USER_TYPE_FOLLOWING    = 1;
	public static final int USER_TYPE_FOLLOWME     = 2;
	public static final int USER_TYPE_BI_FOLLOWING = 3;
	
	private long    uid;
	
	private String  screenName;
	private String  name;
	
	private int     province;
	private int     city;
	private String  location;
	
	private String  description;
	private String  url;
	private String  profileImageUrl;
	private String  avatarLarge;
	private String  domain;
	private String  gender;
	
	private int     followersCount;
	private int     friendsCount;
	private int     statusesCount;
	private int     favouritesCount;
	private int     biFollowersCount;
	private boolean following;
	private boolean followMe;
	
	private String  createdAt;
	private boolean verified;
	private String  verifiedReason;
	private boolean geoEnabled;
	private boolean allowAllActMsg;
	private boolean allowAllComment;
	
	private int     onlineStatus;
	
	// 'status' field is available in user information
	private WeiboStatus status = null;
	
	public WeiboUser() { }
	
	public WeiboUser(JSONObject jsonWeiboUser) throws Exception {
		init(jsonWeiboUser);
	}

	private void init(JSONObject jsonWeiboUser) throws Exception {
		try {
			uid        = jsonWeiboUser.getLong("id");
			screenName = jsonWeiboUser.getString("screen_name");
			name       = jsonWeiboUser.getString("name");
			
			province   = jsonWeiboUser.getInt("province");
			city       = jsonWeiboUser.getInt("city");
			location   = jsonWeiboUser.getString("location");
			
			description      = jsonWeiboUser.getString("description");
			url              = jsonWeiboUser.getString("url");
			profileImageUrl  = jsonWeiboUser.getString("profile_image_url");
			avatarLarge      = jsonWeiboUser.getString("avatar_large");
			domain           = jsonWeiboUser.getString("domain");
			gender           = jsonWeiboUser.getString("gender");
			
			followersCount   = jsonWeiboUser.getInt("followers_count");
			friendsCount     = jsonWeiboUser.getInt("friends_count");
			statusesCount    = jsonWeiboUser.getInt("statuses_count");
			favouritesCount  = jsonWeiboUser.getInt("favourites_count");
			biFollowersCount = jsonWeiboUser.getInt("bi_followers_count");
			following        = jsonWeiboUser.getBoolean("following");
			followMe         = jsonWeiboUser.getBoolean("follow_me");
			
			createdAt        = jsonWeiboUser.getString("created_at");
			verified         = jsonWeiboUser.getBoolean("verified");
			verifiedReason   = jsonWeiboUser.getString("verified_reason");
			geoEnabled       = jsonWeiboUser.getBoolean("geo_enabled");
			allowAllActMsg   = jsonWeiboUser.getBoolean("allow_all_act_msg");
			allowAllComment  = jsonWeiboUser.getBoolean("allow_all_comment");
			
			onlineStatus     = jsonWeiboUser.getInt("online_status");
			
			if (!jsonWeiboUser.isNull("status")) {
				status       = new WeiboStatus(jsonWeiboUser.getJSONObject("status"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static WeiboUserWrapper initWeiboUserWrapper(JSONObject jsonResponse) throws Exception {
		JSONArray  jsonUsers     = null;
		WeiboUserWrapper wrapper = null;
		
		try {
			if (!jsonResponse.isNull("users")) {
				jsonUsers = jsonResponse.getJSONArray("users");
			}
			
			int size = jsonUsers.length();
			ArrayList<WeiboUser> users = new ArrayList<WeiboUser>(size);
			for(int i = 0; i < size; i++) {
				users.add(new WeiboUser(jsonUsers.getJSONObject(i)));
			}
			
			long previousCursor = jsonResponse.getLong("previous_cursor");
			long nextCursor     = jsonResponse.getLong("next_cursor");
			int  totalNumber    = jsonResponse.getInt("total_number");
			
			wrapper = new WeiboUserWrapper(users, previousCursor, nextCursor, totalNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return wrapper;
	}
	
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}

	public int getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public int getBiFollowersCount() {
		return biFollowersCount;
	}

	public void setBiFollowersCount(int biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isFollowMe() {
		return followMe;
	}

	public void setFollowMe(boolean followMe) {
		this.followMe = followMe;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getVerifiedReason() {
		return verifiedReason;
	}

	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}

	public boolean isGeoEnabled() {
		return geoEnabled;
	}

	public void setGeoEnabled(boolean geoEnabled) {
		this.geoEnabled = geoEnabled;
	}

	public boolean isAllowAllActMsg() {
		return allowAllActMsg;
	}

	public void setAllowAllActMsg(boolean allowAllActMsg) {
		this.allowAllActMsg = allowAllActMsg;
	}

	public boolean isAllowAllComment() {
		return allowAllComment;
	}

	public void setAllowAllComment(boolean allowAllComment) {
		this.allowAllComment = allowAllComment;
	}

	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public WeiboStatus getStatus() {
		return status;
	}

	public void setStatus(WeiboStatus status) {
		this.status = status;
	}
}