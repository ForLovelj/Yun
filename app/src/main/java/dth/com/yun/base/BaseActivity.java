package dth.com.yun.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import dth.com.yun.R;
import dth.com.yun.presenter.viewImpl.IBaseView;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.utils.StatusBarUtil;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements IBaseView{

    private RelativeLayout mContainer;
    private LinearLayout mLlProgressBar;
    private View mRefresh;
    private AnimationDrawable mAnimationDrawable;
    protected View mContentView;
    protected Toolbar mTool_bar;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);

        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.attach((IBaseView) this);
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        View view = View.inflate(this,R.layout.activity_base, null);
        mContainer = (RelativeLayout) view.findViewById(R.id.container);
        //子类的内容布局
        mContentView = View.inflate(this, setLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
        mContainer.addView(mContentView);

        getWindow().setContentView(view);

        // 设置透明状态栏
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);

        mTool_bar = (Toolbar) view.findViewById(R.id.tool_bar);
        mLlProgressBar = (LinearLayout) view.findViewById(R.id.ll_progress_bar);
        mRefresh = view.findViewById(R.id.ll_error_refresh);
        ImageView img = (ImageView) view.findViewById(R.id.img_progress);

//        mTool_bar.setVisibility(View.GONE);
        setToolBar();

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

    private View initBaseView() {
        View view = View.inflate(this,R.layout.activity_base, null);
        mContainer = (RelativeLayout) view.findViewById(R.id.container);
        //子类的内容布局
        mContentView = View.inflate(this, setLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
        mContainer.addView(mContentView);

        mTool_bar = (Toolbar) view.findViewById(R.id.tool_bar);
        mLlProgressBar = (LinearLayout) view.findViewById(R.id.ll_progress_bar);
        mRefresh = view.findViewById(R.id.ll_error_refresh);
        ImageView img = (ImageView) view.findViewById(R.id.img_progress);

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

        return view;
    }


    protected abstract int setLayoutId();

    protected abstract T getPresenter();

    protected Toolbar getToolbar() {
        return mTool_bar;
    }

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

    /**
     * 设置titlebar
     */
    protected void setToolBar() {
        setSupportActionBar(mTool_bar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        mTool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }


    @Override
    protected void onDestroy() {
//        AppManager.getAppManager().finishActivity(this);
        ButterKnife.unbind(this);
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.unsubcrible();
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
