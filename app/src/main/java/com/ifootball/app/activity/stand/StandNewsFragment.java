package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ifootball.app.R;
import com.ifootball.app.adapter.stand.StandPage2DAdapter;
import com.ifootball.app.baseapp.BaseFragment;
import com.ifootball.app.common.StandPageTypeEnum;
import com.ifootball.app.entity.HasCollection;
import com.ifootball.app.entity.stand.DynamicInfo;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.adapter.MyDecoratedAdapter;
import com.ifootball.app.framework.content.CBCollectionResolver;
import com.ifootball.app.framework.content.CollectionStateObserver;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.stand.StandService;

import java.io.IOException;

public class StandNewsFragment extends BaseFragment {
    private static final int pageSize = 10;

    private int pageIndex = 0;


    private ListView mListView;
    private CBCollectionResolver<StandInfo> mResolver;
    private View view;
    private DynamicInfo mDynamicInfo;
    private StandPage2DAdapter mAdapter;

    private int mCurIndex = -1;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.activity_stand_fragment_content, null);
            findView(view);
            isPrepared = true;
            lazyLoad();
        }


        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(DetailsActivity.SYSNO, ((StandInfo) mListView.getAdapter().getItem(position)).getSysNo());
                IntentUtil.redirectToNextActivity(getActivity(), DetailsActivity.class, bundle);


            }
        });
        return view;
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        mResolver = new CBCollectionResolver<StandInfo>() {
            @Override
            public HasCollection<StandInfo> query()
                    throws IOException, ServiceException {

                mDynamicInfo = new StandService().getDynamicInfo(pageIndex, pageSize, StandPageTypeEnum.NEWS.getPageType());

                if (mDynamicInfo != null
                        && mDynamicInfo.getList() != null
                        && mDynamicInfo.getList().size() > 0) {
                    mHasLoadedOnce = true;
                    pageIndex = pageIndex + 1;
                }

                return mDynamicInfo;
            }
        };

        mAdapter = new StandPage2DAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        CollectionStateObserver observer = new CollectionStateObserver();
        mAdapter.setVisible(true);
        observer.setFragmentView(view, this);
        observer.setAdapters(mAdapter);
        mAdapter.startQuery(mResolver);
        mListView.setOnScrollListener(new MyDecoratedAdapter.ListScrollListener(mAdapter, mResolver));


    }


    private void findView(View view) {

        mListView = (ListView) view.findViewById(R.id.frg_rostrum_listview);


    }


}
