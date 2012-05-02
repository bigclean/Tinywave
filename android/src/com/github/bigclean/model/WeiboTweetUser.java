package com.github.bigclean.model;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboTweetUser {
	private int     uid;
	private String  screenName;
	private String  name;
	
	private int     province;
	private int     city;
	private String  location;
	
	private String  description;
	private String  url;
	private String  profileImageUrl;
	private String  domain;
	
	private String  gender;
	private int     followersCount;
	private int     friendsCount;
	private int     statusesCount;
	private int     favouritesCount;
	
	private String  createdAt;
	private boolean following;
	private boolean verified;
	private boolean geoEnabled;
	private boolean allowAllActMsg;
	
	WeiboTweetUser() {
		super();
	}
	
	WeiboTweetUser(JSONObject jsonWeibotweetUser) throws Exception {
		init(jsonWeibotweetUser);
	}

	private void init(JSONObject jsonWeiboTweetUser) throws Exception {
		try {
			uid = jsonWeiboTweetUser.getInt("id");
			screenName = jsonWeiboTweetUser.getString("screen_name");
			name = jsonWeiboTweetUser.getString("name");

			province = jsonWeiboTweetUser.getInt("province");
			city = jsonWeiboTweetUser.getInt("city");
			location = jsonWeiboTweetUser.getString("location");
			
			description = jsonWeiboTweetUser.getString("description");
			url = jsonWeiboTweetUser.getString("url");
			profileImageUrl = jsonWeiboTweetUser.getString("profile_image_url");
			domain = jsonWeiboTweetUser.getString("domain");
			
			gender = jsonWeiboTweetUser.getString("gender");
			followersCount = jsonWeiboTweetUser.getInt("followers_count");
			friendsCount = jsonWeiboTweetUser.getInt("friends_count");
			statusesCount = jsonWeiboTweetUser.getInt("statuses_count");
			favouritesCount = jsonWeiboTweetUser.getInt("favourites_count");
			
			createdAt = jsonWeiboTweetUser.getString("created_at");
			following = jsonWeiboTweetUser.getBoolean("following");
			verified = jsonWeiboTweetUser.getBoolean("verified");
			geoEnabled = jsonWeiboTweetUser.getBoolean("geo_enabled");
			allowAllActMsg = jsonWeiboTweetUser.getBoolean("allow_all_act_msg");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "WeiboTweetUser [uid=" + uid + ", screenName=" + screenName
				+ ", name=" + name + ", province=" + province + ", city="
				+ city + ", location=" + location + ", description="
				+ description + ", url=" + url + ", profileImageUrl="
				+ profileImageUrl + ", domain=" + domain + ", gender=" + gender
				+ ", followersCount=" + followersCount + ", friendsCount="
				+ friendsCount + ", statusesCount=" + statusesCount
				+ ", favouritesCount=" + favouritesCount + ", createdAt="
				+ createdAt + ", following=" + following + ", verified="
				+ verified + ", geoEnabled=" + geoEnabled + ", allowAllActMsg="
				+ allowAllActMsg + "]";
	}
	
	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
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

	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
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
	
}
