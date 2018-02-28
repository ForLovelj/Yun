package dth.com.yun.base;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import dth.com.yun.R;
import dth.com.yun.presenter.viewImpl.IBaseView;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.utils.StatusBarUtil;
import dth.com.yun.view.CustomChangeBounds;
import dth.com.yun.view.MyNestedScrollView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/17.
 */

public abstract class BaseHeaderActivity<T extends BasePresenter> extends RxAppCompatActivity {

    private   RelativeLayout    mContainer;
    private   LinearLayout      mLlProgressBar;
    private   View              mRefresh;
    private   AnimationDrawable mAnimationDrawable;
    protected View              mContentView;
    private   Toolbar           mTool_bar;
    protected T                 mPresenter;
    private ImageView mToolBar_bg;

    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 滑动多少距离后标题不透明
    private int slidingDistance;

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

        View view = View.inflate(this,R.layout.activity_header_base, null);

        //头部布局
        View mHeaderView = View.inflate(this, setHeaderLayout(), null);

        RelativeLayout.LayoutParams headerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(headerParams);
        RelativeLayout mHeaderContainer = (RelativeLayout) view.findViewById(R.id.header_container);
        mHeaderContainer.addView(mHeaderView);

        //标题布局
        View mTitleView = View.inflate(this, R.layout.base_header_title_bar, null);
        mTool_bar = (Toolbar) mTitleView.findViewById(R.id.tb_base_title);
        mToolBar_bg = (ImageView) mTitleView.findViewById(R.id.iv_base_titlebar_bg);

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleView.setLayoutParams(titleParams);
        RelativeLayout mTitleContainer = (RelativeLayout) view.findViewById(R.id.title_container);
        mTitleContainer.addView(mTitleView);

        //子类的内容布局
        mContainer = (RelativeLayout) view.findViewById(R.id.container);
        mContentView = View.inflate(this, setLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
        mContainer.addView(mContentView);

        getWindow().setContentView(view);

        // 设置自定义元素共享切换动画
        setMotion(setHeaderPicView(),false);

        // 初始化滑动渐变
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

        // 设置toolbar
        setToolBar();

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

    }

    /**
     * a. 布局高斯透明图 header布局
     */
    protected abstract int setHeaderLayout();

    /**
     * b. 设置头部header高斯背景 imgUrl
     */
    protected abstract String setHeaderImgUrl();

    /**
     * c. 设置头部header布局 高斯背景ImageView控件
     */
    protected abstract ImageView setHeaderImageView();

    /**
     * 设置头部header布局 左侧的图片(需要设置曲线路径切换动画时重写)
     */
    protected ImageView setHeaderPicView() {
        return new ImageView(this);
    }

    /**
     * 1. 标题
     */
    public void setTitle(CharSequence text) {
        mTool_bar.setTitle(text);
    }

    /**
     * 2. 副标题
     */
    protected void setSubTitle(CharSequence text) {
        mTool_bar.setSubtitle(text);
    }

    /**
     * 3. toolbar 单击"更多信息"
     */
    protected void setTitleClickMore() {
    }


    protected abstract int setLayoutId();

    protected abstract T getPresenter();

    protected Toolbar getToolbar() {
        return mTool_bar;
    }



    /**
     * 设置toolbar
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
        // 手动设置才有效果
        mTool_bar.setTitleTextAppearance(this, R.style.ToolBar_Title);
        mTool_bar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        mTool_bar.inflateMenu(R.menu.base_header_menu);
        mTool_bar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        mTool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTool_bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionbar_more:// 更多信息
                        setTitleClickMore();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 设置自定义 Shared Element切换动画
     * 默认不开启曲线路径切换动画，
     * 开启需要重写setHeaderPicView()，和调用此方法并将isShow值设为true
     *
     * @param imageView 共享的图片
     * @param isShow    是否显示曲线动画
     */
    protected void setMotion(ImageView imageView, boolean isShow) {
        if (!isShow) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //定义ArcMotion
            ArcMotion arcMotion = new ArcMotion();
            arcMotion.setMinimumHorizontalAngle(50f);
            arcMotion.setMinimumVerticalAngle(50f);
            //插值器，控制速度
            Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

            //实例化自定义的ChangeBounds
            CustomChangeBounds changeBounds = new CustomChangeBounds();
            changeBounds.setPathMotion(arcMotion);
            changeBounds.setInterpolator(interpolator);
            changeBounds.addTarget(imageView);
            //将切换动画应用到当前的Activity的进入和返回
            getWindow().setSharedElementEnterTransition(changeBounds);
            getWindow().setSharedElementReturnTransition(changeBounds);
        }
    }

    /**
     * *** 初始化滑动渐变 一定要实现 ******
     *
     * @param imgUrl    header头部的高斯背景imageUrl
     * @param mHeaderBg header头部高斯背景ImageView控件
     */
    protected void initSlideShapeTheme(String imgUrl, ImageView mHeaderBg) {
        setImgHeaderBg(imgUrl);

        // toolbar 的高
        int toolbarHeight = mTool_bar.getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);

        // 使toolbar的背景图向上移动到toolbar的最低端，背景图保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = mToolBar_bg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) mToolBar_bg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        mToolBar_bg.setImageAlpha(0);
//        StatusBarUtils.setTranslucentImageHeader(this, 0, mTool_bar);
        //使状态栏透明，并使toolbar向下偏移一个状态栏的高度
        StatusBarUtil.setTranslucentForImageView(this, 0, mTool_bar);

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);

            ViewGroup.LayoutParams imgItemBgparams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgparams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }

    private void initScrollViewListener() {
        // 为了兼容23以下
        ((MyNestedScrollView) findViewById(R.id.mns_base)).setOnScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) + StatusBarUtil.getStatusBarHeight(this));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = mToolBar_bg.getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            mToolBar_bg.setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            mToolBar_bg.setImageDrawable(drawable);
        }
    }

    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(imgUrl)
                    .error(R.drawable.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mTool_bar.setBackgroundColor(Color.TRANSPARENT);
                    mToolBar_bg.setImageAlpha(0);
                    mToolBar_bg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(mToolBar_bg);
        }
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
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_header_menu, menu);
        return true;
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
