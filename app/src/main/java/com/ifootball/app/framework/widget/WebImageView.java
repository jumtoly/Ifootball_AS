/* Copyright (c) 2009 Matthias Kaeppler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ifootball.app.framework.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.ImageUrlUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


/**
 * An image view that fetches its image off the web using the supplied URL.
 * While the image is being downloaded, a progress indicator will be shown.
 *
 * @author Matthias Kaeppler
 */
public class WebImageView extends ViewSwitcher {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.chinamobilemarket.app";

    private String imageUrl;

    private boolean isLoaded;

    private ProgressBar loadingSpinner;

    private ImageView imageView;

    private ScaleType scaleType = ScaleType.FIT_CENTER;

    private Drawable progressDrawable;

    private volatile int errorResId;

    public WebImageView(Context context) {
        super(context);

        init(context);
    }

    public WebImageView(Context context, int progressResId) {
        super(context);

        progressDrawable = getResources().getDrawable(progressResId);

        init(context);
    }

    public WebImageView(Context context, ImageView imageView) {
        super(context);

        addLoadingSpinnerView(context);
        addImageView(context, imageView);
    }

    public WebImageView(Context context, AttributeSet attributes) {
        super(context, attributes);
        int progressResId = attributes.getAttributeResourceValue(NAMESPACE, "progressResId", -1);

        if (progressResId != -1) {
            progressDrawable = context.getResources().getDrawable(progressResId);
        }
        errorResId = attributes.getAttributeResourceValue(NAMESPACE, "errorResId", -1);

        init(context);
    }

    private void init(Context context) {

        addLoadingSpinnerView(context);
        addImageView(context);
    }

    private void addLoadingSpinnerView(Context context) {
        loadingSpinner = new ProgressBar(context);
        loadingSpinner.setIndeterminate(true);
        if (this.progressDrawable == null) {
            this.progressDrawable = loadingSpinner.getIndeterminateDrawable();
            loadingSpinner.setProgressDrawable(progressDrawable);
        } else {
            loadingSpinner.setProgressDrawable(progressDrawable);
            // loadingSpinner.setIndeterminateDrawable(progressDrawable);
            if (progressDrawable instanceof AnimationDrawable) {
                ((AnimationDrawable) progressDrawable).start();
            }
        }
        LayoutParams lp = new LayoutParams(progressDrawable.getIntrinsicWidth(), progressDrawable.getIntrinsicHeight());
        lp.gravity = Gravity.CENTER;

        addView(loadingSpinner, 0, lp);
    }

    private void addImageView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(scaleType);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        addView(imageView, 1, lp);
    }

    private void addImageView(Context context, ImageView imageview) {
        imageView = imageview;
        imageView.setScaleType(ScaleType.MATRIX);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        addView(imageView, 1, lp);
    }

    /**
     * Use this method to trigger the image download if you had previously set
     * autoLoad to false.
     */
    public void loadImage() {
        if (imageUrl == null) {
            isLoaded = true;
            if (errorResId != -1) {
                imageView.setImageResource(errorResId);
            }
            setDisplayedChild(1);
            return;
        }


        ImageLoaderUtil.displayImage(imageUrl, imageView, 0, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                isLoaded = true;
                setDisplayedChild(1);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
                isLoaded = true;
                if (errorResId != -1) {
                    imageView.setImageResource(errorResId);
                }
                setDisplayedChild(1);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                isLoaded = true;
                setDisplayedChild(1);
            }
        });

//		ImageLoader.BindResult result = imageLoader.bind(imageView, imageUrl, this);
//		
//		if (result == ImageLoader.BindResult.OK) {
//			isLoaded = true;
//			setDisplayedChild(1);
//		} else if (result == ImageLoader.BindResult.ERROR) {
//			isLoaded = true;
//			if (errorResId != -1) {
//				imageView.setImageResource(errorResId);
//			}
//			setDisplayedChild(1);
//		}
    }

    public void loadImage(int width) {
        imageUrl = ImageUrlUtil.getImageUrl(imageUrl, width);

        loadImage();
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getErrorResId() {
        return this.errorResId;
    }

    public void setErrorResId(int id) {
        errorResId = id;
    }

    /**
     * Returns the URL of the image to show
     *
     * @return
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Often you have resources which usually have an image, but some don't. For
     * these cases, use this method to supply a placeholder drawable which will
     * be loaded instead of a web image.
     *
     * @param imageResourceId the resource of the placeholder image drawable
     */
    public void setNoImageDrawable(int imageResourceId) {
        imageView.setImageDrawable(getContext().getResources().getDrawable(imageResourceId));
        setDisplayedChild(1);
    }

    @Override
    public void reset() {
        super.reset();

        this.setDisplayedChild(0);
    }

    public ImageView getImageView() {
        return imageView;
    }
}