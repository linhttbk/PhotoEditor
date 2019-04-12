package com.example.core

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseRecycleViewAdapter<T> : RecyclerView.Adapter<BaseRecycleViewAdapter<T>.BaseViewHolder>() {
    private var mItemClickListener: ItemClickListener? = null
    private var mLoadMoreListener: LoadMoreListener? = null
    private var mViewClickListener: ViewClickListener? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindView(position)
        if (position == itemCount - 1 && mLoadMoreListener != null) {
            mLoadMoreListener?.onLoadMore()
        }
    }

    abstract fun getItem(position: Int): T


    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun setOnLoadMoreListener(loadMoreListener: LoadMoreListener) {
        mLoadMoreListener = loadMoreListener
    } fun setOnViewClickListener(viewClickListener: ViewClickListener) {
        mViewClickListener = viewClickListener
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index: Int = 0

        init {
            itemView.setOnClickListener {
                onViewHolderClick(it)

                mItemClickListener?.onItemClick(itemView, index)

            }
        }


        protected fun onViewHolderClick(v: View) {

        }

        fun bindView(position: Int) {
            index = position
            onBindingData(position)
        }

        protected abstract fun onBindingData(position: Int)
        protected fun onViewClick(resId: Int) {
            mViewClickListener?.onViewItemClock(resId, index)
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

    interface ViewClickListener {
        fun onViewItemClock(resId: Int, position: Int)
    }
}
