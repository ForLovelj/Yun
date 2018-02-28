package dth.com.yun.presenter.viewImpl;

import dth.com.yun.model.BookBean;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */

public interface IBookCustomView extends IBaseView {

    void loadSuccess(BookBean bookBean);

    void loadFailed();
}
