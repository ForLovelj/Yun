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
import dth.com.yun.presenter.IBookFragmentPresenter;
import dth.com.yun.presenter.viewImpl.IBookFragmentView;

/**
 * Created by dth.
 * Des: 书籍fragment
 * Date: 2017/2/9.
 */

public class BookFragment extends BaseFragment<IBookFragmentPresenter> implements IBookFragmentView {

    @Bind(R.id.tab_book)
    TabLayout mTabBook;
    @Bind(R.id.vp_book)
    ViewPager mVpBook;

    private ArrayList<String>   mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVpBook.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mVpBook.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        mTabBook.setTabMode(TabLayout.MODE_FIXED);
        mTabBook.setupWithViewPager(mVpBook);
        showContentView();
    }

    private void initFragmentList() {
        mTitleList.add("文学");
        mTitleList.add("文化");
        mTitleList.add("生活");
        mFragments.add(BookCustomFragment.newInstance("文学"));
        mFragments.add(BookCustomFragment.newInstance("文化"));
        mFragments.add(BookCustomFragment.newInstance("生活"));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    protected IBookFragmentPresenter getPresenter() {
        return new IBookFragmentPresenter();
    }


}
