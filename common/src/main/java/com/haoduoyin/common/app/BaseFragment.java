package com.haoduoyin.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/8/6.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView = null;
    protected Unbinder mRootUnbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            int layId = getContentLayoutId();
            mRootView = inflater.inflate(layId,container,false);
            initWidget(mRootView);
        }else {
            if (mRootView.getParent() != null){
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
        }

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initData() {
    }

    /**
     * 初始化控件
     * */
    protected  void initWidget(View mRootView){
        mRootUnbinder = ButterKnife.bind(this,mRootView);
    };

    /**
     * @param arguments 参数bundle
     * */
    protected void initArgs(Bundle arguments) {
    }

    /**
     * @return int 返回碎片内容的id
     * */
    protected abstract int getContentLayoutId();

    /**
     * return
     * */
    public boolean onBackPressed(){
        return false;
    }
}
