package com.github.bigclean.weibo;

import java.io.UnsupportedEncodingException;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.github.bigclean.oauth.WeiboOAuthKeys;

public class Weibo {
	private OAuthConsumer weiboConsumer;
	
	public Weibo() {
	}
	
	public Weibo(String token, String tokenSecret) {
		this(WeiboOAuthKeys.CONSUMER_KEY, WeiboOAuthKeys.CONSUMER_SECRET, token, tokenSecret);
	}
	
	public Weibo(String consumerKey, String consumerSecret,
			String token, String tokenSecret) {
		weiboConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		weiboConsumer.setTokenWithSecret(token, tokenSecret);
	}
	
	public void setToken() {
	}
	
	public String sendPostRequest(String url, List<? extends NameValuePair> params) {
		HttpPost weiboPost = new HttpPost(url);
		try {
			weiboPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendPostRequest(weiboPost);
	}
	
	public String sendPostRequest(HttpPost post) {
		String weiboResponse = null;
		
		try {
			weiboConsumer.sign(post);
			// see also: https://github.com/regrecall/WeiboA
			weiboResponse = new DefaultHttpClient().execute(post, new BasicResponseHandler());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weiboResponse;
	}
	
	public String sendGetRequest(HttpGet get) {
		String weiboResponse = null;
		
		try {
			weiboConsumer.sign(get);
			weiboResponse = new DefaultHttpClient().execute(get, new BasicResponseHandler());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weiboResponse;
	}		
	
}