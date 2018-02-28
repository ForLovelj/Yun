package dth.com.yun.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import dth.com.yun.R;
import dth.com.yun.presenter.viewImpl.IBaseView;
import dth.com.yun.utils.PerfectClickListener;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {

    private RelativeLayout mContainer;
    private View mContentView;
    protected Context mContext;
    protected boolean mIsVisible;
    private LinearLayout mLlProgressBar;
    private View mRefresh;
    private AnimationDrawable mAnimationDrawable;
    protected T mPresenter;
    private View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initContentView(inflater, container, savedInstanceState);
        ButterKnife.bind(this,view);

        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.attach((IBaseView) this);
        }
        return view;
    }

    private View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base, container,false);
        mContainer = (RelativeLayout) mView.findViewById(R.id.container);

        mContentView = View.inflate(mContext, setLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
        mContainer.addView(mContentView);
        return mView;
    }

    protected abstract int setLayoutId();

    protected abstract T getPresenter();

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {

    }

    protected void onVisible() {
        loadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlProgressBar = (LinearLayout) mView.findViewById(R.id.ll_progress_bar);
        mRefresh = mView.findViewById(R.id.ll_error_refresh);
        ImageView img = (ImageView) mView.findViewById(R.id.img_progress);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        // 点击加载失败布局
        mRefresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });

        mContentView.setVisibility(View.GONE);

    }

//    protected <T extends View> T getView(int id) {
//        return (T) getView().findViewById(id);
//    }


    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (mLlProgressBar.getVisibility() != View.VISIBLE) {
            mLlProgressBar.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (mContentView.getVisibility() != View.GONE) {
            mContentView.setVisibility(View.GONE);
        }
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
        if (mContentView.getVisibility() != View.VISIBLE) {
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefresh.getVisibility() != View.VISIBLE) {
            mRefresh.setVisibility(View.VISIBLE);
        }
        if (mContentView.getVisibility() != View.GONE) {
            mContentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.unsubcrible();
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
