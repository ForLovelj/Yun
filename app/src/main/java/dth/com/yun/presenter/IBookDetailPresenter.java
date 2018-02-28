package dth.com.yun.presenter;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.BookDetailBean;
import dth.com.yun.presenter.viewImpl.IBookDetailView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public class IBookDetailPresenter extends BasePresenter<IBookDetailView> {

    public void getBookDetail(String id) {

        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.API_DOUBAN)
                .build()
                .create(ApiService.class)
                .getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {

                        mView.loadSuccess(bookDetailBean);
                    }
                });

        addSubscription(subscribe);
    }
}
