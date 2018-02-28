package dth.com.yun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.GankIoDataBean;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/14.
 */

public class WelfareAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultsEntity> {

    private  Context mContext;

    public WelfareAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welfare, parent, false);
        return new WelfareHolder(view);
    }


    public class WelfareHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultsEntity> {

        private ImageView mIv_welfare;

        public WelfareHolder(View itemView) {
            super(itemView);
            mIv_welfare = (ImageView) itemView.findViewById(R.id.iv_welfare);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultsEntity object, final int position) {
            /**
             * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
             * 如果这样使用，则每个item的左右边距是不一样的，
             * 这样item不能复用，所以下拉刷新成功后显示会闪一下
             * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
             */
//            if (position % 2 == 0) {
//                DensityUtil.setViewMargin(itemView, false, 12, 6, 12, 0);
//            } else {
//                DensityUtil.setViewMargin(itemView, false, 6, 12, 12, 0);
//            }

//            ImgLoadUtil.displayEspImage(object.getUrl(), mIv_welfare, 1);
            /**
             * 当item根布局为LinearLayout 不是 CardView 时ImageView设置了固定 宽高，加载图片滑动时出现空白  这儿是个超级大坑
             * 解决方法   将CardView设置成根布局 或者 imageview宽高设置成warp_content
             */

            Glide.with(mContext)
                    .load(object.getUrl())
                    .crossFade(1500)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.img_default_meizi)
                    .error(R.drawable.img_default_meizi)
                    .into(mIv_welfare);


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(WelfareHolder.this,object, position);
                    }
                }
            });*/
        }
    }
}

