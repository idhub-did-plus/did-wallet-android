package com.idhub.wallet.common.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    protected View header;
    protected View footer;
    private int headerCount = 0;
    private int footerCount = 0;

    protected List<T> mDataList = new ArrayList<>();
    protected Context context;
    private int lastPosition = -1;
    protected LayoutInflater mInflater;

    private boolean isScrolling;//是否在滚动
    private RecyclerView recyclerView;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public void addAll(List<T> data) {
        this.mDataList.clear();
        this.mDataList.addAll(data);
        this.notifyDataSetChanged();
    }

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void addHeaderView(View header) {
        this.header = header;
        headerCount = 1;
    }

    public int getHeaderCount() {
        return headerCount;
    }

    public void addFooterView(View footer) {
        this.footer = footer;
        footerCount = 1;
    }

    public void setItems(List<T> data) {
        this.mDataList = data;
    }

    public List<T> getmDataList() {
        return mDataList;
    }

    public T getItemObject(int pos){
        return mDataList.get(pos);
    }

    public boolean isHeader(int position) {
        if (headerCount == 0) {
            return false;
        }
        return position == 0;
    }

    public boolean isFooter(int position) {
        if (footerCount == 0) {
            return false;
        }
        return position == mDataList.size() + headerCount;
    }


    @Override
    public int getItemCount() {
        return mDataList == null ? headerCount + footerCount : mDataList.size() + headerCount + footerCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (isFooter(position)) {

            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }
        if (isFooter(position)) {
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    mClickListener.onItemClick(view, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mLongClickListener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    mLongClickListener.onItemLongClick(view, position);
                    return true;
                }
                return false;
            }
        });

        final T object = mDataList.get(position - headerCount);
        bindViewWithHolder(position, holder, object);
//        setAnimation(holder.lie_Root, position);
    }

    protected abstract void bindViewWithHolder(int position, RecyclerViewHolder holder, T item);


    public void addData(T data) {
        this.mDataList.add(data);
    }

    public void add(int pos, T item) {
        mDataList.add(pos, item);
        notifyItemInserted(pos);
    }

    public void changeValue(int pos,T item){
        mDataList.set(pos, item);
        notifyItemChanged(pos);
    }


    public void delete(int pos) {
        mDataList.remove(pos);
        notifyItemRemoved(pos);
        if (pos != mDataList.size()) {
            notifyItemRangeChanged(pos, mDataList.size() - pos);
        }
    }

    public void removeAll() {
        mDataList.clear();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        holder.lie_Root.clearAnimation();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new RecyclerViewHolder(context, header);
        }
        if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return new RecyclerViewHolder(context, footer);
        }
        View view = mInflater.inflate(getItemLayoutId(viewType), parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context, view);
        return holder;
    }

    abstract public int getItemLayoutId(int layoutId);

    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }

    private void setAnimation(View viewToAnimate, int position, int animResID) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(context, animResID);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
