package dth.com.yun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import dth.com.yun.ConstantsImageUrl;
import dth.com.yun.R;
import dth.com.yun.activity.DoubanTopActivity;
import dth.com.yun.activity.OneMovieDetailActivity;
import dth.com.yun.adapter.OneAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.presenter.IOneFragmentPresenter;
import dth.com.yun.presenter.viewImpl.IOneFragmentView;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.PerfectClickListener;

/**
 * Created by dth.
 * Des:电影热映榜
 * Date: 2017/2/9.
 */

public class OneFragment extends BaseFragment<IOneFragmentPresenter> implements IOneFragmentView {

    @Bind(R.id.list_one)
    XRecyclerView mListOne;
    @Bind(R.id.frameLayout)
    FrameLayout   mFrameLayout;
    private OneAdapter mOneAdapter;

    private boolean mIsPrepared =false;
    private boolean mIsFirst = true;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected IOneFragmentPresenter getPresenter() {
        return new IOneFragmentPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();

        mIsPrepared = true;
    }

    private void initRecyclerView() {
        View headerView = View.inflate(getContext(), R.layout.header_item_one, null);
        ImageView ivImg = (ImageView) headerView.findViewById(R.id.iv_img);
        ImgLoadUtil.displayRandom(3, ConstantsImageUrl.ONE_URL_01,ivImg);

        headerView.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                DoubanTopActivity.start(getContext());
            }
        });

        mOneAdapter = new OneAdapter(getActivity());
        mListOne.setLayoutManager(new LinearLayoutManager(getContext()));
        // 加上这两行代码，禁止下拉刷新头
        mListOne.setPullRefreshEnabled(false);
        mListOne.clearHeader();

        //禁止加载更多
        mListOne.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        mListOne.setNestedScrollingEnabled(false);
        mListOne.setHasFixedSize(false);

        mListOne.addHeaderView(headerView);
        mListOne.setAdapter(mOneAdapter);

        mOneAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<HotMovieBean.SubjectsEntity>() {
            @Override
            public void onClick(BaseRecyclerViewHolder holder, HotMovieBean.SubjectsEntity subjectsEntity, int position) {
                OneMovieDetailActivity.start(getActivity(),subjectsEntity, (ImageView) holder.itemView.findViewById(R.id.iv_one_photo));
            }
        });

    }

    @Override
    protected void loadData() {

        if (!mIsVisible || !mIsPrepared || !mIsFirst) {
            return;
        }

        mPresenter.getHotMovie();
    }

    @Override
    protected void onRefresh() {
        mOneAdapter.clear();
        mPresenter.getHotMovie();
    }

    @Override
    public void loadSuccess(List<HotMovieBean.SubjectsEntity> subjects) {
        mIsFirst = false;
        showContentView();
        mOneAdapter.addAll(subjects);
    }

    @Override
    public void loadFailed() {
        showError();
    }


}
