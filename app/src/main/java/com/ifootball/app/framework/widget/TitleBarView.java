package com.ifootball.app.framework.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifootball.app.R;

public class TitleBarView extends LinearLayout implements OnClickListener {

	public interface TitleBarViewRightIconClick {
		public void onClick();
	}

	private Context mContext;
	private Activity mActivity;

	private TextView pageContent;
	private ImageView leftImageView;
	private ImageView rightImageView;
	private TitleBarViewRightIconClick rightClick;

	public TitleBarView(Context context) {
		this(context, null);
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		initView(context);

	}

	public void setRightClick(TitleBarViewRightIconClick rightClick) {
		this.rightClick = rightClick;
	}

	/**
	 * 
	 * @param leftDrawable
	 *            左边图标，Null表示不显示左边图标
	 * @param pageDescription
	 *            中间描述内容
	 * @param rightDrawable
	 *            右边图标，Null表示不显示右边图标
	 */
	public void setViewData(Drawable leftDrawable, String pageDescription,
			Drawable rightDrawable) {
		if (leftDrawable == null) {
			leftImageView.setVisibility(GONE);
		} else {
			leftImageView.setImageDrawable(leftDrawable);
		}
		if (rightDrawable == null) {
			rightImageView.setVisibility(GONE);
		} else {
			rightImageView.setImageDrawable(rightDrawable);
		}
		pageContent.setText(pageDescription);

	}

	private void initView(Context context) {
		View.inflate(context, R.layout.title_bar, this);
		pageContent = (TextView) findViewById(R.id.title_center_description);
		leftImageView = (ImageView) findViewById(R.id.title_left_icon);
		rightImageView = (ImageView) findViewById(R.id.title_right_icon);
		leftImageView.setOnClickListener(this);
		rightImageView.setOnClickListener(this);

	}

	public void setActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_icon:
			mActivity.finish();
			break;

		case R.id.title_right_icon:
			rightClick.onClick();
			break;
		}

	}
}
