package com.haoduoyin.common.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoduoyin.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tangwuyang on 2018/8/6.
 */

public abstract class BaseRecycleAdapter<Data>
        extends RecyclerView.Adapter<BaseRecycleAdapter.ViewHolder<Data>>
        implements View.OnClickListener,View.OnLongClickListener,AdapterCallback<Data>{

    private List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     * 自定义监听器
     *
    * */
    public interface AdapterListener<Data>{
        void onItemClick(RecyclerView.ViewHolder holder,Data data);
        void onItemLongClick(RecyclerView.ViewHolder holder,Data data);
    }

    /**
     * 构造函数模块
     */
    public BaseRecycleAdapter() {
        this(null);
    }

    public BaseRecycleAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public BaseRecycleAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }




    /**
     * 复写默认的布局类型返回
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的ID
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }



    /**
     * 得到布局的类型
     * @param position 坐标
     * @param data     当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    protected abstract int getItemViewType(int position,Data data);
    /**
     * 抽象类viewholder
     *
     * */





    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        ViewHolder<Data> holder = onCreateViewHolder(root,viewType);

        //设置view的tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycle_holder,holder);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //进行界面绑定
        holder.unbinder = ButterKnife.bind(holder,root);
        holder.callback = this;
        return holder;
    }


    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，其实就是XML的ID
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root,int viewType);

    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        Data date = mDataList.get(position);
        holder.bind(date);
    }


    /**
     * 得到当前集合的数量
     * */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    /**
     *操作插入一条数据并通知
     * @param data
     * */
    public void add(Data data){
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
    * 插入多个数据
    *@param datas
    * */
    public void add(Data...datas){
        if (null != datas && datas.length > 0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList,datas);
            notifyItemRangeChanged(startPos,datas.length);
        }
    }

    /**
     * 插入多个数据2
     *@param datas
     * */
    public void add(Collection<Data> datas){
        if (null != datas && datas.size() > 0){
            int startPos = mDataList.size();
            mDataList.addAll(startPos,datas);
            notifyItemRangeChanged(startPos,datas.size());
        }
    }

    /**
     * 清空操作
     * */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }


    /**
     * 替换成为一个新的集合 包括清空
     * @param
     * */
    public void replace(Collection<Data> datas){
        mDataList.clear();
        if (null != datas && datas.size() > 0){
            mDataList.addAll(datas);
            notifyDataSetChanged();
        }
    }



    /**
     * 单击事件
     * */
    @Override
    public void onClick(View view) {
        ViewHolder<Data> holder = (ViewHolder<Data>) view.getTag(R.id.tag_recycle_holder);
        if (this.mListener != null){
            //得到当前点击的坐标
            int curPos = holder.getAdapterPosition();
            //接口回调方法
            this.mListener.onItemClick(holder,mDataList.get(curPos));
        }
    }




    /**
     * 长按事件
     * */
    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_recycle_holder);
        if (null != this.mListener){
            int curPos = holder.getAdapterPosition();
            this.mListener.onItemLongClick(holder,mDataList.get(curPos));
            return true;
        }
        return false;
    }



    /**
     * 设置适配器监听
     *
     * */
    public void setListener(AdapterListener<Data> listener){
        this.mListener = listener;
    }


    /**
     * 设置数据源
     * */
    public void setDataList(List<Data> datas){
        this.mDataList = datas;
    }

    /**
     * viewHolder基类
     * 主要提供，传入数据，绑定数据，以及数据更新
     * */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{
        private Data mData;
        private Unbinder unbinder;
        private AdapterCallback callback;
        public ViewHolder(View itemView) {
            super(itemView);
        }


        /**
         * 用于绑定数据的触发
         * @param data 绑定的数据
         */
        void bind(Data data){
            this.mData = data;
            onBind(data);
        }


        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data) ;



        /**
         * holder自己对自己对应的data进行更新
         * @param
         * */
        public void updataData(Data data){
            if (null != this.callback){
                this.callback.update(data,this);
            }
        }



    }

}
