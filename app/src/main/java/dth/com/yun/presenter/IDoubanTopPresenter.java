package dth.com.yun.presenter;

import java.util.List;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.presenter.viewImpl.IDoubanTopView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth
 * des:
 : date: 2017/2/20.
 */

public class IDoubanTopPresenter extends BasePresenter<IDoubanTopView> {

    public void getDouBanTop250(int start , int count){
        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.API_DOUBAN)
                .build()
                .create(ApiService.class)
                .getMovieTop250(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {

                        List<HotMovieBean.SubjectsEntity> subjects = hotMovieBean.getSubjects();
                        mView.loadSuccess(subjects);
                    }
                });

        addSubscription(subscribe);
    }

}
