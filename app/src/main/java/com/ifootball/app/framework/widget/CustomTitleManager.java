package com.ifootball.app.framework.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifootball.app.R;

public class CustomTitleManager {

	private Activity mActivity;

	private TextView mTitleTextView;
	private Boolean mNoTitle;
	private ImageButton mBackButton;
	private LinearLayout mRightNormalButtonLayout;
	private LinearLayout mRightIconButtonLayout;
	private ImageButton mRightIconImageButton;

	public CustomTitleManager(Activity activity, Boolean noTitle) {

		mActivity = activity;
		mNoTitle = noTitle;
		if (noTitle) {
			mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		} else {
			mActivity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		}

	}

	public void setUp() {

		if (!mNoTitle) {
			LayoutInflater inflater = LayoutInflater.from(mActivity);
			View view = inflater.inflate(R.layout.frm_custom_title, null);
			int statusBarHeight = getStatusBarHeight();
			view.setPadding(0, statusBarHeight, 0, 0);
			mActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.frm_custom_title);

			mTitleTextView = (TextView) mActivity
					.findViewById(R.id.activity_title);
			mBackButton = (ImageButton) mActivity.findViewById(R.id.btn_back);
			mRightNormalButtonLayout = (LinearLayout) mActivity
					.findViewById(R.id.btn_right_normal);
			mRightIconButtonLayout = (LinearLayout) mActivity
					.findViewById(R.id.btn_right_icon);
			mRightIconImageButton = (ImageButton) mActivity
					.findViewById(R.id.btn_right_icon_btn);
		}
	}

	private int getStatusBarHeight() {
		int result = 0;
		int resourceId = mActivity.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = mActivity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public void setTitle(String title) {

		if (mTitleTextView != null) {
			mTitleTextView.setText(title);
		}
	}

	public void setTitle(int title) {

		if (mTitleTextView != null) {
			mTitleTextView.setText(title);
		}
	}

	public void showBackButton(Boolean visible) {
		if (mBackButton != null) {
			mBackButton.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
	}

	public LinearLayout showRightNormalButton(Boolean visible, String title,
			View.OnClickListener listener) {
		if (mRightNormalButtonLayout != null) {
			mRightNormalButtonLayout.setVisibility(visible ? View.VISIBLE
					: View.GONE);
			Button btnButton = (Button) mRightNormalButtonLayout
					.findViewById(R.id.btn_right_normal_btn);
			btnButton.setText(title);
			btnButton.setOnClickListener(listener);
		}
		return mRightNormalButtonLayout;
	}

	public LinearLayout showRightIconButton(Boolean visible, int drawable,
			View.OnClickListener listener) {
		if (mRightIconImageButton != null) {
			mRightIconButtonLayout.setVisibility(visible ? View.VISIBLE
					: View.GONE);
			mRightIconImageButton.setImageDrawable(mActivity.getResources()
					.getDrawable(drawable));
			mRightIconImageButton.setOnClickListener(listener);
		}

		return mRightIconButtonLayout;
	}

	public ImageButton getRightIconButton() {
		return mRightIconImageButton;
	}
}
