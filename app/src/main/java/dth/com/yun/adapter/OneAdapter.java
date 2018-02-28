package dth.com.yun.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.StringFormatUtil;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/16.
 */

public class OneAdapter extends BaseRecyclerViewAdapter<HotMovieBean.SubjectsEntity> {


    private  Activity mContext;

    public OneAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneHolder(parent, R.layout.item_one);
    }


    class OneHolder extends BaseRecyclerViewHolder<HotMovieBean.SubjectsEntity> {

        @Bind(R.id.iv_one_photo)
        ImageView    mIvOnePhoto;
        @Bind(R.id.tv_one_title)
        TextView     mTvOneTitle;
        @Bind(R.id.tv_one_directors)
        TextView         mTvOneDirectors;
        @Bind(R.id.tv_one_casts)
        TextView         mTvOneCasts;
        @Bind(R.id.tv_one_genres)
        TextView         mTvOneGenres;
        @Bind(R.id.tv_one_rating_rate)
        TextView         mTvOneRatingRate;
        @Bind(R.id.view_color)
        View         mViewColor;
        @Bind(R.id.ll_one_item)
        LinearLayout mLlOneItem;

        public OneHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final HotMovieBean.SubjectsEntity object, int position) {

            //电影名
            mTvOneTitle.setText(object.getTitle());
            // 图片
            ImgLoadUtil.displayEspImage(object.getImages().getLarge(), mIvOnePhoto,0);
            // 导演
            mTvOneDirectors.setText(object.getDirectors().get(0).getName());
            // 主演
            mTvOneCasts.setText(StringFormatUtil.formatName(object.getCasts()));
            // 类型
            mTvOneGenres.setText("类型：" + StringFormatUtil.formatGenres(object.getGenres()));
            // 评分
            mTvOneRatingRate.setText("评分：" + String.valueOf(object.getRating().getAverage()));
            // 分割线颜色
            mViewColor.setBackgroundColor(CommonUtils.randomColor());

            //item 加载动画
            ViewHelper.setScaleX(itemView,0.8f);
            ViewHelper.setScaleY(itemView,0.8f);
            ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();


            /*mLlOneItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    OneMovieDetailActivity.start(mContext,object,mIvOnePhoto);
                }
            });*/
        }
    }
}
