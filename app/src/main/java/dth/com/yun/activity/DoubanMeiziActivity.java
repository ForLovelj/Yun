package dth.com.yun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.MyFragmentPagerAdapter;
import dth.com.yun.base.BaseActivity;
import dth.com.yun.fragment.DoubanMeiziFragment;
import dth.com.yun.presenter.IDoubanMeiziPresenter;

/**
 * Created by dth
 * Des:
 * Date: 2017/2/27.
 */

public class DoubanMeiziActivity extends BaseActivity {

    @Bind(R.id.tab_douban_meizhi)
    TabLayout mTabDoubanMeizhi;
    @Bind(R.id.vp_douban_meizhi)
    ViewPager mVpDoubanMeizhi;

    private List<String> titles = Arrays.asList("大胸妹", "小翘臀", "黑丝袜", "美腿控", "高颜值", "大杂烩");

    private List<Integer> cids = Arrays.asList(2, 6, 7, 3, 4, 5);

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTool_bar.setTitle("豆瓣妹纸");
        initFragments();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, titles);

        mVpDoubanMeizhi.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mVpDoubanMeizhi.setOffscreenPageLimit(5);
        myAdapter.notifyDataSetChanged();
        mTabDoubanMeizhi.setTabMode(TabLayout.MODE_FIXED);
        mTabDoubanMeizhi.setupWithViewPager(mVpDoubanMeizhi);
        showContentView();
    }

    private void initFragments() {
        for (int i = 0; i < titles.size(); i++) {
            mFragments.add(DoubanMeiziFragment.newInstance(cids.get(i),i));
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_douban_meizhi;
    }

    @Override
    protected IDoubanMeiziPresenter getPresenter() {
        return null;
    }


}
