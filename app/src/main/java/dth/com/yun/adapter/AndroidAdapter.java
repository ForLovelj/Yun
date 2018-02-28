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
import dth.com.yun.model.GankIoDataBean;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/15.
 */

public class AndroidAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultsEntity> {

    private boolean isAll = false;

    public void setAll(boolean b) {
        isAll = b;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AndroidHolder(parent, R.layout.item_android);
    }

    class AndroidHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultsEntity> {

        @Bind(R.id.iv_all_welfare)
        ImageView    mIvAllWelfare;
        @Bind(R.id.tv_android_des)
        TextView     mTvAndroidDes;
        @Bind(R.id.iv_android_pic)
        ImageView        mIvAndroidPic;
        @Bind(R.id.ll_welfare_other)
        LinearLayout mLlWelfareOther;
        @Bind(R.id.tv_android_who)
        TextView         mTvAndroidWho;
        @Bind(R.id.tv_content_type)
        TextView         mTvContentType;
        @Bind(R.id.tv_android_time)
        TextView         mTvAndroidTime;
        @Bind(R.id.ll_all)
        LinearLayout     mLlAll;

        public AndroidHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultsEntity object, int position) {

            if (isAll && "福利".equals(object.getType())) {
                mIvAllWelfare.setVisibility(View.VISIBLE);
                mLlWelfareOther.setVisibility(View.GONE);
                ImgLoadUtil.displayEspImage(object.getUrl(), mIvAllWelfare, 1);
            } else {
                mIvAllWelfare.setVisibility(View.GONE);
                mLlWelfareOther.setVisibility(View.VISIBLE);
            }

            if (isAll) {
                mTvContentType.setVisibility(View.VISIBLE);
                mTvContentType.setText(" · " + object.getType());
            } else {
                mTvContentType.setVisibility(View.GONE);

            }

            mTvAndroidDes.setText(object.getDesc());
            mTvAndroidWho.setText(object.getWho());
            mTvAndroidTime.setText(object.getPublishedAt());

            // 显示gif图片会很耗内存
            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
                //                binding.ivAndroidPic.setVisibility(View.GONE);
                mIvAndroidPic.setVisibility(View.VISIBLE);
                ImgLoadUtil.displayGif(object.getImages().get(0), mIvAndroidPic);
            } else {
                mIvAndroidPic.setVisibility(View.GONE);
            }

            mLlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.loadUrl(v.getContext(), object.getUrl(), "加载中...");
                }
            });
        }


    }
}
