package com.github.bigclean.model;

import java.util.ArrayList;

public class WeiboCommentWrapper {
	private ArrayList<WeiboComment> comments;
	
	private long previousCursor;
	private long nextCursor;
	private int  totalNumber;
	
	public WeiboCommentWrapper(ArrayList<WeiboComment> comments,
			long previousCursor, long nextCursor, int totalNumber) {
		this.comments       = comments;
		this.previousCursor = previousCursor;
		this.nextCursor     = nextCursor;
		this.totalNumber    = totalNumber;
	}

	public ArrayList<WeiboComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<WeiboComment> comments) {
		this.comments = comments;
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