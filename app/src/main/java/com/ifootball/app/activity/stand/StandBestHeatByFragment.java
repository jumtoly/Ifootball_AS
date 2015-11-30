package com.ifootball.app.activity.stand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifootball.app.R;
import com.ifootball.app.base.BaseFragment;

public class StandBestHeatByFragment extends BaseFragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.activity_bestheat, null);
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void installView() {

    }
}
