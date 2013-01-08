package com.github.bigclean.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	private HashMap<String, SoftReference<Drawable>> imageCache = null;
	
	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}
	
	public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Drawable) msg.obj);
			}
		};
		
		new Thread() {
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		
		return null;
	}
	
	public static Drawable loadImageFromUrl(String url) {
		InputStream is;
		Drawable drawable = null;
		try {
			is       = new URL(url).openStream();
			drawable = Drawable.createFromStream(is, "src");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return drawable;
	}
	
	// callback interface
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}

}