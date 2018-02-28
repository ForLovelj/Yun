package dth.com.yun.presenter;

import java.util.List;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.HotMovieBean;
import dth.com.yun.presenter.viewImpl.IOneFragmentView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public class IOneFragmentPresenter extends BasePresenter<IOneFragmentView> {

    public IOneFragmentPresenter() {

    }

    public void getHotMovie() {
        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.API_DOUBAN)
                .build()
                .create(ApiService.class)
                .getHotMovie()
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
