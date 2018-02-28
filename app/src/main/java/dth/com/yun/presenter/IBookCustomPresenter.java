package dth.com.yun.presenter;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.BookBean;
import dth.com.yun.presenter.viewImpl.IBookCustomView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public class IBookCustomPresenter extends BasePresenter<IBookCustomView> {

    public void getCustomData(String tag, int start, int count) {

        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.API_DOUBAN)
                .build()
                .create(ApiService.class)
                .getBook(tag,start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(BookBean bookBean) {

                        mView.loadSuccess(bookBean);
                    }
                });

        addSubscription(subscribe);
    }
}
