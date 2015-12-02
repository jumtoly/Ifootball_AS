package com.ifootball.app.activity.green;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.common.Common;
import com.ifootball.app.entity.VenueSearchCriteria;
import com.ifootball.app.entity.VenueSearchResultItem;
import com.ifootball.app.framework.adapter.MyDecoratedAdapter;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.framework.content.CBCollectionResolver;
import com.ifootball.app.framework.content.CollectionStateObserver;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.IntentUtil;

public class GreenActivity extends BaseActivity implements View.OnClickListener {
    private final static int MSG_GET_DATA = 0;

    private TextView mSelectMap;
    private TextView mCurrentCity;
    private RadioButton districtButton;
    private RadioButton categoryButton;
    private RadioGroup categorySelectorContainer;
    private RadioGroup districtSelectorContainer;
    private ListView mVenueListView;
    VenueSearchCriteria criteria;

    private MyVenueListAdapter mAdapter;
    private CollectionStateObserver mObserver;
    private CBCollectionResolver<VenueSearchResultItem> mResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_green, "", NavigationHelper.GREEN,
                true, true);
        findView();

    }

    private void findView() {
        mSelectMap = (TextView) findViewById(R.id.frg_green_title_map_seclect);
        mSelectMap.setOnClickListener(this);
        mCurrentCity = (TextView) findViewById(R.id.frg_green_title_city);
        mCurrentCity.setText(MySharedCache.get(Common.CURRENT_CITY.name(), ""));
        mVenueListView = (ListView) findViewById(R.id.frg_green_listview);
        districtButton = (RadioButton) findViewById(R.id.frg_green_allarea);
        districtButton.setOnClickListener(this);
        categoryButton = (RadioButton) findViewById(R.id.frg_green_allcategory);
        categoryButton.setOnClickListener(this);
        categorySelectorContainer = (RadioGroup) findViewById(R.id.venue_selector_category_container);
        districtSelectorContainer = (RadioGroup) findViewById(R.id.venue_selector_district_container);

        categorySelectorContainer
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                        categoryButton.setText(rb.getText());
                        criteria.setCategory(rb.getTag().toString());
                        criteria.setPageNumber(1);
                        refresh();
                        categorySelectorContainer.setVisibility(View.GONE);
                    }
                });
        districtSelectorContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                districtButton.setText(rb.getText());
                criteria.setDistrictSysno(rb.getTag().toString());
                criteria.setPageNumber(1);
                refresh();
                districtSelectorContainer.setVisibility(View.GONE);
            }
        });
        mVenueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                VenueSearchResultItem info = (VenueSearchResultItem) parent
                        .getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("SysNo", info.getSysNo());
                IntentUtil.redirectToNextActivity(GreenActivity.this, VenueDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitAppUtil.exit(this);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }

    private void refresh() {
        mAdapter = new MyVenueListAdapter(this);
        mAdapter.setVisible(true);

        mVenueListView.setAdapter(mAdapter);
        mObserver.setAdapters(mAdapter);
        mObserver.showContent();
        mVenueListView
                .setOnScrollListener(new MyDecoratedAdapter.ListScrollListener(
                        mAdapter, mResolver));
        mAdapter.startQuery(mResolver);
    }

    private class MyVenueListAdapter extends
            MyDecoratedAdapter<VenueSearchResultItem> {
        private MyVenueListAdapter(Context context) {
            super(context);
            this.mContext = context;
            this.inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        private LayoutInflater inflater;
        private Context mContext;

        @Override
        protected View newErrorView(Context context, ViewGroup parent) {
            View view = inflater.inflate(R.layout.frm_list_item_error, parent,
                    false);
            view.findViewById(R.id.retry).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retry();
                        }
                    });

            return view;
        }

        @Override
        protected View newLoadingView(Context context, ViewGroup parent) {
            return inflater.inflate(R.layout.frm_list_item_loading, parent,
                    false);
        }

        @Override
        protected View newNormalView(int position, View convertView,
                                     ViewGroup parent) {
            final VenueSearchResultItem venueDetailsInfo = getItem(position);
            if (convertView == null || convertView.getTag() == null) {
                convertView = VenueListViewHolder.generateView(this.inflater);
            }
            VenueListViewHolder.fillData(convertView, venueDetailsInfo);
            return convertView;
        }
    }

    public static class VenueListViewHolder {

        private ImageView venueImageView;
        private TextView venueNameTextView;
        private TextView venueLocationTextView;
        private LinearLayout categoryLayout;
        private TextView venueDistanceTextView;
        private TextView palyerNumberTextView;
        private RatingBar venueScoreBar;
        private TextView venueScoreTextView;
        private TextView gotoMapTextView;

        public static View generateView(LayoutInflater layoutInflater) {
            VenueListViewHolder holder = new VenueListViewHolder();
            View convertView = layoutInflater
                    .inflate(R.layout.activity_green_item_venue, null);
            holder.venueImageView = (ImageView) convertView
                    .findViewById(R.id.venue_img);
            holder.venueNameTextView = (TextView) convertView
                    .findViewById(R.id.venue_name);
            holder.venueLocationTextView = (TextView) convertView
                    .findViewById(R.id.venue_location);

            holder.categoryLayout = (LinearLayout) convertView
                    .findViewById(R.id.venue_category_container);
            holder.venueDistanceTextView = (TextView) convertView
                    .findViewById(R.id.venue_distance);
            holder.palyerNumberTextView = (TextView) convertView
                    .findViewById(R.id.venue_palyernumber);
            holder.venueScoreBar = (RatingBar) convertView
                    .findViewById(R.id.venue_rating);
            holder.venueScoreTextView = (TextView) convertView
                    .findViewById(R.id.venue_score);
            holder.gotoMapTextView = (TextView) convertView
                    .findViewById(R.id.venue_map);
            convertView.setTag(holder);
            return convertView;
        }

        public static void fillData(final View convertView,
                                    final VenueSearchResultItem info) {
            VenueListViewHolder holder = (VenueListViewHolder) convertView
                    .getTag();
            // 图片
            ImageLoaderUtil.displayImage(info.getDefaultPicUrl(),
                    holder.venueImageView, R.mipmap.app_icon);
            holder.venueNameTextView.setText(info.getName());
            holder.venueLocationTextView.setText(info.getLocation());
            holder.categoryLayout.removeAllViews();
            for (String cateString : info.getCategory().split(" ")) {
                TextView t = new TextView(convertView.getContext());
                t.setText(cateString + "人场");
                t.setPadding(6, 2, 6, 2);
                t.setTextColor(convertView.getContext().getResources()
                        .getColor(R.color.white));
                t.setBackgroundResource(R.drawable.radius_redbg);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.setMargins(0, 0, 5, 0);
                holder.categoryLayout.addView(t, p);
            }
            holder.venueDistanceTextView.setText(Double.toString(info
                    .getDistance()));
            holder.palyerNumberTextView.setText(String.format("%s人最近踢过",
                    info.getPlayerNumber()));
            holder.venueScoreBar.setRating((float) info.getAVGScore());
            holder.venueScoreTextView.setText(String.format("评分%s分",
                    info.getAVGScore()));
            holder.gotoMapTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(convertView.getContext(),
                            MapActivity.class);
                    convertView.getContext().startActivity(intent);
                }
            });

        }
    }

}
