package com.ifootball.app.baseapp;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {


    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */

    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    /*

    private int index;
    *//**
     * 界面是否是当前可见
     *//*
    protected boolean isCurrentVisible = false;
    *//**
     * 是否加载完界面
     *//*
    protected boolean isPrepared = false;

    private boolean isLoadConent = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isCurrentVisible = true;
            onVisible();
        } else {
            isCurrentVisible = false;
            onInVisible();
        }
    }

    *//**
     * Fragment不可见
     *//*
    protected abstract void onInVisible();

    *//**
     * Fragment可见
     *//*
    private void onVisible() {

        getData();
    }

    *//**
     * 加载数据
     *//*
    protected abstract void getData();

    *//**
     * put数据到布局
     *//*
    protected abstract void installView();

    protected abstract void lazyLoad();*/

}
