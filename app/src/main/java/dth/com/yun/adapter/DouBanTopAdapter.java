package dth.com.yun.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.utils.ImgLoadUtil;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public class DouBanTopAdapter extends BaseRecyclerViewAdapter<HotMovieBean.SubjectsEntity> {

    private  Activity mContext;

    public DouBanTopAdapter(Activity context) {
        mContext = context;
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DouBanTopHolder(parent, R.layout.item_douban_top);
    }

    class DouBanTopHolder extends BaseRecyclerViewHolder<HotMovieBean.SubjectsEntity> {

        @Bind(R.id.iv_top_photo)
        ImageView    mIvTopPhoto;
        @Bind(R.id.tv_name)
        TextView     mTvName;
        @Bind(R.id.tv_rate)
        TextView     mTvRate;
        @Bind(R.id.ll_item_top)
        LinearLayout mLlItemTop;

        public DouBanTopHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final HotMovieBean.SubjectsEntity data, final int position) {


            ImgLoadUtil.showMovieImg(mIvTopPhoto,data.getImages().getLarge());
            mTvName.setText(data.getTitle());
            mTvRate.setText("评分: "+data.getRating().getAverage());

            /*mLlItemTop.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    OneMovieDetailActivity.start(mContext, data, mIvTopPhoto);
                }
            });

            mLlItemTop.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
                    TextView titleTop = (TextView) view.findViewById(R.id.title_top);
                    titleTop.setText("Top" + (position + 1) + ": " + data.getTitle());
                    builder.setCustomTitle(view);
                    builder.setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OneMovieDetailActivity.start(mContext, data, mIvTopPhoto);
                        }
                    });
                    builder.show();
                    return false;
                }
            });*/
        }
    }

}
