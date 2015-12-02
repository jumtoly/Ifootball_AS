package com.ifootball.app.activity.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ifootball.app.R;
import com.ifootball.app.framework.widget.CustomTitleManager;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.AppManager;
import com.ifootball.app.util.DialogUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.SystemBarTintManager;
import com.ifootball.app.util.VersionUtil;

/**
 * This Class include Title Bar ,Navigation Bar and some other Broadcast
 * Receiver components.
 */
public class BaseActivity extends FragmentActivity {

    private CustomTitleManager mCustomTitleManager;
    private NavigationHelper mNavigationHelper;

    private BroadcastReceiver mCartNumberChangedBroadcastReceiver;

    private int mCurrentSelectedTab = NavigationHelper.DEFAULT;
    private ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        mNavigationHelper = new NavigationHelper(this);
        mCartNumberChangedBroadcastReceiver = mNavigationHelper
                .getCartItemsCountChangedReceiver();

    }

    private void setSystemBar(boolean hasTab) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.top_bar_bg);//通知栏所需颜色
            //			View childAt = ((ViewGroup) this.findViewById(android.R.id.content))
            //					.getChildAt(0);
            View view = getWindow().getDecorView().findViewById(
                    android.R.id.content);
            if (hasTab) {
                int statusBarHeight = getStatusBarHeight();
                view.setPadding(0, statusBarHeight, 0, 0);
            } else {
                Rect frame = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                view.setPadding(0, statusBarHeight, 0, 0);
            }


        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * Set content view with Title bar and Navigation bar
     *
     * @param layoutId  Page Content Layout
     * @param pageTitle Page Title
     * @param tab       The Current Selected Tab,
     *                  NavigationHelper.HOME,NavigationHelper.SEARCH
     *                  NavigationHelper.CATEGORY,NavigationHelper.CART
     *                  NavigationHelper.MORE
     */
    public void putContentView(int layoutId, String pageTitle, int tab,
                               boolean isSetBar, boolean hasTab) {

        Boolean noTitle = pageTitle == null || pageTitle.equals("");
        mCustomTitleManager = new CustomTitleManager(this, noTitle);
        setContentView(layoutId);
        if (!noTitle) {
            mCustomTitleManager.setUp();
            mCustomTitleManager.setTitle(pageTitle);
        }

        mCurrentSelectedTab = tab;
        if (mCurrentSelectedTab != NavigationHelper.NONE) {

            mNavigationHelper.setNavigationActionEvent();
            mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
        } else {

            unregisterReceiver();
        }
        if (isSetBar) {
            setSystemBar(hasTab);
        }

    }

    /**
     * Set content view
     *
     * @param layoutId
     * @param pageTitle
     * @param tab       NavigationHelper.HOME,NavigationHelper.SEARCH
     *                  NavigationHelper.CATEGORY,NavigationHelper.CART
     *                  NavigationHelper.MORE
     */
    public void putContentView(int layoutId, int pageTitle, int tab) {

        Boolean noTitle = pageTitle <= 0;

        mCustomTitleManager = new CustomTitleManager(this, noTitle);
        setContentView(layoutId);
        if (!noTitle) {
            mCustomTitleManager.setUp();
            mCustomTitleManager.setTitle(pageTitle);
        }

        mCurrentSelectedTab = tab;

        if (mCurrentSelectedTab != NavigationHelper.NONE) {

            mNavigationHelper.setNavigationActionEvent();
            mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
        } else {

            unregisterReceiver();
        }
    }

    public void putContentView(int layoutId, String pageTitle, boolean isSetBar, boolean hasTab) {

        putContentView(layoutId, pageTitle, NavigationHelper.DEFAULT, isSetBar, hasTab);
    }

    public void putContentView(int layoutId, int pageTitle) {

        putContentView(layoutId, pageTitle, NavigationHelper.DEFAULT);
    }

    public void setPageTitle(int pageTitle) {

        if (mCustomTitleManager != null) {
            mCustomTitleManager.setTitle(pageTitle);
        }
    }

    public void setPageTitle(String pageTitle) {

        if (mCustomTitleManager != null) {
            mCustomTitleManager.setTitle(pageTitle);
        }
    }

    public void showBackButton() {
        if (mCustomTitleManager != null) {
            mCustomTitleManager.showBackButton(true);
        }
    }

    public LinearLayout showRightNormalButton(String title,
                                              View.OnClickListener listener) {
        if (mCustomTitleManager != null) {
            return mCustomTitleManager.showRightNormalButton(true, title,
                    listener);
        }
        return null;
    }

    public LinearLayout showRightIconButton(int drawable,
                                            View.OnClickListener listener) {
        if (mCustomTitleManager != null) {
            return mCustomTitleManager.showRightIconButton(true, drawable,
                    listener);
        }
        return null;
    }

    public ImageButton getRightIconButton() {
        if (mCustomTitleManager != null) {
            return mCustomTitleManager.getRightIconButton();
        }
        return null;
    }

    private void registerReceiver() {

        if (mCurrentSelectedTab != NavigationHelper.NONE
                && mCartNumberChangedBroadcastReceiver != null) {

            //			IntentFilter filter = new IntentFilter(CartUtil.NUMBER_CHANGED_ACTION);
            //			this.registerReceiver(mCartNumberChangedBroadcastReceiver, filter);
        }
    }

    private void unregisterReceiver() {

        if (mCurrentSelectedTab != NavigationHelper.NONE
                && mCartNumberChangedBroadcastReceiver != null) {

            //			this.unregisterReceiver(mCartNumberChangedBroadcastReceiver);
        }
    }

    @Override
    protected void onRestart() {

        if (mCurrentSelectedTab != NavigationHelper.NONE
                && mNavigationHelper != null) {

            mNavigationHelper.setSelectedNavigationTab(mCurrentSelectedTab);
            //			mNavigationHelper.setCartItemsCount(CartUtil.getQuantity());
        }

        super.onRestart();
    }

    @Override
    protected void onPause() {

        unregisterReceiver();
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver();
        if (VersionUtil.getInstance().IsUpdate()) {
            IntentUtil.redirectToNextActivity(this, VersionActivity.class);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void showLoading(String tips) {
        closeLoading();
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = DialogUtil.getProgressDialog(this, tips);
            mLoadingDialog.show();
        } catch (Exception e) {
        }
    }

    public void showLoading(int id) {
        showLoading(this.getResources().getString(id));
    }

    public void closeLoading() {
        try {

            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

}