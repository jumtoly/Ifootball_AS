package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.widget.ListView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.adapter.stand.Details2Dadapter;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.stand.UserDetailsInfo;
import com.ifootball.app.framework.content.CBContentResolver;
import com.ifootball.app.framework.content.ContentStateObserver;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.stand.StandService;

import java.io.IOException;

public class DetailsActivity extends BaseActivity {

    public static final String SYSNO = "SYSNO";

    private CBContentResolver<UserDetailsInfo> mResolver;


    private ListView mListView;
    private int mSysoNo;
    private UserDetailsInfo mUserDetailsInfo;
    private ContentStateObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        putContentView(R.layout.activity_details, "", true, false);
        TitleBarView view = (TitleBarView) findViewById(R.id.details_titlebar);

        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "动态详情", getResources().getDrawable(R.mipmap.ico_submit));
        view.setActivity(this);


        mSysoNo = getIntent().getIntExtra(SYSNO, 0);

        findView();

        getData();
    }


    private void findView() {
        mListView = (ListView) findViewById(R.id.details_listview);

    }


    protected void getData() {

        mResolver = new CBContentResolver<UserDetailsInfo>() {
            @Override
            public UserDetailsInfo query() throws IOException, ServiceException, BizException {
                return new StandService().getDetailInfo(mSysoNo);
            }

            @Override
            public void onLoaded(UserDetailsInfo result) {
                if (result != null) {
                    mListView.setAdapter(new Details2Dadapter(DetailsActivity.this, result));
                }
            }
        };
        mObserver = new ContentStateObserver();
        mObserver.setView(getWindow().getDecorView().findViewById(android.R.id.content), R.id.details_layout, R.id.loading, R.id.error);
        mObserver.setResolver(mResolver);
        mResolver.setVisible(true);
        mResolver.startQuery();
    }

}
