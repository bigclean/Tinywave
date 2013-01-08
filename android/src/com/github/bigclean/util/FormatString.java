package com.github.bigclean.util;

public class FormatString {
	/**
	 * Parse date/time from Weibo timestamp
	 * Original source format: Wed Sep 19 20:29:09 +0800 2012
	 * @param str Weibo timestamp literal string
	 * @return
	 */
	public static String formatWeiboTime(String str) {
		return null;
	}
	
	/**
	 * Fetch weibo source in readability style
	 * Original source format: <a href="http://mobileways.de/gravity" rel="nofollow">Gravity</a>
	 * @param str Weibo source literal string
	 * @return Weibo source
	 */
	// regular expression to fetch weibo source
	public static String formatWeiboSource(String str) {
		int sourceStartIndex = str.indexOf(">") + 1;
		int sourceEndIndex   = str.lastIndexOf("<");
		return str.substring(sourceStartIndex, sourceEndIndex);
	}
	
}