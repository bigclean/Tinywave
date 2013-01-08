package com.github.bigclean.model;

import java.util.ArrayList;

public class WeiboStatusWrapper {
	private ArrayList<WeiboStatus> statuses;
	
	private long previousCursor;
	private long nextCursor;
	private int  totalNumber;
	
	public WeiboStatusWrapper(ArrayList<WeiboStatus> statuses,
			long previousCursor, long nextCursor, int totalNumber) {
		this.statuses       = statuses;
		this.previousCursor = previousCursor;
		this.nextCursor     = nextCursor;
		this.totalNumber    = totalNumber;
	}

	public ArrayList<WeiboStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<WeiboStatus> statuses) {
		this.statuses = statuses;
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