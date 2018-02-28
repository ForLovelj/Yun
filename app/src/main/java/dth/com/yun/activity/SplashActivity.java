package dth.com.yun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import dth.com.yun.ConstantsImageUrl;
import dth.com.yun.MainActivity;
import dth.com.yun.R;
import dth.com.yun.utils.PerfectClickListener;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.iv_pic)
    ImageView      mIvPic;
    @Bind(R.id.tv_jump)
    TextView       mTvJump;
    @Bind(R.id.iv_defult_pic)
    ImageView      mIvDefultPic;
    @Bind(R.id.activity_splash)
    RelativeLayout mActivitySplash;

    private boolean isOne = false;//保证至执行一次toMainActivity 方法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);

        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(mIvPic);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);

        mTvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
            }
        });
    }

    private void toMainActivity() {

        if (isOne) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isOne = true;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
