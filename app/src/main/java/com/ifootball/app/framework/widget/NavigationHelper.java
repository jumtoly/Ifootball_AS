package com.ifootball.app.framework.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ifootball.app.activity.career.CareerActivity;
import com.ifootball.app.activity.found.FoundActivity;
import com.ifootball.app.activity.green.GreenActivity;
import com.ifootball.app.activity.stand.StandActivity;
import com.ifootball.app.R;

public class NavigationHelper {

    public static final int STAND = 10;
    public static final int GREEN = 11;
    public static final int FOUND = 12;
    public static final int CAREER = 13;
    /**
     * the page has navigation bar ,but no tab selected.
     */
    public static final int DEFAULT = 0;
    /**
     * the page has no navigation bar.
     */
    public static final int NONE = -1;

    private Activity mActivity;
    private BroadcastReceiver mBroadcastReceiver;

    private int mItemTextColor;
    private int mItemTextPressedColor;
    private int mItemLinePressedColor;
    ;
    private int mItemLineDefaultColor;

    private Button mStandActionView;
    private Button mGreenActionView;
    private Button mfoundActionView;
    private Button mCareerActionView;

    private Button mPreviousSelectedActionView;

    public NavigationHelper(Activity activity) {

        mActivity = activity;
    }

    public BroadcastReceiver getCartItemsCountChangedReceiver() {

        if (mBroadcastReceiver == null) {
            mBroadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    String action = intent.getAction();
                    //					int quantity = intent.getIntExtra(CartUtil.NUMBER, 0);
                    //					if (action.equals(CartUtil.NUMBER_CHANGED_ACTION)) {
                    //
                    //						setCartItemsCount(quantity);
                    //					}
                }
            };
        }

        return mBroadcastReceiver;
    }

    public void setCartItemsCount(int quantity) {

        TextView cartItemsCountTextView = (TextView) mActivity
                .findViewById(R.id.cart_items_count_textview);
        if (cartItemsCountTextView == null) {
            return;
        }

        if (quantity == 0) {
            cartItemsCountTextView.setVisibility(View.GONE);
        } else {
            cartItemsCountTextView.setVisibility(View.VISIBLE);
            //if qty >=100 ,then always show 99+ only.
            if (quantity >= 100) {
                cartItemsCountTextView.setText("99+");
            } else {
                cartItemsCountTextView.setText(String.valueOf(quantity));
            }
        }
    }

    public void setNavigationActionEvent() {

        int qty = 0;//CartUtil.getQuantity();

        setCartItemsCount(qty);

        mItemTextColor = mActivity.getResources().getColor(
                R.color.navigation_bar_item_text);
        mItemTextPressedColor = mActivity.getResources().getColor(
                R.color.navigation_bar_item_text_pressed);
        mItemLinePressedColor = mActivity.getResources().getColor(
                R.color.navigation_bar_line);
        mItemLineDefaultColor = mActivity.getResources().getColor(
                R.color.navigation_bar_line);

        mStandActionView = (Button) mActivity
                .findViewById(R.id.navigation_bar_item_stand);
        mGreenActionView = (Button) mActivity
                .findViewById(R.id.navigation_bar_item_green);
        mfoundActionView = (Button) mActivity
                .findViewById(R.id.navigation_bar_item_found);
        mCareerActionView = (Button) mActivity
                .findViewById(R.id.navigation_bar_item_career);

        ActionEventResponser actionEventResponser = new ActionEventResponser(
                mActivity);

        if (mStandActionView != null) {
            mStandActionView.setOnClickListener(actionEventResponser);
        }
        if (mGreenActionView != null) {
            mGreenActionView.setOnClickListener(actionEventResponser);
        }
        if (mfoundActionView != null) {
            mfoundActionView.setOnClickListener(actionEventResponser);
        }
        if (mCareerActionView != null) {
            mCareerActionView.setOnClickListener(actionEventResponser);
        }
    }

    /**
     * Set current selected navigation tab
     *
     * @param tab NavigationHelper.HOME,NavigationHelper.CATEGORY,
     *            NavigationHelper.SEARCH,NavigationHelper.CART
     *            NavigationHelper.MORE,NavigationHelper.DEFAULT
     */
    public void setSelectedNavigationTab(int tab) {

        restoreViewState();

        switch (tab) {

            case STAND:

                mActivity.findViewById(R.id.navigation_bar_item_stand_line)
                        .setBackgroundColor(mItemLinePressedColor);

                mStandActionView.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.ico_mark1_on, 0, 0);
                mStandActionView.setTextColor(mItemTextPressedColor);
                mStandActionView.setClickable(false);
                break;
            case GREEN:

                mActivity.findViewById(R.id.navigation_bar_item_green_line)
                        .setBackgroundColor(mItemLinePressedColor);

                mGreenActionView.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.ico_mark2_on, 0, 0);
                mGreenActionView.setTextColor(mItemTextPressedColor);
                mGreenActionView.setClickable(false);
                break;
            case FOUND:

                mActivity.findViewById(R.id.navigation_bar_item_found_line)
                        .setBackgroundColor(mItemLinePressedColor);

                mfoundActionView.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.ico_mark3_on, 0, 0);
                mfoundActionView.setTextColor(mItemTextPressedColor);
                mfoundActionView.setClickable(false);
                break;
            case CAREER:

                mActivity.findViewById(R.id.navigation_bar_item_career_line)
                        .setBackgroundColor(mItemLinePressedColor);

                mCareerActionView.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.ico_mark4_on, 0, 0);
                mCareerActionView.setTextColor(mItemTextPressedColor);
                mCareerActionView.setClickable(false);
                break;
            default:
                break;
        }
    }

    private void restoreViewState() {

        if (mPreviousSelectedActionView != null) {

            switch (mPreviousSelectedActionView.getId()) {

                case R.id.navigation_bar_item_stand:
                    mActivity.findViewById(R.id.navigation_bar_item_stand_line)
                            .setBackgroundColor(mItemLineDefaultColor);

                    mPreviousSelectedActionView
                            .setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.mipmap.ico_mark1, 0, 0);
                    mPreviousSelectedActionView.setTextColor(mItemTextColor);
                    break;

                case R.id.navigation_bar_item_green:
                    mActivity.findViewById(R.id.navigation_bar_item_green_line)
                            .setBackgroundColor(mItemLineDefaultColor);

                    mPreviousSelectedActionView
                            .setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.mipmap.ico_mark2, 0, 0);
                    mPreviousSelectedActionView.setTextColor(mItemTextColor);

                    break;
                case R.id.navigation_bar_item_found:
                    mActivity.findViewById(R.id.navigation_bar_item_found_line)
                            .setBackgroundColor(mItemLineDefaultColor);

                    mPreviousSelectedActionView
                            .setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.mipmap.ico_mark3, 0, 0);
                    mPreviousSelectedActionView.setTextColor(mItemTextColor);

                    break;
                case R.id.navigation_bar_item_career:
                    mActivity.findViewById(R.id.navigation_bar_item_career_line)
                            .setBackgroundColor(mItemLineDefaultColor);

                    mPreviousSelectedActionView
                            .setCompoundDrawablesWithIntrinsicBounds(0,
                                    R.mipmap.ico_mark4, 0, 0);
                    mPreviousSelectedActionView.setTextColor(mItemTextColor);

                    break;
            }
        }
    }

    private class ActionEventResponser implements View.OnClickListener {

        private Activity mActivity;

        public ActionEventResponser(Activity mActivity) {

            this.mActivity = mActivity;
        }

        @Override
        public void onClick(View v) {

            Button button = (Button) v;
            restoreViewState();
            mPreviousSelectedActionView = button;

            switch (v.getId()) {

                case R.id.navigation_bar_item_stand:
                    redirect(StandActivity.class,
                            Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);
                    break;
                case R.id.navigation_bar_item_green:

                    redirect(GreenActivity.class,
                            Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);
                    break;
                case R.id.navigation_bar_item_found:

                    redirect(FoundActivity.class,
                            Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);
                    break;
                case R.id.navigation_bar_item_career:

                    redirect(CareerActivity.class,
                            Intent.FLAG_ACTIVITY_NO_ANIMATION, 0, 0);
                    break;
            }
        }

        /**
         * redirect to target page
         *
         * @param targetActivity target page
         * @param flags          for activity self parameter
         * @param enterAnim      0 if no need anim
         * @param exitAnim       0 if no need anim
         */
        @SuppressWarnings("rawtypes")
        private void redirect(Class targetActivity, int flags, int enterAnim,
                              int exitAnim) {

            Intent intent = new Intent(mActivity, targetActivity);
            intent.setFlags(flags);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(enterAnim, exitAnim);

        }
    }
}
