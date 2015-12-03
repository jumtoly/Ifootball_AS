package com.ifootball.app.adapter.green;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ifootball.app.R;
import com.ifootball.app.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class VenueBannerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mBannerList;

    public VenueBannerAdapter(Context context, List<String> banners) {
        this.mContext = context;
        if (banners != null) {
            this.mBannerList = banners;
        } else {
            this.mBannerList = new ArrayList<String>();
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return mBannerList.size();
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_green_venue_detail_banner_imageview, null, false);
        ImageView cellImageView = (ImageView) view
                .findViewById(R.id.banner_cell_imageview);
        String imageUrl = "";
        if (getRealCount() != 0) {
            imageUrl = mBannerList.get(position % getRealCount());
        }
        setImageSrc(cellImageView, imageUrl);
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    private void setImageSrc(ImageView imageView, String url) {
        if (url != null && !"".equals(url)) {
            ImageLoaderUtil.displayImage(url, imageView,
                    R.mipmap.ico_football);
        } else {
            setImageDefault(imageView);
        }
    }

    private void setImageDefault(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mContext
                .getResources().getDrawable(R.mipmap.ico_football);
        if (bitmapDrawable != null) {
            imageView.setImageBitmap(bitmapDrawable.getBitmap());
        }
    }
}
