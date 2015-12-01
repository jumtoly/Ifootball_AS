package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ifootball.app.R;
import com.ifootball.app.adapter.stand.StandPage2DAdapter;
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

public class StandBestHeatFragment extends Fragment {
    private static final int pageSize = 10;

    private int pageIndex = 0;


    private ListView mListView;
    private CBCollectionResolver<StandInfo> mResolver;
    private View view;
    private DynamicInfo mDynamicInfo;
    private StandPage2DAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_stand_fragment_content, null);
        findView(view);
        getData();

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

    protected void getData() {


        mResolver = new CBCollectionResolver<StandInfo>() {
            @Override
            public HasCollection<StandInfo> query()
                    throws IOException, ServiceException {

                mDynamicInfo = new StandService().getDynamicInfo(pageIndex, pageSize, StandPageTypeEnum.BESTHEAT.getPageType());

                if (mDynamicInfo != null
                        && mDynamicInfo.getList() != null
                        && mDynamicInfo.getList().size() > 0) {
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

    @Override
    public void onPause() {
        super.onPause();
        pageIndex = 0;
        Log.i("TEST","BESTHEAT");
    }


}
