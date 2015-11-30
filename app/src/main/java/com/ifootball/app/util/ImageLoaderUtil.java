package com.ifootball.app.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ifootball.app.entity.SettingsUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ImageLoaderUtil {
	public static void displayImage(String imageUrl, ImageView imageView,
			int defaultImage) {
		displayImage(imageUrl, imageView, defaultImage, null);
	}

	public static void displayImage(String imageUrl, ImageView imageView,
			int defaultImage, ImageLoadingListener callback) {
		if (StringUtil.isEmpty(imageUrl)) {
			imageView.setImageResource(defaultImage);
			return;
		}
		// 非图文模式，不加载图片
		if (!SettingsUtil.getImageSettings()) {
			imageView.setImageResource(defaultImage);
			return;
		}
		DisplayImageOptions options;
		if (defaultImage == 0) {
			options = new DisplayImageOptions.Builder()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		} else {
			options = new DisplayImageOptions.Builder()
					.showStubImage(defaultImage)
					.showImageForEmptyUri(defaultImage)
					.showImageOnFail(defaultImage)
					.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		}

		ImageLoader.getInstance().displayImage(imageUrl, imageView, options,
				callback);
	}
	
	public static void displayImage(String imageUrl, ImageView imageView,
			int defaultImage,final Bitmap bitmap, ImageLoadingListener callback) {
		if (StringUtil.isEmpty(imageUrl)) {
			imageView.setImageResource(defaultImage);
			return;
		}
		// 非图文模式，不加载图片
		if (!SettingsUtil.getImageSettings()) {
			imageView.setImageResource(defaultImage);
			return;
		}
		DisplayImageOptions options;
		if (defaultImage == 0) {
			options = new DisplayImageOptions.Builder()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		} else {
			options = new DisplayImageOptions.Builder()
					.showStubImage(defaultImage)
					.showImageForEmptyUri(defaultImage)
					.showImageOnFail(defaultImage)
					.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		}

		ImageLoader.getInstance().displayImage(imageUrl, imageView, options,
				callback);
		
	}

	public static boolean clearCache() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();

		return true;
	}
}
