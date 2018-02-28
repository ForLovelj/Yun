package dth.com.yun.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.model.AndroidBean;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class EverydayAdapter extends BaseRecyclerViewAdapter<List<AndroidBean>> {

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图
    private static final int TYPE_THREE = 4;// 三张图

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_title, parent, false);
                return new TitleHolder(view);
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_one, parent, false);
                return new OneHolder(view);

            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_two, parent, false);
                return new TwoHolder(view);

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_three, parent, false);
                return new ThreeHolder(view);

        }
    }



    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(getData().get(position).get(0).getType_title())) {
            return TYPE_TITLE;
        } else if (getData().get(position).size() == 1) {
            return TYPE_ONE;
        } else if (getData().get(position).size() == 2) {
            return TYPE_TWO;
        } else if (getData().get(position).size() == 3) {
            return TYPE_THREE;
        }
        return super.getItemViewType(position);
    }


    class OneHolder extends BaseRecyclerViewHolder<List<AndroidBean>> {

        private  ImageView iv_one_photo;
        private TextView tv_one_photo_title;

        public OneHolder(View itemView) {
            super(itemView);
            tv_one_photo_title = (TextView) itemView.findViewById(R.id.tv_one_photo_title);
            iv_one_photo = (ImageView) itemView.findViewById(R.id.iv_one_photo);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            AndroidBean androidBean = object.get(0);
            if (androidBean != null) {
                tv_one_photo_title.setText(androidBean.getDesc());
                if ("福利".equals(object.get(0).getType())) {
                    tv_one_photo_title.setVisibility(View.GONE);
                    iv_one_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(iv_one_photo.getContext())
                            .load(object.get(0).getUrl())
                            .crossFade(1500)
                            .placeholder(R.drawable.img_two_bi_one)
                            .error(R.drawable.img_two_bi_one)
                            .into(iv_one_photo);

                } else {
                    tv_one_photo_title.setVisibility(View.VISIBLE);
                    ImgLoadUtil.displayRandom(1,androidBean.getImage_url(),iv_one_photo);
                }
                setOnClick(iv_one_photo, object.get(0));
            }
        }

    }

    class TitleHolder extends BaseRecyclerViewHolder<List<AndroidBean>> {

        private ImageView iv_title_type;
        private TextView tv_title_type;
        private TextView tv_more;

        public TitleHolder(View itemView) {
            super(itemView);
            iv_title_type = (ImageView) itemView.findViewById(R.id.iv_title_type);
            tv_title_type = (TextView) itemView.findViewById(R.id.tv_title_type);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            int index = 0;
            AndroidBean androidBean = object.get(0);
            
            if (androidBean != null) {
                String title = androidBean.getType_title();
                tv_title_type.setText(title);

                if ("Android".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_android));
                    index = 0;
                } else if ("福利".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_meizi));
                    index = 1;
                } else if ("IOS".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_ios));
                    index = 2;
                } else if ("休息视频".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_movie));
                    index = 2;
                } else if ("拓展资源".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_source));
                    index = 2;
                } else if ("瞎推荐".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_xia));
                    index = 2;
                } else if ("前端".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_qian));
                    index = 2;
                } else if ("App".equals(title)) {
                    iv_title_type.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_app));
                    index = 2;
                }
            }

            final int finalIndex = index;
            tv_more.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, finalIndex);
                }
            });


        }
    }

    class TwoHolder extends BaseRecyclerViewHolder<List<AndroidBean>> {

        private ImageView iv_two_one_one;
        private ImageView iv_two_one_two;
        private TextView tv_two_one_one_title;
        private TextView tv_two_one_two_title;

        public TwoHolder(View itemView) {
            super(itemView);
            iv_two_one_one = (ImageView) itemView.findViewById(R.id.iv_two_one_one);
            iv_two_one_two = (ImageView) itemView.findViewById(R.id.iv_two_one_two);
            tv_two_one_one_title = (TextView) itemView.findViewById(R.id.tv_two_one_one_title);
            tv_two_one_two_title = (TextView) itemView.findViewById(R.id.tv_two_one_two_title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            ImgLoadUtil.displayRandom(2,object.get(0).getImage_url(),iv_two_one_one);
            ImgLoadUtil.displayRandom(2,object.get(1).getImage_url(),iv_two_one_two);
            tv_two_one_one_title.setText(object.get(0).getDesc());
            tv_two_one_two_title.setText(object.get(1).getDesc());

            setOnClick(iv_two_one_one, object.get(0));
            setOnClick(iv_two_one_two, object.get(1));
        }
    }

    class ThreeHolder extends BaseRecyclerViewHolder<List<AndroidBean>>{

        private ImageView iv_three_one_one;
        private ImageView iv_three_one_three;
        private ImageView iv_three_one_two;
        private TextView tv_three_one_one_title;
        private TextView tv_three_one_two_title;
        private TextView tv_three_one_three_title;

        public ThreeHolder(View itemView) {
            super(itemView);
            iv_three_one_one = (ImageView) itemView.findViewById(R.id.iv_three_one_one);
            iv_three_one_two = (ImageView) itemView.findViewById(R.id.iv_three_one_two);
            iv_three_one_three = (ImageView) itemView.findViewById(R.id.iv_three_one_three);
            tv_three_one_one_title = (TextView) itemView.findViewById(R.id.tv_three_one_one_title);
            tv_three_one_two_title = (TextView) itemView.findViewById(R.id.tv_three_one_two_title);
            tv_three_one_three_title = (TextView) itemView.findViewById(R.id.tv_three_one_three_title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            ImgLoadUtil.displayRandom(3,object.get(0).getImage_url(),iv_three_one_one);
            ImgLoadUtil.displayRandom(3,object.get(1).getImage_url(),iv_three_one_two);
            ImgLoadUtil.displayRandom(3,object.get(2).getImage_url(),iv_three_one_three);
            tv_three_one_one_title.setText(object.get(0).getDesc());
            tv_three_one_two_title.setText(object.get(1).getDesc());
            tv_three_one_three_title.setText(object.get(2).getDesc());
            setOnClick(iv_three_one_one, object.get(0));
            setOnClick(iv_three_one_two, object.get(1));
            setOnClick(iv_three_one_three, object.get(2));
        }
    }

    /**
     * 点击事件  跳转webView
     * @param imageView
     * @param bean
     */
    private void setOnClick(final ImageView imageView, final AndroidBean bean) {
        imageView.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), bean.getUrl(), "加载中...");
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
                TextView titleTop = (TextView) view.findViewById(R.id.title_top);
                titleTop.setTextSize(14);
                String title = TextUtils.isEmpty(bean.getType()) ? bean.getDesc() : bean.getType() + "：  " + bean.getDesc();
                titleTop.setText(title);
                builder.setCustomTitle(view);
                builder.setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebViewActivity.loadUrl(imageView.getContext(), bean.getUrl(), "加载中...");
                    }
                });
                builder.show();
                return false;
            }
        });

    }

}
