package dth.com.yun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseActivity;
import dth.com.yun.base.BasePresenter;
import dth.com.yun.utils.PerfectClickListener;
import dth.com.yun.utils.QRCodeUtil;
import dth.com.yun.utils.ShareUtils;

public class QRCodeActivity extends BaseActivity {

    @Bind(R.id.iv_erweima)
    ImageView    mIvErweima;
    @Bind(R.id.tv_share)
    TextView     mTvShare;
    @Bind(R.id.activity_qrcode)
    LinearLayout mActivityQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTool_bar.setTitle("扫码下载");

        String url = "https://fir.im/dthyun";
        QRCodeUtil.showThreadImage(this,url,mIvErweima,R.mipmap.ic_launcher);
        mTvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(),"分享给大家一个好玩的app啦~~~");
            }
        });

        showContentView();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, QRCodeActivity.class);
        mContext.startActivity(intent);
    }
}
