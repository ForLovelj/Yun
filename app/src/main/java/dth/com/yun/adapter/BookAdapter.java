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
import dth.com.yun.model.BookBean;
import dth.com.yun.utils.ImgLoadUtil;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public class BookAdapter extends BaseRecyclerViewAdapter<BookBean.BooksEntity> {

    private  Activity mContext;

    public BookAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookHolder(parent, R.layout.item_book);
    }

    class BookHolder extends BaseRecyclerViewHolder<BookBean.BooksEntity> {

        @Bind(R.id.iv_top_photo)
        ImageView    mIvTopPhoto;
        @Bind(R.id.tv_name)
        TextView     mTvName;
        @Bind(R.id.tv_rate)
        TextView     mTvRate;
        @Bind(R.id.ll_item_top)
        LinearLayout mLlItemTop;

        public BookHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final BookBean.BooksEntity data, int position) {

            ImgLoadUtil.showBookImg(mIvTopPhoto,data.getImages().getLarge());
            mTvName.setText(data.getTitle());
            mTvRate.setText("评分: "+data.getRating().getAverage());

            /*mLlItemTop.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    BookDetailActivity.start(mContext,data,mIvTopPhoto);
                }
            });*/
        }
    }

}
