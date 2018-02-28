package dth.com.yun.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.MovieDetailBean;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/17.
 */

public class OneMovieDetailAdapter extends BaseRecyclerViewAdapter<MovieDetailBean.PersonEntity> {


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneMovieDetailHolder(parent, R.layout.item_movie_detail_person);
    }

    class OneMovieDetailHolder extends BaseRecyclerViewHolder<MovieDetailBean.PersonEntity> {

        @Bind(R.id.iv_avatar)
        ImageView    mIvAvatar;
        @Bind(R.id.ll_item)
        LinearLayout mLlItem;
        @Bind(R.id.tv_name)
        TextView     mTvName;
        @Bind(R.id.tv_type)
        TextView     mTvType;

        public OneMovieDetailHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final MovieDetailBean.PersonEntity object, int position) {
            if (object != null) {

                ImgLoadUtil.showImg(mIvAvatar, object.getAvatars().getLarge());
                mTvName.setText(object.getName());
                mTvType.setText(object.getType());

                mLlItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (!TextUtils.isEmpty(object.getAlt())) {
                            WebViewActivity.loadUrl(v.getContext(), object.getAlt(), object.getName());
                        }
                    }
                });
            }
        }
    }

}
