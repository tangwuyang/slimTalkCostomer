package com.haoduoyin.common.widget;

/**
 * Created by Administrator on 2018/8/6.
 */

public interface AdapterCallback<Data> {
    void update(Data data, BaseRecycleAdapter.ViewHolder<Data> holder);
}
