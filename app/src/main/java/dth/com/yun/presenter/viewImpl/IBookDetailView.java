package dth.com.yun.presenter.viewImpl;

import dth.com.yun.model.BookDetailBean;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public interface IBookDetailView extends IBaseView {

    void loadSuccess(BookDetailBean bookDetailBean);

    void loadFailed();
}
