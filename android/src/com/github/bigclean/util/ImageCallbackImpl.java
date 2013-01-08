package com.github.bigclean.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageCallbackImpl implements AsyncImageLoader.ImageCallback {
	private ImageView imageView;
	
	public ImageCallbackImpl(ImageView imageView) {
		this.imageView = imageView;
	}
	
	@Override
	public void imageLoaded(Drawable imageDrawable) {
		imageView.setImageDrawable(imageDrawable);
	}

}