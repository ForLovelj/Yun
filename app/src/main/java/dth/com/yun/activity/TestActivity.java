package dth.com.yun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dth.com.yun.R;
import dth.com.yun.model.DoubanMeizi;
import dth.com.yun.utils.CommonUtils;
import uk.co.senab.photoview.PhotoView;

public class TestActivity extends AppCompatActivity {

    public static final  String MEI_ZHI     = "mei_zhi";
    private static final String EXTRA_INDEX = "extra_index";
    @Bind(R.id.zoom_image_view)
    PhotoView   mZoomImageView;
    @Bind(R.id.loading)
    ProgressBar mLoading;
    private List<DoubanMeizi> mDoubanMeizis;
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mDoubanMeizis = (List<DoubanMeizi>) getIntent().getSerializableExtra(MEI_ZHI);
            mCurrentIndex = getIntent().getIntExtra(EXTRA_INDEX, -1);
        }

        mLoading.setVisibility(View.GONE);
        Glide.with(this)
                .load(mDoubanMeizis.get(mCurrentIndex).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(500)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.img_default_meizi)
                .into(mZoomImageView);
        /*Glide.with(this).load(mDoubanMeizis.get(mCurrentIndex).getUrl())
                .crossFade(700)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(App.getContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
                        mLoading.setVisibility(View.GONE);
                        return false;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //                            Toast.makeText(getApplicationContext(), "图片加载完成", Toast.LENGTH_SHORT).show();
                        mLoading.setVisibility(View.GONE);

                        //这里应该是加载成功后图片的高
                        int height = mZoomImageView.getHeight();

                        int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                        if (height > wHeight) {
                            mZoomImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            mZoomImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        return false;
                    }
                }).into(mZoomImageView);*/
    }

    public static void start(Activity context, List<DoubanMeizi> data, String transitionName, int index, ImageView imageView) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(MEI_ZHI, (ArrayList) data);//强转为arrayList  arrayList才实现了Serializable接口
        intent.putExtra(EXTRA_INDEX, index);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_meizhi_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
