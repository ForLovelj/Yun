package dth.com.yun.presenter;

import android.os.Handler;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.MovieDetailBean;
import dth.com.yun.presenter.viewImpl.IOneMovieDetailView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/17.
 */

public class IOneMovieDetailPresenter extends BasePresenter<IOneMovieDetailView> {

    public void getMovieDetail(String id) {
        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.API_DOUBAN)
                .build()
                .create(ApiService.class)
                .getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(MovieDetailBean movieDetailBean) {

                        transformData(movieDetailBean);
                    }
                });

        addSubscription(subscribe);
    }

    Handler handler = new Handler();


    /**
     * 异步线程转换数据
     */
    private void transformData(final MovieDetailBean movieDetailBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < movieDetailBean.getDirectors().size(); i++) {
                    movieDetailBean.getDirectors().get(i).setType("导演");
                }
                for (int i = 0; i < movieDetailBean.getCasts().size(); i++) {
                    movieDetailBean.getCasts().get(i).setType("演员");
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.loadSuccess(movieDetailBean);
                    }
                });
            }
        }).start();
    }
}
