package com.github.bigclean.data;

import java.util.ArrayList;

import com.github.bigclean.TinywaveApp;
import com.github.bigclean.model.WeiboComment;
import com.github.bigclean.model.WeiboFavorite;
import com.github.bigclean.model.WeiboStatus;
import com.github.bigclean.model.WeiboUser;

public class WeiboManager {
	private final WeiboDatabase db;
	
	public WeiboManager() {
		db = TinywaveApp.getInstance().getDatabase();
	}
	
	// action of fetching statuses
	public ArrayList<WeiboStatus> getWeiboStatuses(Integer type) {
		return db.selectWeiboStatuses(type);
	}
	
	public ArrayList<WeiboStatus> getWeiboPublicStatuses() {
		return getWeiboStatuses(WeiboStatus.STATUS_TYPE_PUBLIC);
	}
	
	public ArrayList<WeiboStatus> getWeiboHomeStatuses() {
		return getWeiboStatuses(WeiboStatus.STATUS_TYPE_HOME);
	}
	
	public ArrayList<WeiboStatus> getWeiboUserStatuses() {
		return getWeiboStatuses(WeiboStatus.STATUS_TYPE_USER);
	}
	
	public ArrayList<WeiboStatus> getWeiboMentionsStatuses() {
		return getWeiboStatuses(WeiboStatus.STATUS_TYPE_MENTIONS);
	}
	
	// action of refreshing statuses(timeline)
	public void refreshWeiboTimeline(ArrayList<WeiboStatus> statuses, Integer type) {
		WeiboStatus.setWeiboStatusesType(statuses, type);
		db.updateWeiboStatuses(statuses, type);
	}
	
	public void refreshWeiboPublicTimeline(ArrayList<WeiboStatus> statuses) {
		refreshWeiboTimeline(statuses, WeiboStatus.STATUS_TYPE_PUBLIC);
	}
	
	public void refreshWeiboHomeTimeline(ArrayList<WeiboStatus> statuses) {
		refreshWeiboTimeline(statuses, WeiboStatus.STATUS_TYPE_HOME);
	}
	
	public void refreshWeiboUserTimeline(ArrayList<WeiboStatus> statuses) {
		refreshWeiboTimeline(statuses, WeiboStatus.STATUS_TYPE_USER);
	}
	
	public void refreshWeiboMentionsTimeline(ArrayList<WeiboStatus> statuses) {
		refreshWeiboTimeline(statuses, WeiboStatus.STATUS_TYPE_MENTIONS);
	}
	
	public WeiboStatus getWeiboStatusById(Long id, Integer type) {
		return db.selectWeiboStatus(id, type);
	}
	
	// action of deleting statuses
	public boolean deleteWeiboStatuses(Integer type) {
		return db.deleteWeiboStatuses(type);
	}
	
	public boolean deleteWeiboHomeStatuses() {
		return deleteWeiboStatuses(WeiboStatus.STATUS_TYPE_HOME);
	}
	
	public boolean deleteWeiboUserStatuses() {
		return deleteWeiboStatuses(WeiboStatus.STATUS_TYPE_USER);
	}
	
	public boolean deleteWeiboMentionsStatuses() {
		return deleteWeiboStatuses(WeiboStatus.STATUS_TYPE_MENTIONS);
	}
	
	public boolean deleteWeiboPublicStatuses() {
		return deleteWeiboStatuses(WeiboStatus.STATUS_TYPE_PUBLIC);
	}
	
	public ArrayList<WeiboComment> getWeiboComments(Integer type) {
		return db.selectWeiboComments(type);
	}
	
	public ArrayList<WeiboComment> getWeiboByMeComments() {
		return getWeiboComments(WeiboComment.COMMENT_TYPE_BY_ME);
	}
	
	public ArrayList<WeiboComment> getWeiboToMeComments() {
		return getWeiboComments(WeiboComment.COMMENT_TYPE_TO_ME);
	}
	
	public ArrayList<WeiboComment> getWeiboMentionComments() {
		return getWeiboComments(WeiboComment.COMMENT_TYPE_MENTIONS);
	}
	
	public WeiboComment getWeiboCommentById(Long id) {
		return db.selectWeiboComment(id);
	}
	
	public void refreshWeiboComments(ArrayList<WeiboComment> comments, Integer type) {
		WeiboComment.setWeiboCommentsType(comments, type);
		db.updateWeiboComments(comments, type);
	}
	
	public void refreshWeiboByMeComments(ArrayList<WeiboComment> comments) {
		refreshWeiboComments(comments, WeiboComment.COMMENT_TYPE_BY_ME);
	}
	
	public void refreshWeiboToMeComments(ArrayList<WeiboComment> comments) {
		refreshWeiboComments(comments, WeiboComment.COMMENT_TYPE_TO_ME);
	}
	
	public void refreshWeiboMentionsComments(ArrayList<WeiboComment> comments) {
		refreshWeiboComments(comments, WeiboComment.COMMENT_TYPE_MENTIONS);
	}
	
	public ArrayList<WeiboFavorite> getWeiboFavorites() {
		return db.selectWeiboFavorites();
	}
	
	public void refreshWeiboFavorites(ArrayList<WeiboFavorite> favorites) {
		db.updateWeiboFavorites(favorites);
	}
	
	public void addWeiboFavorite(Long mid) {
	}
	
	public ArrayList<WeiboUser> getWeiboUsers(Integer type) {
		return db.selectWeiboUsers(type);
	}
	
	public WeiboUser getWeiboUser(Long uid) {
		return db.selectWeiboUser(uid);
	}
	
	public void follow(Long uid) {
	}
	
	public void unfollow(Long uid) {
	}

}