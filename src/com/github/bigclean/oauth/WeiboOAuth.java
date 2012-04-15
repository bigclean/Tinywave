package com.github.bigclean.oauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class WeiboOAuth {
	// Weibo Oauth version 1.x api
	// TODO: update to Oauth version 2.x
	public static final String REQUEST_URL           = "http://api.t.sina.com.cn/oauth/request_token";
	public static final String ACCESS_TOKEN_URL      = "http://api.t.sina.com.cn/oauth/access_token";
	public static final String AUTHENTICATE_URL      = "http://api.t.sina.com.cn/oauth/authorize";
	// XXX: when 'oauthweibo' is changed to 'OauthWeibo', it does not work, why?
	public static final String OAUTH_CALLBACK_SCHEME = "tinywave";
	public static final String OAUTH_CALLBACK_HOST   = "authorize";
	public static final String OAUTH_CALLBACK_URL    = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	
	private String consumerKey;
	private String consumerSecret;
	private String userId;
	
	private OAuthConsumer weiboConsumer;
	private OAuthProvider weiboProvider;
	
	public WeiboOAuth() {
		this(WeiboOAuthKeys.CONSUMER_KEY, WeiboOAuthKeys.CONSUMER_SECRET);
	}
	
	public WeiboOAuth(String consumerKey, String consumerSecret) {
		this.consumerKey    = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public OAuthConsumer getConsumer() {
		return weiboConsumer;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getRequestToken(Context context) {
		weiboConsumer = new CommonsHttpOAuthConsumer(
				consumerKey, consumerSecret);
		weiboProvider = new CommonsHttpOAuthProvider(
				REQUEST_URL, ACCESS_TOKEN_URL, AUTHENTICATE_URL);
		String authUrl = null;
		
		try {
			authUrl = weiboProvider.retrieveRequestToken(weiboConsumer, OAUTH_CALLBACK_URL);
			Log.d("OAuthWeibo", authUrl);
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return authUrl;
	}
	
	public boolean getAccessToken(Uri uri, SharedPreferences prefs) {
		boolean isSuccessed       = false;
		String  accessToken       = null;
		String  accessTokenSecret = null;
		
		String weiboVerifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		Log.d("OAuthWeibo", weiboVerifier);
		weiboProvider.setOAuth10a(true); // important, why?
		
		try {
			weiboProvider.retrieveAccessToken(weiboConsumer, weiboVerifier);
			userId            = weiboProvider.getResponseParameters().getFirst("user_id");
			accessToken       = weiboConsumer.getToken();
			accessTokenSecret = weiboConsumer.getTokenSecret();
			
			final Editor weiboEditor = prefs.edit();
			weiboEditor.putString(OAuth.OAUTH_TOKEN, accessToken);
			weiboEditor.putString(OAuth.OAUTH_TOKEN_SECRET, accessTokenSecret);
			weiboEditor.commit();
				
			Log.d("OAuthWeibo", userId);
			Log.d("OAuthWeibo", accessToken);
			Log.d("OAuthWeibo", accessTokenSecret);

			isSuccessed = true;
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSuccessed;
	}
	
}