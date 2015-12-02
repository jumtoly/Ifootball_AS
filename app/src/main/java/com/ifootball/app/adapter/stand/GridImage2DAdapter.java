package com.ifootball.app.adapter.stand;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ifootball.app.R;
import com.ifootball.app.entity.release.PictureInfo;
import com.ifootball.app.util.ImageLoaderUtil;

public class GridImage2DAdapter extends BaseAdapter {

    private Context mContext;
    private List<PictureInfo> mBitmaps;
    private boolean isAllData;
    private List<String> mPics;
    private boolean isDetails = true;

    public GridImage2DAdapter(Context context) {
        this.mContext = context;
    }

    public GridImage2DAdapter(Context context, List<String> mPics,
                              boolean isDetails) {
        this.mContext = context;
        this.mPics = mPics;
        this.isDetails = isDetails;
    }

    public GridImage2DAdapter(Context context, List<PictureInfo> mBitmaps) {
        this.mContext = context;
        this.mBitmaps = mBitmaps;
    }

    @Override
    public int getCount() {
        return mPics.size();
        /*return isDetails ? mBitmaps.size() : (mPics.size() <= 4 ? mPics.size()
                : 4);*/

    }

    @Override
    public Object getItem(int position) {
        return mBitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler vHodler = new ViewHodler();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.itme_gridimage, null);

            vHodler.imageView = (ImageView) convertView
                    .findViewById(R.id.item_gridimage_iv);
            convertView.setTag(vHodler);
        } else {
            vHodler = (ViewHodler) convertView.getTag();
        }
       /* if (isDetails) {
            ImageLoaderUtil.displayImage(mBitmaps.get(position).getPicUrl(),
                    vHodler.imageView, R.mipmap.app_icon);
        } else {*/
        ImageLoaderUtil.displayImage(mPics.get(position),
                vHodler.imageView, R.mipmap.app_icon);
//        }

        return convertView;
    }

    class ViewHodler {
        public ImageView imageView;
    }
}
