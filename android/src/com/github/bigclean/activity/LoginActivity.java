package com.github.bigclean.activity;

import com.github.bigclean.oauth.WeiboOAuth;
import com.github.bigclean.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LoginActivity extends Activity {
	public WeiboOAuth mWeiboOAuth = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mWeiboOAuth = new WeiboOAuth();
		//mWeiboOAuth.getRequestToken(this);
		mWeiboOAuth.getRequestToken(LoginActivity.this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		
		SharedPreferences weiboPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		if (uri != null && uri.toString().startsWith(WeiboOAuth.OAUTH_CALLBACK_URL)) {
			mWeiboOAuth.getAccessToken(uri, weiboPrefs);
		}
	}
		
}