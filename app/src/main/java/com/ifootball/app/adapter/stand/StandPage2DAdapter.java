package com.ifootball.app.adapter.stand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.ifootball.app.R;
import com.ifootball.app.activity.stand.SeeImageActivity;
import com.ifootball.app.entity.stand.DynamicInfo;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.adapter.MyDecoratedAdapter;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.framework.widget.release.NoScrollBarGridView;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class StandPage2DAdapter extends MyDecoratedAdapter<StandInfo> {


    private LayoutInflater mInflater;
    private List<StandInfo> mDatas;
    private Context mContext;
    private LinearLayout mLayout;
    private EditText mEditText;


    public StandPage2DAdapter(Context currentContext) {
        super(currentContext);
        this.mContext = currentContext;
        this.mInflater = (LayoutInflater) currentContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    protected View newErrorView(Context context, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.frm_list_item_error, parent,
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
        return mInflater.inflate(R.layout.frm_list_item_loading, parent,
                false);
    }

    @Override
    protected View newNormalView(int position, View convertView, ViewGroup parent) {
        final int currentPosition = position;
        MyViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = mInflater.inflate(R.layout.itme_stand_rostrum, null);
            holder = new MyViewHolder();
            initHoler(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        settingHoler(currentPosition, holder, position);
        return convertView;
    }


    private void initHoler(View convertView, final MyViewHolder holder) {
        holder.icon = (CircleImageView) convertView.findViewById(R.id.item_rostrum_icon);

        holder.name = (TextView) convertView.findViewById(R.id.item_rostrum_name);
        holder.imageViews = (NoScrollBarGridView) convertView.findViewById(R.id.item_rostrum_image_gridview);
        holder.operation = (LinearLayout) convertView.findViewById(R.id.item_rostrum_operation);
        holder.operation.setVisibility(View.GONE);
        holder.comment = (TextView) convertView.findViewById(R.id.item_rostrum_comment_num);
        holder.assists = (TextView) convertView.findViewById(R.id.item_rostrum_assists_num);
        holder.content = (TextView) convertView.findViewById(R.id.item_rostrum_content);
        holder.address = (TextView) convertView.findViewById(R.id.item_rostrum_address);
        holder.videoView = (VideoView) convertView.findViewById(R.id.item_rostrum_vv);
        holder.playVideo = (ImageView) convertView.findViewById(R.id.item_rostrum_paly);
        holder.fl = (FrameLayout) convertView.findViewById(R.id.item_rostrum_fl);
        holder.videoBgView = (ImageView) convertView.findViewById(R.id.item_rostrum_videobg);

        holder.imageViews.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private void settingHoler(final int currentPosition, final MyViewHolder holder, int position) {
        final StandInfo mStandInfo = getItem(position);
        ImageLoaderUtil.displayImage(mStandInfo.getAvatar(), holder.icon, R.mipmap.app_icon);
        holder.name.setText(mStandInfo.getNickName());
        holder.comment.setText(mStandInfo.getViewCount() + "");
        holder.assists.setText(mStandInfo.getEnjoyCount() + "");
        holder.content.setText(mStandInfo.getContent());
        holder.address.setText(mStandInfo.getPosition());
        if (mStandInfo.isVideo()) {
            //视频动态
            holder.imageViews.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.playVideo.setVisibility(View.VISIBLE);
            holder.fl.setVisibility(View.VISIBLE);
            holder.videoBgView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(mStandInfo.getVideoPath());
            holder.videoView.setVideoURI(uri);
            ImageLoaderUtil.displayImage(mStandInfo.getPicUrls().get(0),
                    holder.videoBgView, R.mipmap.app_icon);
            holder.playVideo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    holder.videoView.start();
                    holder.playVideo.setVisibility(View.GONE);
                    holder.videoBgView.setVisibility(View.GONE);
                }
            });

            holder.videoView.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    holder.playVideo.setVisibility(View.VISIBLE);

                }
            });
        } else {
            //图片动态
            holder.imageViews.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            holder.playVideo.setVisibility(View.GONE);
            holder.fl.setVisibility(View.GONE);
            holder.videoBgView.setVisibility(View.GONE);
            if (mStandInfo.getPicUrls() == null || mStandInfo.getPicUrls().size() < 0) {
                return;
            }
            holder.imageViews.setAdapter(new GridImage2DAdapter(mContext, mStandInfo.getPicUrls(), false));
            holder.imageViews.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(SeeImageActivity.SIGN_STAND_POSITION, position);
                    bundle.putStringArrayList(SeeImageActivity.SIGN_STAND_IMAGES, (ArrayList<String>) mStandInfo.getPicUrls());
                    IntentUtil.redirectToNextActivity(mContext, SeeImageActivity.class, bundle);
                    ((Activity) mContext).overridePendingTransition(R.anim.enter_scale, R.anim.out_scale);
                }
            });
        }
    }

    class MyViewHolder {
        CircleImageView icon;
        LinearLayout operation;
        TextView name;
        TextView comment;
        TextView assists;
        TextView content;
        NoScrollBarGridView imageViews;
        TextView address;
        VideoView videoView;
        ImageView playVideo;
        FrameLayout fl;
        ImageView videoBgView;
    }


}
