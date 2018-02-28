package dth.com.yun.base;

import dth.com.yun.presenter.viewImpl.IBaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public abstract class BasePresenter<T extends IBaseView> {

    public T mView;
    private CompositeSubscription mCompositeSubscription;


    public void attach(T mView) {
        this.mView = mView;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    public void unsubcrible() {

        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
