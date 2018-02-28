package dth.com.yun.presenter;

import java.util.List;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.GankIoDataBean;
import dth.com.yun.presenter.viewImpl.IAndroidView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class IAndroidPresenter extends BasePresenter<IAndroidView> {

    public IAndroidPresenter() {

    }


    public void getGankIoData(String id, int page, int per_page) {
        Subscription subscribe = HttpUtils.getInstence().getRebuilder().baseUrl(HttpUtils.API_GANKIO)
                .build().create(ApiService.class)
                .getGankIoData(id, page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        List<GankIoDataBean.ResultsEntity> results = gankIoDataBean.getResults();
                        mView.loadSuccess(results);

                    }
                });

        addSubscription(subscribe);
    }
}
