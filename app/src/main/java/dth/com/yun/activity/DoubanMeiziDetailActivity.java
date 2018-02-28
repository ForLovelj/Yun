package dth.com.yun.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dth.com.yun.R;
import dth.com.yun.fragment.DouBanMeiziDetailsFragment;
import dth.com.yun.fragment.DoubanMeiziFragment;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.model.DoubanMeizi;
import dth.com.yun.utils.DepthTransFormes;
import dth.com.yun.utils.GlideDownloadImageUtil;
import dth.com.yun.utils.ImmersiveUtil;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.utils.ShareUtils;
import dth.com.yun.utils.StatusBarUtil;
import dth.com.yun.utils.ToastUtil;
import dth.com.yun.view.HackyViewPager;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dth
 * Des: 豆瓣妹纸大图详情activity
 * Date: 2017/2/27.
 */

public class DoubanMeiziDetailActivity extends RxAppCompatActivity implements PhotoViewAttacher.OnPhotoTapListener {

    @Bind(R.id.vp_douban_meizhi)
    HackyViewPager mVpDoubanMeizhi;

    public static final String MEI_ZHI = "mei_zhi";
    private static final String EXTRA_INDEX = "extra_index";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private List<DoubanMeizi> mDoubanMeizis;
    public int mCurrentIndex;
    private ViewPagerAdapter mViewPagerAdapter;
    private MeiZhiAdapter mMeiZhiAdapter;
    private boolean isHide = false;
    private int mType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_meizhi_detail);
        StatusBarUtil.setColor(this, Color.BLACK);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mDoubanMeizis = (List<DoubanMeizi>) getIntent().getSerializableExtra(MEI_ZHI);
            mCurrentIndex = getIntent().getIntExtra(EXTRA_INDEX, -1);
            mType = getIntent().getIntExtra(DoubanMeiziFragment.EXTRA_TYPE, -1);
        }

        initToolBar();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mMeiZhiAdapter = new MeiZhiAdapter();
        mVpDoubanMeizhi.setPageTransformer(true,new DepthTransFormes());
        mVpDoubanMeizhi.setAdapter(mViewPagerAdapter);
        mVpDoubanMeizhi.setCurrentItem(mCurrentIndex);

        initListener();

        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_THREE,String.class)
                .compose(this.<String>bindToLifecycle())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        hideOrShowToolbar();
                    }
                });


    }

    private void initToolBar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(mDoubanMeizis.get(mCurrentIndex).getTitle());
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_meizi);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_fuli_save:
                        saveImage();
                        break;
                    case R.id.action_fuli_share:
                        shareImage();
                        break;
                }
                return false;
            }
        });


    }

    private void shareImage() {

        String url = mDoubanMeizis.get(mCurrentIndex).getUrl();
        GlideDownloadImageUtil.saveImageToLocal(this, url)
                .compose(this.<Uri>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("分享失败");
                    }

                    @Override
                    public void onNext(Uri uri) {
                        ShareUtils.shareImage(DoubanMeiziDetailActivity.this,uri,mDoubanMeizis.get(mCurrentIndex).getTitle());
                    }
                });
    }

    private void saveImage() {
        String url = mDoubanMeizis.get(mCurrentIndex).getUrl();
        GlideDownloadImageUtil.saveImageToLocal(this, url)
                .compose(this.<Uri>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("图片下载失败");
                    }

                    @Override
                    public void onNext(Uri uri) {
                        ToastUtil.showToast("已保存至"+ Environment.getExternalStorageDirectory().getAbsolutePath()+"/Yun相册");
                    }
                });
    }

    private void initListener() {
        mVpDoubanMeizhi.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mToolbar.setTitle(mDoubanMeizis.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                //进入activity会执行一次，退出时先执行supportFinishAfterTransition 然后再执行一次

                Log.d("aa", "onMapSharedElements: "+sharedElements);
                DoubanMeizi doubanMeizi = mDoubanMeizis.get(mVpDoubanMeizhi.getCurrentItem());
                DouBanMeiziDetailsFragment fragment = (DouBanMeiziDetailsFragment)
                        mViewPagerAdapter.instantiateItem(mVpDoubanMeizhi, mCurrentIndex);
//                names.clear();
//                names.add(doubanMeizi.getUrl());
                sharedElements.clear();
                sharedElements.put(doubanMeizi.getUrl(), fragment.getSharedElement());

                /*View view = (View) mMeiZhiAdapter.instantiateItem(mVpDoubanMeizhi, mCurrentIndex);
                ImageView tag = (ImageView) view.getTag();

                Log.d("aa", "url: "+mDoubanMeizis.get(mCurrentIndex).getUrl());

                Log.d("aa", "tag.getTransitionName(): "+tag.getTransitionName());

                if (sharedElements.size() == 0) {

                }
                if (sharedElements.size() == 0) {

                    sharedElements.clear();
                    sharedElements.put(tag.getTransitionName(),tag);
                }*/
//                names.clear();
//                names.add(tag.getTransitionName());


                    /*sharedElements.clear();
                    sharedElements.put(mDoubanMeizis.get(mCurrentIndex).getUrl(),tag);
                    names.clear();
                    names.add(mDoubanMeizis.get(mCurrentIndex).getUrl());*/


//                Log.d("aa", "names: "+names.get(0));

                for (Map.Entry<String, View> entry : sharedElements.entrySet()) {

                    Log.d("aa", "key : " +entry.getKey()+"   value : "+entry.getValue());
                }
                //                Log.d("aa", "names2: "+names.get(0));
//                super.onMapSharedElements(names, sharedElements);


            }
        });

    }

    @Override
    public void supportFinishAfterTransition() {
        Log.d("aa", "supportFinishAfterTransition: ------------");
        if (isHide) {
            //显示 状态栏显示
            ImmersiveUtil.exit(this);
        }

        Intent data = new Intent();
        data.putExtra("index", mCurrentIndex);
        data.putExtra("name",mDoubanMeizis.get(mCurrentIndex).getUrl());
//        RxBus.getDefault().send(data);
        RxBus.getDefault().post(mType,data);
        super.supportFinishAfterTransition();
    }

    @Override
    public void onBackPressed() {

        supportFinishAfterTransition();
    }

    protected void hideOrShowToolbar() {

        if (isHide) {
            //显示
            ImmersiveUtil.exit(this);
            mToolbar.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            isHide = false;
        } else {
            //隐藏
            ImmersiveUtil.enter(this);
            mToolbar.animate()
                    .translationY(-mToolbar.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            isHide = true;
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        supportFinishAfterTransition();
    }


    /**
     * ViewPager的适配器
     * 用fragment的方式
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DouBanMeiziDetailsFragment.newInstance(mDoubanMeizis.get(position).getUrl());
        }

        @Override
        public int getCount() {
            return mDoubanMeizis == null ? 0 : mDoubanMeizis.size();
        }
    }

    /**
     * 直接用ViewPager显示图片的方式 共享动画会有问题？
     */
    class MeiZhiAdapter extends PagerAdapter {

        LayoutInflater inflater;

        MeiZhiAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
            final PhotoView zoom_image_view = (PhotoView) view.findViewById(R.id.zoom_image_view);
            final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
            // 保存网络图片的路径

            String imageUrl = mDoubanMeizis.get(position).getUrl();
            zoom_image_view.setTransitionName(imageUrl);
            view.setTag(zoom_image_view);
//            zoom_image_view.setTransitionName(imageUrl);


            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(false);
            Glide.with(DoubanMeiziDetailActivity.this).load(imageUrl)
                    .crossFade(700)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Toast.makeText(getApplicationContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);
                            return false;
                        }

                        //这个用于监听图片是否加载完成
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //                            Toast.makeText(getApplicationContext(), "图片加载完成", Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);

                            /**这里应该是加载成功后图片的高*/
                            int height = zoom_image_view.getHeight();

                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            if (height > wHeight) {
                                zoom_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                zoom_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(zoom_image_view);

            zoom_image_view.setOnPhotoTapListener(DoubanMeiziDetailActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if (mDoubanMeizis == null || mDoubanMeizis.size() == 0) {
                return 0;
            }
            return mDoubanMeizis.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    public static void start(Activity context, List<DoubanMeizi> data,String transitionName, int index,int type, ImageView imageView) {
        Intent intent = new Intent(context, DoubanMeiziDetailActivity.class);
        intent.putExtra(MEI_ZHI, (ArrayList)data);//强转为arrayList  arrayList才实现了Serializable接口
        intent.putExtra(EXTRA_INDEX,index);
        intent.putExtra(DoubanMeiziFragment.EXTRA_TYPE,type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, transitionName);//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
