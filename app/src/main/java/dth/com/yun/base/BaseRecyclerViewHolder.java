package dth.com.yun.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {


    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public BaseRecyclerViewHolder(ViewGroup viewGroup , int layoutId) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));
        ButterKnife.bind(this,itemView);
    }



    /**
     * @param data   the data of bind
     * @param position the item position of recyclerView
     */
    public abstract void onBindViewHolder(T data, final int position);

    public void onBaseBindViewHolder(T data, final int position) {
        onBindViewHolder(data, position);
    }
}
