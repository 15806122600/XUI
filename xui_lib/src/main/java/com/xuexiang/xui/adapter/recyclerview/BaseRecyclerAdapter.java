/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xui.adapter.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 基础适配器
 *
 * @author XUE
 * @date 2017/9/10 18:30
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected final List<T> mData = new ArrayList<>();
    private RecyclerViewHolder.OnItemClickListener mClickListener;
    private RecyclerViewHolder.OnItemLongClickListener mLongClickListener;

    /**
     * 当前点击的条目
     */
    private int mSelectPosition = -1;

    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(List<T> list) {
        if (list != null) {
            mData.addAll(list);
        }
    }

    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    abstract public int getItemLayoutId(int viewType);

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(viewType), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, position, mData.get(position));
    }

    public T getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public BaseRecyclerAdapter add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
        return this;
    }

    public BaseRecyclerAdapter delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
        return this;
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> collection) {
        if (collection != null) {
            mData.clear();
            mData.addAll(collection);
            notifyDataSetChanged();
            mSelectPosition = -1;
        }
        return this;
    }

    public BaseRecyclerAdapter<T> loadMore(Collection<T> collection) {
        if (collection != null) {
            mData.addAll(collection);
            notifyDataSetChanged();
        }
        return this;
    }

    public BaseRecyclerAdapter<T> load(T item) {
        if (item != null) {
            mData.add(item);
            notifyDataSetChanged();
        }
        return this;
    }

    public BaseRecyclerAdapter setOnItemClickListener(RecyclerViewHolder.OnItemClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public BaseRecyclerAdapter setOnItemLongClickListener(RecyclerViewHolder.OnItemLongClickListener listener) {
        mLongClickListener = listener;
        return this;
    }

    /**
     * @return 当前列表的选中项
     */
    public int getSelectPosition() {
        return mSelectPosition;
    }

    /**
     * 设置当前列表的选中项
     *
     * @param selectPosition
     * @return
     */
    public BaseRecyclerAdapter<T> setSelectPosition(int selectPosition) {
        mSelectPosition = selectPosition;
        notifyDataSetChanged();
        return this;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     * @param item
     */
    abstract public void bindData(RecyclerViewHolder holder, int position, T item);


}
