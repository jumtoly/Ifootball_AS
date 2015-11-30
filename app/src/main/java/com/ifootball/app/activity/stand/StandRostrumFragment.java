package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ifootball.app.R;
import com.ifootball.app.adapter.stand.Rostrum2DAdapter;
import com.ifootball.app.base.BaseFragment;
import com.ifootball.app.common.StandPageTypeEnum;
import com.ifootball.app.entity.CurrentLocation;
import com.ifootball.app.entity.HasCollection;
import com.ifootball.app.entity.stand.DynamicInfo;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.adapter.MyDecoratedAdapter;
import com.ifootball.app.framework.content.CBCollectionResolver;
import com.ifootball.app.framework.content.CollectionStateObserver;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.stand.StandService;

import java.io.IOException;
import java.util.List;

;

public class StandRostrumFragment extends BaseFragment {
    private static final String DYNAMICINFO_INFO = "DYNAMICINFO_INFO";
    private static final int pageSize = 10;

    private int pageIndex = 0;


    private static final int REFLASH = 1;
    private static final int LOADING = 2;
    private ListView mListView;
    private LinearLayout mErrorView;
    private CBCollectionResolver<StandInfo> mResolver;
    private View view;
    private DynamicInfo mDynamicInfo;
    private CurrentLocation mCurrentLocation;
    private List<StandInfo> mStandInfo;
    private Rostrum2DAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_stand_rostrum, null);
        findView(view);
        isPrepared = true;

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
              /*  Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("SYSNO", mStandInfo.get(position - 1)
                        .getSysNo());
                startActivity(intent);*/

            }
        });
        return view;
    }

    @Override
    protected void getData() {
        if (!isCurrentVisible || !isPrepared) {
            return;
        }

        mResolver = new CBCollectionResolver<StandInfo>() {
            @Override
            public HasCollection<StandInfo> query()
                    throws IOException, ServiceException {

                mDynamicInfo = new StandService().getDynamicInfo(pageIndex, pageSize, StandPageTypeEnum.ROSTRUM.getPageType());

                if (mDynamicInfo != null
                        && mDynamicInfo.getList() != null
                        && mDynamicInfo.getList().size() > 0) {
                    pageIndex = pageIndex + 1;
                }

                return mDynamicInfo;
            }
        };

        mAdapter = new Rostrum2DAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        CollectionStateObserver observer = new CollectionStateObserver();
        mAdapter.setVisible(true);
        observer.setActivity(getActivity());
        observer.setAdapters(mAdapter);
        mAdapter.startQuery(mResolver);
        mListView.setOnScrollListener(new MyDecoratedAdapter.ListScrollListener(mAdapter, mResolver));
    }


    private void findView(View view) {

        mListView = (ListView) view.findViewById(R.id.frg_rostrum_listview);

        mErrorView = (LinearLayout) view.findViewById(R.id.error);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

   /* @Override
    public void onReflash() {

        getDataFSer(REFLASH, 1);


    }*/

   /* @Override
    public void onLoading() {
        if (mStandInfo.size() < mDynamicInfo.getPageInfo().getTotalCount()) {
            getDataFSer(LOADING, mPagerIndex < mDynamicInfo.getPageInfo()
                    .getPageCount() ? (++mPagerIndex) : mPagerIndex);
        } else {
            Toast.makeText(getActivity(), "已经到底了哦！！", Toast.LENGTH_SHORT)
                    .show();
            mListView.loadingComplete();
        }
    }*/

  /*  private void getDataFSer(final int index, int mPagerIndex) {
        RequestParams params = new RequestParams();
        params.put("latitude", Float.parseFloat(MySharedCache.get(
                CommonInface.CURRENT_LATITUDE, "0")));
        params.put("longitude", Float.parseFloat(MySharedCache.get(
                Common.CURRENT_LONGITUDE.name(), "0")));
        params.put("listtype", 0);
        params.put("pageindex", mPagerIndex);
        params.put("pagesize", 10);
        Connect2Service.post(CommonInface.CONTNET_URL
                        + CommonInface.URL_BALLSTATE, params,
                new BaseJsonHttpResponseHandler() {

                    @Override
                    public void onFailure(int arg0, Header[] arg1,
                                          Throwable arg2, String arg3, Object arg4) {
                        System.out.println(arg3);
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, String arg2,
                                          Object arg3) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ResultData<DynamicInfo>>() {
                        }.getType();
                        try {
                            ResultData<DynamicInfo> mInfo = gson.fromJson(arg2,
                                    type);
                            switch (index) {
                                case REFLASH:
                                    mStandInfo = mInfo.getData().getStandInfos();
                                    setupAll();
                                    //								mAdapter.notifyDataSetChanged();
                                    mListView.reflashComplete();
                                    break;

                                case LOADING:
                                    mStandInfo.addAll(mInfo.getData()
                                            .getStandInfos());
                                    mAdapter.notifyDataSetChanged();
                                    mListView.loadingComplete();
                                    break;
                            }

                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }

                    }

                    @Override
                    protected Object parseResponse(String arg0, boolean arg1)
                            throws Throwable {
                        return null;
                    }

                });
    }*/

    @Override
    protected void onInVisible() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void installView() {
        // TODO Auto-generated method stub

    }
}
