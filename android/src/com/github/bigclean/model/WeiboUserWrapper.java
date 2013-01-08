package com.github.bigclean.model;

import java.util.ArrayList;


public class WeiboUserWrapper {
	private ArrayList<WeiboUser> users;
	
	private long previousCursor;
	private long nextCursor;
	private int  totalNumber;
	
	public WeiboUserWrapper(ArrayList<WeiboUser> users,
			long previousCursor, long nextCursor, int totalNumber) {
		this.users          = users;
		this.previousCursor = previousCursor;
		this.nextCursor     = nextCursor;
		this.totalNumber    = totalNumber;
	}

	public ArrayList<WeiboUser> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<WeiboUser> users) {
		this.users = users;
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

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

}