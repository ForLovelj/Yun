package dth.com.yun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dth.com.yun.Constants;
import dth.com.yun.R;
import dth.com.yun.activity.DoubanMeiziActivity;
import dth.com.yun.adapter.EverydayAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.http.cache.ACache;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxBusBaseMessage;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.model.AndroidBean;
import dth.com.yun.model.FrontpageBean;
import dth.com.yun.model.GankIoDayBean;
import dth.com.yun.presenter.IEveryDayPresenter;
import dth.com.yun.presenter.viewImpl.IEveryDayView;
import dth.com.yun.utils.DebugUtil;
import dth.com.yun.utils.GlideImageLoader;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */
public class EverydayFragment extends BaseFragment<IEveryDayPresenter> implements IEveryDayView, View.OnClickListener {

    @Bind(R.id.xrv_everyday)
    XRecyclerView mXrvEveryday;
    @Bind(R.id.iv_loading)
    ImageView     mIvLoading;
    @Bind(R.id.ll_loading)
    LinearLayout  mLlLoading;

    private RotateAnimation              animation;
    private ACache                       maCache;
    private ArrayList<String>            mBannerImages;

    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;//是否是第一次进来
    // 是否是上一天的请求
    private boolean isOldDayRequest;
    private View mHeaderView;
    private View mFooterView;
    private Banner mBanner;
    private ImageView mIb_xiandu;
    private ImageView mDaily_btn;
    private ImageView mIb_movie_hot;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_everyday;
    }

    @Override
    protected IEveryDayPresenter getPresenter() {
        return new IEveryDayPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showContentView();
        mLlLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatCount(10);
        mIvLoading.setAnimation(animation);
        animation.startNow();

        maCache = ACache.get(getContext());
        mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);
        //        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        DebugUtil.error("----mBannerImages: " + (mBannerImages == null));
//        DebugUtil.error("----mLists: " + (mLists == null));

        // 设置本地数据点击事件等
//        initLocalSetting();
        initRecyclerView();

        //准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    protected void loadData() {

        // 显示时轮播图滚动
        if (mBanner != null) {
            mBanner.startAutoPlay();
            mBanner.setDelayTime(5000);
        }

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        //        if (!mIsVisible || !mIsPrepared) {
//            return;
//        }
        showRotaLoading(true);
        mPresenter.getBanncerPage();
        mPresenter.getRecyclerViewData();
    }

    private void initRecyclerView() {
        mXrvEveryday.setPullRefreshEnabled(false);
        mXrvEveryday.setLoadingMoreEnabled(false);
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(),R.layout.header_item_everyday, null);
            mBanner = (Banner) mHeaderView.findViewById(R.id.banner);
            mIb_xiandu = (ImageView) mHeaderView.findViewById(R.id.ib_xiandu);
            mDaily_btn = (ImageView) mHeaderView.findViewById(R.id.daily_btn);
            mIb_movie_hot = (ImageView) mHeaderView.findViewById(R.id.ib_movie_hot);

            mIb_xiandu.setOnClickListener(this);
            mDaily_btn.setOnClickListener(this);
            mIb_movie_hot.setOnClickListener(this);

            mXrvEveryday.addHeaderView(mHeaderView);
        }
        if (mFooterView == null) {
            mFooterView = View.inflate(getContext(),R.layout.footer_item_everyday, null);
            mXrvEveryday.addFootView(mFooterView, true);
            mXrvEveryday.noMoreLoading();
        }
        mXrvEveryday.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
        mXrvEveryday.setNestedScrollingEnabled(false);
        mXrvEveryday.setHasFixedSize(false);
        mXrvEveryday.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showContentData(List<List<AndroidBean>> lists) {
        showContentView();
        mIsFirst = false;
        showRotaLoading(false);
        EverydayAdapter everydayAdapter = new EverydayAdapter();
        mXrvEveryday.setAdapter(everydayAdapter);
        everydayAdapter.setData(lists);
//        everydayAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadSuccess(Object object) {

        mIsFirst = false;
        if (object instanceof FrontpageBean) {

        } else if (object instanceof GankIoDayBean) {

        }

    }

    @Override
    protected void onRefresh() {
        showRotaLoading(true);
        mPresenter.getBanncerPage();
        mPresenter.getRecyclerViewData();
    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    public void showBanncerPage(List<String> urlList) {
        showContentView();
        mBanner.setImages(urlList).setImageLoader(new GlideImageLoader()).start();
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
//                ToastUtil.showToast(position+"");
            }
        });
    }

    /**
     * 加载动画
     * @param isLoading
     */
    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            mLlLoading.setVisibility(View.VISIBLE);
            mXrvEveryday.setVisibility(View.GONE);
            animation.startNow();
        } else {
            mLlLoading.setVisibility(View.GONE);
            mXrvEveryday.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (mBanner != null && mBanner != null) {
            mBanner.stopAutoPlay();
        }
        super.onInvisible();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        mXrvEveryday.setFocusable(false);
        DebugUtil.error("-----EverydayFragment----onResume()");
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugUtil.error("-----EverydayFragment----onPause()");
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_xiandu:
                WebViewActivity.loadUrl(v.getContext(), "https://gank.io/xiandu", "加载中...");
                break;
            case R.id.daily_btn:
//                ToastUtil.showToast("每日干货推荐");
                Intent intent = new Intent(getContext(), DoubanMeiziActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_movie_hot:
//                ToastUtil.showToast("新电影热映榜");
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE,new RxBusBaseMessage());
                break;
        }
    }
}
