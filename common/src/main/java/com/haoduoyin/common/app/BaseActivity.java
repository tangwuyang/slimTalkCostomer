package com.haoduoyin.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by tangwuyang on 2018/8/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //初始化窗口
    protected void initWidows(){}

    /**
     * 初始化相关参数
     * @param bundle
     * @return boolean 如果参数正确返回true 否者返回false
     * 可以由子类重写
     * */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 得到当前页面的资源文件id
     * @return 当前页面的布局文件id
     * */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * */
    protected void initWidget(){
        ButterKnife.bind(this);
    }



    /**
     * 初始化数据
     */
    protected void initData() {

    }


    /**
     * 点击界面导航返回时 finish当前页面
     * */
    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (null != fragments && fragments.size() >0){
            for (Fragment fragment :
                    fragments) {
                if (fragment instanceof BaseFragment){
                    if (((BaseFragment)fragment).onBackPressed()){
                        return;
                    };
                }
            }
        }

        finish();
    }
}
