package dth.com.yun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.OneMovieDetailAdapter;
import dth.com.yun.base.BaseHeaderActivity;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.model.MovieDetailBean;
import dth.com.yun.presenter.IOneMovieDetailPresenter;
import dth.com.yun.presenter.viewImpl.IOneMovieDetailView;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.StringFormatUtil;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth.
 * Des: 电影详情页
 * Date: 2017/2/17.
 */

public class OneMovieDetailActivity extends BaseHeaderActivity<IOneMovieDetailPresenter> implements IOneMovieDetailView {

    @Bind(R.id.tv_one_title)
    TextView      mTvOneTitle;
    @Bind(R.id.xrv_cast)
    XRecyclerView mXrvCast;
    @Bind(R.id.activity_one_movie_detail)
    FrameLayout   mActivityOneMovieDetail;
    @Bind(R.id.img_item_bg)
    ImageView     mImgItemBg;
    @Bind(R.id.iv_one_photo)
    ImageView     mIvOnePhoto;
    @Bind(R.id.tv_one_rating_rate)
    TextView      mTvOneRatingRate;
    @Bind(R.id.tv_one_rating_number)
    TextView      mTvOneRatingNumber;
    @Bind(R.id.tv_one_directors)
    TextView      mTvOneDirectors;
    @Bind(R.id.tv_one_casts)
    TextView      mTvOneCasts;
    @Bind(R.id.tv_one_genres)
    TextView      mTvOneGenres;
    @Bind(R.id.tv_one_day)
    TextView      mTvOneDay;
    @Bind(R.id.tv_one_city)
    TextView      mTvOneCity;
    @Bind(R.id.ll_one_item)
    LinearLayout  mLlOneItem;
    @Bind(R.id.tv_intro)
    TextView      mTvIntro;
    private OneMovieDetailAdapter       mOneMovieDetailAdapter;
    private HotMovieBean.SubjectsEntity mSubjectsEntity;
    private String mMoreUrl;
    private String mMovieName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            mSubjectsEntity = (HotMovieBean.SubjectsEntity) getIntent().getSerializableExtra("bean");
        }

        if (!TextUtils.isEmpty(mSubjectsEntity.getId())) {
            mPresenter.getMovieDetail(mSubjectsEntity.getId());
        }

        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());//重新调用  因为先走了super当时的initSlideShapeTheme()的url参数并没有值


        ImgLoadUtil.showMovieImg(mIvOnePhoto,mSubjectsEntity.getImages().getLarge());
        ImgLoadUtil.showImgBg(mImgItemBg,mSubjectsEntity.getImages().getSmall());


        setTitle(mSubjectsEntity.getTitle());
        setSubTitle(String.format("主演：%s", StringFormatUtil.formatName(mSubjectsEntity.getCasts())));

        mTvOneGenres.setText("类型：" + StringFormatUtil.formatGenres(mSubjectsEntity.getGenres()));
        mTvOneRatingRate.setText("评分：" + String.valueOf(mSubjectsEntity.getRating().getAverage()));
        mTvOneRatingNumber.setText(mSubjectsEntity.getCollect_count() + "人评分");
        // 导演
        mTvOneDirectors.setText(mSubjectsEntity.getDirectors().get(0).getName());
        // 主演
        mTvOneCasts.setText(StringFormatUtil.formatName(mSubjectsEntity.getCasts()));

        initRecyclerView();

    }

    private void initRecyclerView() {
        mOneMovieDetailAdapter = new OneMovieDetailAdapter();
        mXrvCast.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXrvCast.setLayoutManager(mLayoutManager);
        mXrvCast.setPullRefreshEnabled(false);
        mXrvCast.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        mXrvCast.setNestedScrollingEnabled(false);
        mXrvCast.setHasFixedSize(false);
        mXrvCast.setAdapter(mOneMovieDetailAdapter);

    }

    @Override
    protected void setTitleClickMore() {
        if (!TextUtils.isEmpty(mMoreUrl)) {
            WebViewActivity.loadUrl(this,mMoreUrl,mMovieName);
        }
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_slide_shape;
    }

    @Override
    protected String setHeaderImgUrl() {
        if (mSubjectsEntity == null) {
            return "";
        }
        return mSubjectsEntity.getImages().getMedium();
    }

    @Override
    protected ImageView setHeaderImageView() {
        return mImgItemBg;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_one_movie_detail;
    }

    @Override
    protected IOneMovieDetailPresenter getPresenter() {
        return new IOneMovieDetailPresenter();
    }

    /**
     * 元素共享动画跳转
     *
     * @param context
     * @param subjectsEntity
     * @param imageView
     */
    public static void start(Activity context, HotMovieBean.SubjectsEntity subjectsEntity, ImageView imageView) {
        Intent intent = new Intent(context, OneMovieDetailActivity.class);
        //传递序列化bean时，bean里面所有内部类都要实现序列化
        intent.putExtra("bean", subjectsEntity);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    @Override
    public void loadSuccess(MovieDetailBean movieDetailBean) {
        showContentView();
        List<MovieDetailBean.PersonEntity> directors = movieDetailBean.getDirectors();
        List<MovieDetailBean.PersonEntity> casts = movieDetailBean.getCasts();
        List<MovieDetailBean.PersonEntity> data = mOneMovieDetailAdapter.getData();
        data.clear();
        data.addAll(directors);
        data.addAll(casts);
        mOneMovieDetailAdapter.setData(data);

        mMoreUrl = movieDetailBean.getAlt();
        mMovieName = movieDetailBean.getTitle();

        // 上映日期
        mTvOneDay.setText(String.format("上映日期：%s", movieDetailBean.getYear()));
        // 制片国家
        mTvOneCity.setText(String.format("制片国家/地区：%s", StringFormatUtil.formatGenres(movieDetailBean.getCountries())));
        //又名
        mTvOneTitle.setText(StringFormatUtil.formatGenres(movieDetailBean.getAka()));
        //剧情
        mTvIntro.setText(movieDetailBean.getSummary());
    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    protected void onRefresh() {
        mPresenter.getMovieDetail(mSubjectsEntity.getId());
    }
}
