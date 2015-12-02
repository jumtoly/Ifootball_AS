package com.ifootball.app.adapter.stand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ifootball.app.R;

import com.ifootball.app.activity.login.LoginActivity;
import com.ifootball.app.activity.stand.SeeImageActivity;
import com.ifootball.app.common.CommentTypeEnum;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.release.PictureInfo;
import com.ifootball.app.entity.stand.CommUPInfo;
import com.ifootball.app.entity.stand.CommentInfo;
import com.ifootball.app.entity.stand.UserDetailsInfo;
import com.ifootball.app.framework.content.CBContentResolver;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.framework.widget.release.NoScrollBarGridView;
import com.ifootball.app.listener.CheckLoginListener;
import com.ifootball.app.util.CustomerUtil;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.stand.StandService;

public class Details2Dadapter extends BaseAdapter {
    public static final String SIGN_DETAILS_POSITION = "sign_details_position";
    public static final String SIGN_DETAILS_BUNDLE = "sign_details_bundle";
    public static final String SIGN_DETAILS_IMAGES = "sign_details_images";

    private Context mContext;
    private UserDetailsInfo mUserDetailsInfo;

    public static final int USER_INFO = 0;
    public static final int ITERACTIVE_INFO = 1;
    private EditText mEditText;
    private LinearLayout mCommentLayout;
    private ImageView mAssisted;
    private boolean isAssisted = false;

    public Details2Dadapter(Context mContext, UserDetailsInfo mUserDetailsInfo) {
        super();
        this.mContext = mContext;
        this.mUserDetailsInfo = mUserDetailsInfo;
    }

    @Override
    public int getCount() {
        return mUserDetailsInfo.getCommentList().size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolderUser myViewHolderUser = null;
        MyViewHolderComment myViewHolderComment = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case USER_INFO:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.activity_stand_item_details_content, null);
                    myViewHolderUser = new MyViewHolderUser();
                    findUserInfoView(convertView, myViewHolderUser);
                    convertView.setTag(myViewHolderUser);
                    break;

                case ITERACTIVE_INFO:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.activity_stand_item_details_comment, null);
                    myViewHolderComment = new MyViewHolderComment();
                    findCommentInfoView(convertView, myViewHolderComment);
                    convertView.setTag(myViewHolderComment);
                    break;
            }
        } else {
            switch (type) {
                case USER_INFO:
                    myViewHolderUser = (MyViewHolderUser) convertView.getTag();
                    break;

                case ITERACTIVE_INFO:

                    myViewHolderComment = (MyViewHolderComment) convertView
                            .getTag();
                    break;
            }
        }
        switch (type) {
            case USER_INFO:
                setData2UserView(myViewHolderUser);
                break;

            case ITERACTIVE_INFO:

                setData2CommentView(myViewHolderComment, mUserDetailsInfo
                        .getCommentList().get(position - 1));
                break;
        }
        return convertView;
    }

    private void setData2CommentView(MyViewHolderComment myViewHolderComment,
                                     CommUPInfo mCommUPInfo) {
        myViewHolderComment.commentContent.setText(mCommUPInfo.getContent());
        myViewHolderComment.commentTime.setText(mCommUPInfo.getStrInDate());
        if (mCommUPInfo.getNickNamed() == null) {
            myViewHolderComment.reply.setVisibility(View.GONE);
            myViewHolderComment.leftName.setText(mCommUPInfo.getNickName());
            myViewHolderComment.rightName.setVisibility(View.GONE);

        } else {
            myViewHolderComment.rightName.setVisibility(View.VISIBLE);
            myViewHolderComment.reply.setVisibility(View.VISIBLE);
            myViewHolderComment.leftName.setText(mCommUPInfo.getNickName());
            myViewHolderComment.rightName.setText(mCommUPInfo.getNickNamed());
        }

    }

    private void findCommentInfoView(View view,
                                     MyViewHolderComment myViewHolderComment) {

        myViewHolderComment.leftName = (TextView) view
                .findViewById(R.id.item_details_name1);
        myViewHolderComment.rightName = (TextView) view
                .findViewById(R.id.item_details_name2);
        myViewHolderComment.reply = (TextView) view
                .findViewById(R.id.item_details_reply);
        myViewHolderComment.commentTime = (TextView) view
                .findViewById(R.id.item_details_time);
        myViewHolderComment.commentContent = (TextView) view
                .findViewById(R.id.item_details_content);
    }

    private void setData2UserView(final MyViewHolderUser myViewHolderUser) {

        ImageLoaderUtil.displayImage(mUserDetailsInfo.getAvatarUrl(),
                myViewHolderUser.icon, R.mipmap.app_icon);
        myViewHolderUser.name.setText(mUserDetailsInfo.getNickName());
        myViewHolderUser.comment.setText(mUserDetailsInfo.getViewCount() + "");
        myViewHolderUser.assists.setText(mUserDetailsInfo.getEnjoyCount() + "");
        myViewHolderUser.userContent.setText(mUserDetailsInfo.getContent());
        myViewHolderUser.address.setText(mUserDetailsInfo.getPosition());
        myViewHolderUser.userTime.setVisibility(View.VISIBLE);
        myViewHolderUser.userTime.setText(mUserDetailsInfo.getStrInDate());
        myViewHolderUser.zan.setText(getUpInfo(mUserDetailsInfo.getUpList()));
        if (mUserDetailsInfo.getPicUrls() == null || mUserDetailsInfo.getPicUrls().size() <= 0) {
            return;
        }
        if (mUserDetailsInfo.getPicUrls().get(0).isVideo()) {

            myViewHolderUser.videoView.setVisibility(View.VISIBLE);
            myViewHolderUser.imageViews.setVisibility(View.GONE);
            myViewHolderUser.playVideo.setVisibility(View.VISIBLE);
            myViewHolderUser.fl.setVisibility(View.VISIBLE);
            myViewHolderUser.videoBgView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(mUserDetailsInfo.getPicUrls().get(0)
                    .getVideoUrl());
            myViewHolderUser.videoView.setVideoURI(uri);
            ImageLoaderUtil.displayImage(mUserDetailsInfo.getPicUrls().get(0)
                            .getPicUrl(), myViewHolderUser.videoBgView,
                    R.mipmap.app_icon);
            myViewHolderUser.playVideo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    myViewHolderUser.videoView.start();
                    myViewHolderUser.playVideo.setVisibility(View.GONE);
                    myViewHolderUser.videoBgView.setVisibility(View.GONE);

                }
            });
            myViewHolderUser.videoView
                    .setOnCompletionListener(new OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            myViewHolderUser.playVideo
                                    .setVisibility(View.VISIBLE);

                        }
                    });
        } else {
            myViewHolderUser.videoView.setVisibility(View.GONE);
            myViewHolderUser.imageViews.setVisibility(View.VISIBLE);
            myViewHolderUser.playVideo.setVisibility(View.GONE);
            myViewHolderUser.fl.setVisibility(View.GONE);
            myViewHolderUser.videoBgView.setVisibility(View.GONE);
            if (mUserDetailsInfo.getPicUrls() == null && mUserDetailsInfo.getPicUrls().size() <= 0) {
                return;
            }
            myViewHolderUser.imageViews.setAdapter(new GridImage2DAdapter(
                    mContext, mUserDetailsInfo.getPicUrls()));
            myViewHolderUser.imageViews
                    .setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            Intent intent = new Intent(mContext,
                                    SeeImageActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt(SIGN_DETAILS_POSITION, position);
                            bundle.putSerializable(SIGN_DETAILS_IMAGES,
                                    (ArrayList<PictureInfo>) mUserDetailsInfo
                                            .getPicUrls());
                            intent.putExtra(SIGN_DETAILS_BUNDLE, bundle);
                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(
                                    R.anim.enter_scale, R.anim.out_scale);
                        }
                    });
        }
        /*myViewHolderUser.operation.setVisibility(View.VISIBLE);

        mCommentLayout = (LinearLayout) ((Activity) mContext)
                .findViewById(R.id.details_comment);
        mCommentLayout.setVisibility(View.VISIBLE);

        mAssisted = (ImageView) ((Activity) mContext)
                .findViewById(R.id.details_comment_assist);

        for (CommUPInfo mCommUPInfo : mUserDetailsInfo.getUpList()) {
            if (mCommUPInfo.getUserSysNo() == CustomerUtil.getCustomerInfo()
                    .getSysNo()) {
                mAssisted.setImageLevel(R.mipmap.ico_on);
                isAssisted = true;
            }
        }

        mAssisted.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAssisted) {
                    Toast.makeText(mContext, "亲，你已经助攻过", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                putData2Service("", CommentTypeEnum.UP);
            }

        });

        mEditText = (EditText) ((Activity) mContext)
                .findViewById(R.id.details_comment_ed);
        mEditText.setFocusable(false);
        mEditText.setFocusableInTouchMode(false);
        mEditText.requestFocus();
        mEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditText.setFocusable(true);
                mEditText.setFocusableInTouchMode(true);
                mEditText.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
            }
        });
        Button submit = (Button) ((Activity) mContext).findViewById(R.id.details_comment_submit);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String comment = mEditText.getText().toString().trim();
                putData2Service(comment, CommentTypeEnum.COMMENT);
            }
        });*/
    }

    private void putData2Service(final String content,
                                 final CommentTypeEnum mCommentType) {
        CustomerUtil.checkLogin((Activity) mContext, LoginActivity.class,
                new CheckLoginListener() {

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void OnLogined(CustomerInfo customer, Bundle bundle) {
                        submitInfo(content, mCommentType);
                    }

                });

    }

    private void submitInfo(String comment, CommentTypeEnum commentType) {
        final CommentInfo info = new CommentInfo(CustomerUtil.getCustomerInfo()
                .getSysNo(), mUserDetailsInfo.getSysNo(),
                commentType.getCommentType(), comment,
                mUserDetailsInfo.getUserSysNo());
        CBContentResolver mResolver = new CBContentResolver<String>() {

            @Override
            public String query() throws IOException, ServiceException,
                    BizException {

                return new StandService().getCommentInfo(info);
            }

            @Override
            public void onLoaded(String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                InputMethodManager inputManager = (InputMethodManager) mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(mEditText.getApplicationWindowToken(), 0);
                mEditText.setText("");
                mEditText.setFocusable(false);
                notifyDataSetChanged();
            }
        };

		/*ContentStateObserver observer = new ContentStateObserver();
        observer.setView(view, R.id.frg_rostrum_main,
				R.id.frg_rostrum_progress, R.id.error);
		observer.setResolver(mResolver);*/

        mResolver.setVisible(true);
        mResolver.startQuery();
    }

    private String getUpInfo(List<CommUPInfo> list) {
        if (list == null || list.size() <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (CommUPInfo commUPInfo : list) {
            if (commUPInfo.getCommentType() == CommentTypeEnum.UP.getCommentType()) {
                sb.append(commUPInfo.getNickName()).append(",");
            }

        }
        return sb.substring(0, sb.length() - 1);
    }

    private void toService() {

    }

    private void findUserInfoView(View view, MyViewHolderUser myViewHolderUser) {
        myViewHolderUser.icon = (CircleImageView) view.findViewById(R.id.item_rostrum_icon);
        myViewHolderUser.name = (TextView) view.findViewById(R.id.item_rostrum_name);
        myViewHolderUser.imageViews = (NoScrollBarGridView) view.findViewById(R.id.item_rostrum_image_gridview);
        myViewHolderUser.operation = (LinearLayout) view.findViewById(R.id.item_rostrum_operation);
        myViewHolderUser.operation.setVisibility(View.GONE);
        myViewHolderUser.comment = (TextView) view.findViewById(R.id.item_rostrum_comment_num);
        myViewHolderUser.assists = (TextView) view.findViewById(R.id.item_rostrum_assists_num);
        myViewHolderUser.userContent = (TextView) view.findViewById(R.id.item_rostrum_content);
        myViewHolderUser.address = (TextView) view.findViewById(R.id.item_rostrum_address);
        myViewHolderUser.userTime = (TextView) view.findViewById(R.id.item_rostrum_time);
        myViewHolderUser.videoView = (VideoView) view.findViewById(R.id.item_rostrum_vv);
        myViewHolderUser.playVideo = (ImageView) view.findViewById(R.id.item_rostrum_paly);
        myViewHolderUser.fl = (FrameLayout) view.findViewById(R.id.item_rostrum_fl);
        myViewHolderUser.videoBgView = (ImageView) view.findViewById(R.id.item_rostrum_videobg);
        myViewHolderUser.zan = (TextView) view.findViewById(R.id.item_rostrum_operation_zan);

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? USER_INFO : ITERACTIVE_INFO;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class MyViewHolderUser {
        CircleImageView icon;
        LinearLayout operation;
        TextView name;
        TextView comment;
        TextView assists;
        TextView userContent;
        NoScrollBarGridView imageViews;
        TextView address;
        TextView userTime;
        VideoView videoView;
        ImageView playVideo;
        FrameLayout fl;
        ImageView videoBgView;
        TextView zan;

    }

    class MyViewHolderComment {
        TextView leftName;
        TextView rightName;
        TextView reply;
        TextView commentTime;
        TextView commentContent;
    }

}
