package dth.com.yun.presenter.viewImpl;

import java.util.List;

import dth.com.yun.model.AndroidBean;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public interface IEveryDayView extends IBaseView {

    void loadSuccess(Object object);

    void loadFailed();

    void showBanncerPage(List<String> urlList);

    void showContentData(List<List<AndroidBean>> lists);


}
