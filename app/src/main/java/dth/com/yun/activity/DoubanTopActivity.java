package dth.com.yun.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.DouBanTopAdapter;
import dth.com.yun.base.BaseActivity;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.presenter.IDoubanTopPresenter;
import dth.com.yun.presenter.viewImpl.IDoubanTopView;

import static android.R.attr.data;

/**
 * Created by dth
 * Des:
 : Date: 2017/2/20.
 */

public class DoubanTopActivity extends BaseActivity<IDoubanTopPresenter> implements IDoubanTopView {

    @Bind(R.id.xrv_top)
    XRecyclerView mXrvTop;

    private int mStart = 0;
    private int mCount = 15;
    private DouBanTopAdapter mDouBanTopAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTool_bar.setVisibility(View.VISIBLE);
        mTool_bar.setTitle("豆瓣电影Top250");

        initRecyclerView();
        mPresenter.getDouBanTop250(mStart,mCount);
    }

    private void initRecyclerView() {
        mDouBanTopAdapter = new DouBanTopAdapter(this);
        mXrvTop.setLayoutManager(new GridLayoutManager(this,3));

        mXrvTop.setAdapter(mDouBanTopAdapter);

        mXrvTop.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mDouBanTopAdapter.clear();
                mStart = 0;
                mPresenter.getDouBanTop250(mStart,mCount);
            }

            @Override
            public void onLoadMore() {
                if (mStart >= 250) {
                    mXrvTop.setLoadingMoreEnabled(false);
//                    mXrvTop.noMoreLoading();
                    return;
                }
                mStart += mCount;
                mPresenter.getDouBanTop250(mStart,mCount);
            }
        });

        mDouBanTopAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<HotMovieBean.SubjectsEntity>() {
            @Override
            public void onClick(BaseRecyclerViewHolder holder, HotMovieBean.SubjectsEntity subjectsEntity, int position) {
                OneMovieDetailActivity.start(DoubanTopActivity.this, subjectsEntity, (ImageView) holder.itemView.findViewById(R.id.iv_top_photo));
            }
        });

        mDouBanTopAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<HotMovieBean.SubjectsEntity>() {
            @Override
            public void onLongClick(final BaseRecyclerViewHolder holder, final HotMovieBean.SubjectsEntity subjectsEntity, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoubanTopActivity.this);
                View view = View.inflate(DoubanTopActivity.this, R.layout.title_douban_top, null);
                TextView titleTop = (TextView) view.findViewById(R.id.title_top);
                titleTop.setText("Top" + (position + 1) + ": " + subjectsEntity.getTitle());
                builder.setCustomTitle(view);
                builder.setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OneMovieDetailActivity.start(DoubanTopActivity.this, subjectsEntity, (ImageView) holder.itemView.findViewById(R.id.iv_top_photo));
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_douban_top;
    }

    @Override
    protected IDoubanTopPresenter getPresenter() {
        return new IDoubanTopPresenter();
    }

    @Override
    public void loadSuccess(List<HotMovieBean.SubjectsEntity> subjects) {
        showContentView();
        mXrvTop.refreshComplete();
        mDouBanTopAdapter.addAll(subjects);
    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    protected void onRefresh() {
        mDouBanTopAdapter.clear();
        mStart = 0;
        mPresenter.getDouBanTop250(mStart,mCount);
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, DoubanTopActivity.class);
        mContext.startActivity(intent);
    }

}
