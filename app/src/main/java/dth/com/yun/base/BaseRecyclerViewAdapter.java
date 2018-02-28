package dth.com.yun.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dth.com.yun.utils.PerfectClickListener;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


    protected List<T> data = new ArrayList<>();
    protected OnItemClickListener<T> listener;
    protected OnItemLongClickListener<T> onItemLongClickListener;

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, final int position) {
        holder.onBaseBindViewHolder(data.get(position), position);

        holder.itemView.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.onClick(holder,data.get(position), position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(holder,data.get(position), position);
                }
                return false;
            }
        });



        //        holder.itemView.setOnClickListener(new PerfectClickListener() {
        //            @Override
        //            protected void onNoDoubleClick(View v) {
        //                if (listener!=null){
        //                    listener.onClick(data.get(position), position);
        //                }
        //            }
        //        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void add(T object) {
        data.add(object);
    }

    public void clear() {
        data.clear();
    }

    public void remove(T object) {
        data.remove(object);
    }
    public void remove(int position) {
        data.remove(position);
    }
    public void removeAll(List<T> data) {
        this.data.retainAll(data);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }


    public List<T> getData() {
        return data;
    }
    public void setData(List<T>  list) {
        data = list;
        notifyDataSetChanged();
    }


    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        public void onClick(BaseRecyclerViewHolder holder, T t, int position);
    }

    public interface OnItemLongClickListener<T> {
        public void onLongClick(BaseRecyclerViewHolder holder, T t, int position);
    }



}
