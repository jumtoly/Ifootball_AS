package com.ifootball.app.activity.green;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.adapter.green.VenueBannerAdapter;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.VenueDetailInfo;
import com.ifootball.app.entity.VenueDetailInfo.TagInfoVM;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.util.DisplayUtil;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.green.GreenService;
import com.neweggcn.lib.json.JsonParseException;

import java.io.IOException;

public class VenueDetailActivity extends BaseActivity {

    @SuppressWarnings("deprecation")
    private ViewPager mViewPager;
    private TextView nameTextView;
    private TextView addressTextView;
    private RatingBar ratingBar;
    private TextView scoreTextView;
    private LinearLayout categoryLinerLayout;
    private ImageView phoneImageView;
    private TextView descriptionTextView;
    private LinearLayout tagLinerLayout;
    private TextView playerTextView;
    private LinearLayout detailPlayerContainer;
    private RadioGroup mRadioGroup;
    private Handler mHandler;
    private int What_Time_Elapsed = 0;
    private int mCurrentItem;
    private int mVenueSysNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVenueSysNo = getIntent().getIntExtra("SysNo", 0);
        setContentView(R.layout.activity_green_venue_detail);
        TitleBarView view = (TitleBarView) findViewById(R.id.details_titlebar);
        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "球场概况", getResources().getDrawable(R.mipmap.ico_map));
        view.setActivity(this);

        view.setRightClick(new TitleBarView.TitleBarViewRightIconClick() {

            @Override
            public void onClick() {

                Intent intent = new Intent(VenueDetailActivity.this,
                        MapActivity.class);
                VenueDetailActivity.this.startActivity(intent);
            }
        });

        findView();
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == What_Time_Elapsed) {
                    scrollOneItem();
                }
            }

        };
        loadData();

    }

    private void sendScrollMessage() {
        mHandler.sendEmptyMessageDelayed(What_Time_Elapsed, 5000);
    }

    private void scrollOneItem() {
        mCurrentItem += 1;
        mViewPager.setCurrentItem(mCurrentItem);
        sendScrollMessage();
    }

    private void loadData() {

        new com.ifootball.app.util.MyAsyncTask<VenueDetailInfo>(this) {

            @Override
            public VenueDetailInfo callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new GreenService().LoadVenue(mVenueSysNo);
            }

            @Override
            public void onLoaded(VenueDetailInfo result) throws Exception {
                if (result != null) {
                    nameTextView.setText(result.getName());
                    addressTextView.setText(result.getLocation());
                    ratingBar.setRating((float) result.getScore());
                    scoreTextView.setText(String.format("评分%s",
                            result.getScore()));
                    for (String cateString : result.getCategory().split(",")) {
                        TextView t = new TextView(VenueDetailActivity.this);
                        t.setText(cateString + "人场");
                        t.setPadding(6, 2, 6, 2);
                        t.setTextColor(getResources().getColor(R.color.white));
                        t.setBackgroundResource(R.drawable.radius_redbg);
                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        p.setMargins(0, 0, 5, 0);
                        categoryLinerLayout.addView(t, p);
                    }
                    phoneImageView.setTag(result.getTelphone());
                    if (result.getDescription() == null) {
                        result.setDescription("暂无介绍");
                    }
                    descriptionTextView.setText(result.getDescription());
                    playerTextView.setText(String.format("%s人最近踢过", result
                            .getMatchLog().getSiginCount()));
                    String[] colorStrings = {"#e51400", "#339933", "#1ba1e2",
                            "#ff0097", "#a200ff", "#00aba9"};
                    // 标签
                    for (TagInfoVM tagInfoVM : result.getTagsList()) {
                        int index = ((int) (Math.random() * 100)) % 6;
                        TextView t = new TextView(VenueDetailActivity.this);
                        t.setText(tagInfoVM.getTag());
                        t.setPadding(6, 2, 6, 2);
                        t.setTextColor(Color.parseColor(colorStrings[index]));
                        int strokeWidth = 2; // 3dp 边框宽度
                        int roundRadius = 5; // 8dp 圆角半径
                        int strokeColor = Color.parseColor(colorStrings[index]);// 边框颜色
                        int fillColor = Color.parseColor("#FFFFFF");// 内部填充颜色
                        GradientDrawable gd = new GradientDrawable();// 创建drawable
                        gd.setColor(fillColor);
                        gd.setCornerRadius(roundRadius);
                        gd.setStroke(strokeWidth, strokeColor);
                        t.setBackgroundDrawable(gd);
                        // 打标签
                        t.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                new com.ifootball.app.util.MyAsyncTask<String>(
                                        VenueDetailActivity.this) {

                                    @Override
                                    public String callService()
                                            throws IOException,
                                            JsonParseException, BizException,
                                            ServiceException {
                                        return "";
                                    }

                                    @Override
                                    public void onLoaded(String result)
                                            throws Exception {

                                    }

                                }.execute();
                            }
                        });
                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        p.setMargins(0, 0, 5, 0);
                        tagLinerLayout.addView(t, p);

                        t = new TextView(VenueDetailActivity.this);
                        t.setText("(" + tagInfoVM.MarkCount + ")");
                        t.setTextColor(Color.parseColor("#cccccc"));
                        tagLinerLayout.addView(t, p);
                    }
                    final VenueBannerAdapter mBannerAdapter = new VenueBannerAdapter(
                            VenueDetailActivity.this, result.getPics());
                    mViewPager.setAdapter(mBannerAdapter);
                    // 设置viewpager
                    if (result.getPicList().size() > 1) {
                        mRadioGroup.setVisibility(View.VISIBLE);
                        generateIndicator(mRadioGroup, result.getPics().size(),
                                R.drawable.venue_pic_dot_selector);
                        mViewPager
                                .setOnPageChangeListener(new OnPageChangeListener() {

                                    @Override
                                    public void onPageSelected(int position) {
                                        if (mBannerAdapter.getRealCount() != 0) {
                                            mRadioGroup.check(position
                                                    % mBannerAdapter
                                                    .getRealCount());
                                        }
                                    }

                                    @Override
                                    public void onPageScrolled(int position,
                                                               float positionOffset,
                                                               int positionOffsetPixels) {
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(
                                            int state) {

                                    }
                                });

                        mViewPager.setOnTouchListener(new OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (mHandler != null) {
                                    mHandler.removeMessages(What_Time_Elapsed);
                                }
                                return false;
                            }
                        });

                        sendScrollMessage();
                    } else {
                        mRadioGroup.setVisibility(View.GONE);
                    }
                }
            }

        }.execute();

    }

    private void generateIndicator(RadioGroup radioGroup, int size, int selector) {
        radioGroup.removeAllViews();
        int radius = DisplayUtil.getPxByDp(VenueDetailActivity.this, 8);
        int margin = DisplayUtil.getPxByDp(VenueDetailActivity.this, 3);
        for (int i = 0; i < size; i++) {
            RadioButton radioButton = new RadioButton(VenueDetailActivity.this);
            radioButton.setId(i);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setBackgroundResource(selector);
            radioButton.setClickable(false);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(radius,
                    radius);
            lp.setMargins(margin, margin, margin, margin);
            radioGroup.addView(radioButton, lp);
        }
        radioGroup.clearCheck();
        radioGroup.check(0);
    }

    private void findView() {
        mViewPager = (ViewPager) findViewById(R.id.veneu_detail_ViewPager);
        nameTextView = (TextView) findViewById(R.id.venue_detail_name);
        addressTextView = (TextView) findViewById(R.id.venue_detail_address);
        ratingBar = (RatingBar) findViewById(R.id.venue_detail_rating);
        scoreTextView = (TextView) findViewById(R.id.venue_detail_score);
        categoryLinerLayout = (LinearLayout) findViewById(R.id.venue_detail_category_container);
        phoneImageView = (ImageView) findViewById(R.id.venue_detail_phone);
        descriptionTextView = (TextView) findViewById(R.id.venue_detail_description);
        tagLinerLayout = (LinearLayout) findViewById(R.id.venue_detail_tag_container);
        playerTextView = (TextView) findViewById(R.id.venue_detail_player);
        detailPlayerContainer = (LinearLayout) findViewById(R.id.venue_detail_player_container);
        mRadioGroup = (RadioGroup) findViewById(R.id.veneu_detail_indicators_radiogroup);
        phoneImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + v.getTag()));
                startService(intent);
            }
        });

    }

}
