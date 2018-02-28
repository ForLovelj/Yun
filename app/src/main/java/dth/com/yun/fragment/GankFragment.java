package dth.com.yun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.MyFragmentPagerAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.presenter.IGankFragmentPresenter;
import dth.com.yun.presenter.viewImpl.IgankFragmentView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by dth.
 * Des:展示干货的页面
 * Date: 2017/2/9.
 */

public class GankFragment extends BaseFragment<IGankFragmentPresenter> implements IgankFragmentView {

    @Bind(R.id.tab_gank)
    TabLayout mTabGank;
    @Bind(R.id.vp_gank)
    ViewPager mVpGank;
    private ArrayList<String>   mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected IGankFragmentPresenter getPresenter() {
        return new IGankFragmentPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();

        initDataFragment();

        /**
         * 当fragment嵌套fragment时注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mVpGank.setOffscreenPageLimit(3);
        myAdapter.notifyDataSetChanged();
        mTabGank.setTabMode(TabLayout.MODE_FIXED);
        mTabGank.setupWithViewPager(mVpGank);
        showContentView();
        // item点击跳转

        initRxBus();
    }

    private void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 0) {
                            mVpGank.setCurrentItem(3);
                        } else if (integer == 1) {
                            mVpGank.setCurrentItem(1);
                        } else if (integer == 2) {
                            mVpGank.setCurrentItem(2);
                        }
                    }
                });

        mPresenter.addSubscription(subscribe);
    }


    @Override
    public void initDataFragment() {
        mTitleList.add("每日推荐");
        mTitleList.add("福利");
        mTitleList.add("干货订制");
        mTitleList.add("大安卓");
        mFragments.add(new EverydayFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
        mFragments.add(AndroidFragment.newInstance("Android"));
    }
}
