package com.ifootball.app.framework.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class EditTextScrollView extends ScrollView {

	public EditTextScrollView(Context context) {
		super(context);
	}

	public EditTextScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EditTextScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
		return true;
	}

}