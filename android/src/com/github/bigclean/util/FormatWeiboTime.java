package com.github.bigclean.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatWeiboTime {
	private static int MILL_MINUTE = 1000 * 60;
	private static int MILL_HOUR   = MILL_MINUTE * 60;
	private static int MILL_DAY    = MILL_HOUR * 24;
	
	public static String formatWeiboTimestamp(long timestamp) {
		long currentMillSeconds = System.currentTimeMillis();
		// compared with current time
		long calcMillSeconds    = currentMillSeconds * 1000 - timestamp;
		String timeStr = null;
		
		if (calcMillSeconds > MILL_DAY) {
			// more than 1 days
			timeStr = calcMillSeconds / MILL_DAY + "days ago";
		} else if (calcMillSeconds > MILL_HOUR) {
			// 1 hour < Gap < 24 hours
			timeStr = calcMillSeconds / MILL_HOUR + "hours ago";
		} else if (calcMillSeconds > MILL_MINUTE) {
			// 1 minute < Gap < 59 minutes
			timeStr = calcMillSeconds / MILL_MINUTE + "minutes ago";
		} else {
			// 1 second < Gap < 59 seconds
			timeStr = "Just now";
		}
		
		return timeStr;
	}
	
	public static String getStandardTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMonthddDay HH:MM");
		
		Date date = new Date(timestamp * 1000);
		sdf.format(date);
		
		return sdf.format(date);
	}

}