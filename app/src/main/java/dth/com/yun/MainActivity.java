package dth.com.yun;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import dth.com.yun.activity.QRCodeActivity;
import dth.com.yun.adapter.MyFragmentPagerAdapter;
import dth.com.yun.base.BaseActivity;
import dth.com.yun.fragment.BookFragment;
import dth.com.yun.fragment.GankFragment;
import dth.com.yun.fragment.OneFragment;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxBusBaseMessage;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.presenter.IMainPresenter;
import dth.com.yun.presenter.viewImpl.IMainView;
import dth.com.yun.runtimepermission.PermissionsManager;
import dth.com.yun.runtimepermission.PermissionsResultAction;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.StatusBarUtil;
import rx.functions.Action1;

import static dth.com.yun.R.id.fab;
import static dth.com.yun.R.layout.nav_header_main;

public class MainActivity extends BaseActivity<IMainPresenter> implements IMainView, View.OnClickListener, ViewPager.OnPageChangeListener {


    @Bind(R.id.iv_title_menu)
    ImageView            mIvTitleMenu;
    @Bind(R.id.ll_title_menu)
    FrameLayout          mLlTitleMenu;
    @Bind(R.id.iv_title_gank)
    ImageView            mIvTitleGank;
    @Bind(R.id.iv_title_one)
    ImageView            mIvTitleOne;
    @Bind(R.id.iv_title_dou)
    ImageView            mIvTitleDou;
    @Bind(R.id.toolbar)
    Toolbar              mToolbar;
    @Bind(R.id.vp_content)
    ViewPager            mVpContent;
    @Bind(fab)
    FloatingActionButton mFab;
    @Bind(R.id.nav_view)
    NavigationView       mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout         mDrawerLayout;

    private long exitTime  = 0;
    private long exitDelay = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "授权通过", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {

                Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        });
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, mDrawerLayout, getResources().getColor(R.color.colorTheme));

        initDrawerLayout();
        initFragment();
        initListener();
        initRxBus();
        showContentView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }

    private void initRxBus() {
        RxBus.getDefault()
                .toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .compose(this.<RxBusBaseMessage>bindToLifecycle())
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        mVpContent.setCurrentItem(1);
                    }
                });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected IMainPresenter getPresenter() {
        return new IMainPresenter();
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        mNavView.inflateHeaderView(nav_header_main);
        View headerView = mNavView.getHeaderView(0);
        //        LinearLayout viewById1 = (LinearLayout) headerView.findViewById(R.id.ll_header_bg);
        //        viewById1.setBackground();
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        ImgLoadUtil.displayCircle(ivAvatar, ConstantsImageUrl.IC_AVATAR);
        LinearLayout llNavHomepage = (LinearLayout) headerView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headerView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedback = (LinearLayout) headerView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headerView.findViewById(R.id.ll_nav_about);
        llNavHomepage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedback.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }

    private void initListener() {
        mLlTitleMenu.setOnClickListener(this);
        mIvTitleGank.setOnClickListener(this);
        mIvTitleDou.setOnClickListener(this);
        mIvTitleOne.setOnClickListener(this);
        mFab.setOnClickListener(this);
    }

    private void initFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GankFragment());
        fragments.add(new OneFragment());
        fragments.add(new BookFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mVpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        mVpContent.setOffscreenPageLimit(2);
        mVpContent.addOnPageChangeListener(this);
        mIvTitleGank.setSelected(true);
        mVpContent.setCurrentItem(0);

        mTool_bar.setVisibility(View.GONE);//基类的一般的toolbar需要隐藏掉
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:// 开启菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
                // 关闭
                //                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:// 干货栏
                if (mVpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    mIvTitleGank.setSelected(true);
                    mIvTitleOne.setSelected(false);
                    mIvTitleDou.setSelected(false);
                    mVpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:// 电影栏
                if (mVpContent.getCurrentItem() != 1) {//不然cpu会有损耗
                    mIvTitleGank.setSelected(false);
                    mIvTitleOne.setSelected(true);
                    mIvTitleDou.setSelected(false);
                    mVpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:// 书籍栏
                if (mVpContent.getCurrentItem() != 2) {//不然cpu会有损耗
                    mIvTitleGank.setSelected(false);
                    mIvTitleOne.setSelected(false);
                    mIvTitleDou.setSelected(true);
                    mVpContent.setCurrentItem(2);
                }
                break;
            case R.id.ll_nav_homepage:// 主页
                break;

            case R.id.ll_nav_scan_download://扫码下载
                mDrawerLayout.closeDrawer(GravityCompat.START);
                QRCodeActivity.start(this);
                break;
            case R.id.ll_nav_deedback:// 问题反馈
                break;
            case R.id.ll_nav_about:// 关于
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mIvTitleGank.setSelected(true);
                mIvTitleOne.setSelected(false);
                mIvTitleDou.setSelected(false);
                break;
            case 1:
                mIvTitleOne.setSelected(true);
                mIvTitleGank.setSelected(false);
                mIvTitleDou.setSelected(false);
                break;
            case 2:
                mIvTitleDou.setSelected(true);
                mIvTitleOne.setSelected(false);
                mIvTitleGank.setSelected(false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > exitDelay) {
                Toast.makeText(this, "再按一次退出Yun.", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
