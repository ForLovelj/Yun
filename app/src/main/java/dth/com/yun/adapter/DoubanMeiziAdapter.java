package dth.com.yun.adapter;

import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.DoubanMeizi;

/**
 * Created by dth
 * Des:
 * Date: 2017/2/27.
 */

public class DoubanMeiziAdapter extends BaseRecyclerViewAdapter<DoubanMeizi> {


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubanMeiziHolder(parent, R.layout.item_douban_meizhi);
    }

    public class DoubanMeiziHolder extends BaseRecyclerViewHolder<DoubanMeizi> {

        @Bind(R.id.iv_meizhi)
        ImageView mIvMeizhi;
        @Bind(R.id.tv_title)
        TextView  mTvTitle;

        public DoubanMeiziHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final DoubanMeizi data, final int position) {


//            ImgLoadUtil.displayFadeImage(mIvMeizhi,data.getUrl(),1);
            Glide.with(mIvMeizhi.getContext())
                    .load(data.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade(500)
//                    .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                    .placeholder(R.drawable.img_default_meizi)
                    .error(R.drawable.img_default_meizi)
                    .into(mIvMeizhi);
            mTvTitle.setText(data.getTitle());

            mIvMeizhi.setTag(R.string.app_name, data.getUrl());//如果key不唯一会抛出java.lang.IllegalArgumentException: You must not call setTag() on a view Glide is targeting
            ViewCompat.setTransitionName(mIvMeizhi, data.getUrl());
        }
    }

}
