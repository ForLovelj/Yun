package dth.com.yun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.base.BaseHeaderActivity;
import dth.com.yun.model.BookBean;
import dth.com.yun.model.BookDetailBean;
import dth.com.yun.presenter.IBookDetailPresenter;
import dth.com.yun.presenter.viewImpl.IBookDetailView;
import dth.com.yun.utils.CommonUtils;
import dth.com.yun.utils.ImgLoadUtil;
import dth.com.yun.utils.StringFormatUtil;
import dth.com.yun.webview.WebViewActivity;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public class BookDetailActivity extends BaseHeaderActivity<IBookDetailPresenter> implements IBookDetailView{

    private static final String BOOKS_ENTITY = "booksEntity";

    @Bind(R.id.tv_book_summary)
    TextView     mTvBookSummary;
    @Bind(R.id.tv_author_intro)
    TextView     mTvAuthorIntro;
    @Bind(R.id.tv_catalog)
    TextView     mTvCatalog;
    @Bind(R.id.activity_book_detail)
    LinearLayout mActivityBookDetail;
    @Bind(R.id.img_item_bg)
    ImageView    mImgItemBg;
    @Bind(R.id.iv_one_photo)
    ImageView    mIvOnePhoto;
    @Bind(R.id.tv_one_directors)
    TextView     mTvOneDirectors;
    @Bind(R.id.tv_one_rating_rate)
    TextView     mTvOneRatingRate;
    @Bind(R.id.tv_one_rating_number)
    TextView     mTvOneRatingNumber;
    @Bind(R.id.tv_one_casts)
    TextView     mTvOneCasts;
    @Bind(R.id.tv_one_genres)
    TextView     mTvOneGenres;
    @Bind(R.id.ll_book_detail)
    LinearLayout mLlBookDetail;
    private BookBean.BooksEntity mBookBean;
    private String mBookDetailUrl;
    private String mBookDetailName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mBookBean = (BookBean.BooksEntity) getIntent().getSerializableExtra(BOOKS_ENTITY);
        }

        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());//重新调用  因为先走了super当时的initSlideShapeTheme()的url参数并没有值
        setMotion(mIvOnePhoto,true); //自定义 Shared Element切换动画 开启曲线路径切换动画，

        mPresenter.getBookDetail(mBookBean.getId());

        setTitle(mBookBean.getTitle());
        setSubTitle("作者：" + mBookBean.getAuthor());

        ImgLoadUtil.showImgBg(mImgItemBg,mBookBean.getImages().getMedium());//高斯模糊背景
        ImgLoadUtil.showBookImg(mIvOnePhoto,mBookBean.getImages().getLarge());

        mTvOneDirectors.setText(StringFormatUtil.formatGenres(mBookBean.getAuthor()));
        mTvOneRatingRate.setText("评分：" + mBookBean.getRating().getAverage());
        mTvOneRatingNumber.setText(mBookBean.getRating().getNumRaters()+"人评分");
        mTvOneCasts.setText(mBookBean.getPubdate());//出版日期
        mTvOneGenres.setText("出版社："+mBookBean.getPublisher());//出版社
    }

    @Override
    protected void setTitleClickMore() {
        if (!TextUtils.isEmpty(mBookDetailUrl)) {
            WebViewActivity.loadUrl(this, mBookDetailUrl, mBookDetailName);
        }
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_book_detail;
    }

    @Override
    protected String setHeaderImgUrl() {
        if (mBookBean == null) {
            return "";
        }
        return mBookBean.getImages().getMedium();
    }

    @Override
    protected ImageView setHeaderImageView() {
        return mImgItemBg;
    }

    @Override
    protected ImageView setHeaderPicView() {
        return mIvOnePhoto;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected IBookDetailPresenter getPresenter() {
        return new IBookDetailPresenter();
    }

    @Override
    public void loadSuccess(BookDetailBean bookDetailBean) {
        showContentView();

        mBookDetailUrl = bookDetailBean.getAlt();
        mBookDetailName = bookDetailBean.getTitle();

        mTvBookSummary.setText(bookDetailBean.getSummary());
        mTvAuthorIntro.setText(bookDetailBean.getAuthor_intro());
        mTvCatalog.setText(bookDetailBean.getCatalog());

    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    protected void onRefresh() {
        mPresenter.getBookDetail(mBookBean.getId());
    }

    public static void start(Activity context, BookBean.BooksEntity booksEntity, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(BOOKS_ENTITY, booksEntity);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_book_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
