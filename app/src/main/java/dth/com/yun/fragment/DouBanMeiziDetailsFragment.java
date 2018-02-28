package dth.com.yun.fragment;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import dth.com.yun.App;
import dth.com.yun.R;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.http.rx.RxCodeConstants;
import dth.com.yun.presenter.IDouBanMeiziDetailsPresenter;
import dth.com.yun.utils.GlideDownloadImageUtil;
import dth.com.yun.utils.ToastUtil;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dth
 * Des:豆瓣妹纸查看大图Fragment
 * Date: 2017/2/28.
 */

public class DouBanMeiziDetailsFragment extends BaseFragment<IDouBanMeiziDetailsPresenter> implements RequestListener<String, GlideDrawable> {

    private static final String EXTRA_URL = "extra_url";
    @Bind(R.id.zoom_image_view)
    PhotoView   mZoomImageView;
    @Bind(R.id.loading)
    ProgressBar mLoading;
    private String mUrl;
    private PhotoViewAttacher mPhotoViewAttacher;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showContentView();

        mUrl = getArguments().getString(EXTRA_URL);


        Glide.with(this).load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(0)
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.viewpager_very_image;
    }

    @Override
    protected IDouBanMeiziDetailsPresenter getPresenter() {
        return new IDouBanMeiziDetailsPresenter();
    }

    public static DouBanMeiziDetailsFragment newInstance(String url) {

        DouBanMeiziDetailsFragment mMeiziFragment = new DouBanMeiziDetailsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(EXTRA_URL, url);
        mMeiziFragment.setArguments(mBundle);

        return mMeiziFragment;
    }

    public View getSharedElement() {

        return mZoomImageView;
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        Toast.makeText(App.getContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
        mLoading.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        mZoomImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mZoomImageView.setImageDrawable(resource);
        mPhotoViewAttacher = new PhotoViewAttacher(mZoomImageView);
        mLoading.setVisibility(View.GONE);
        setPhotoViewAttacher();
        return false;
    }

    private void setPhotoViewAttacher() {
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("是否保存到本地?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveImage();
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });



        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {

                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_THREE,"hideAppBar");
            }
        });
    }

    private void saveImage() {
        GlideDownloadImageUtil.saveImageToLocal(getContext(), mUrl)
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
}
