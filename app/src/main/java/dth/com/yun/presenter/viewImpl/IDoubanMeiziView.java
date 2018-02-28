package dth.com.yun.presenter.viewImpl;

import java.util.List;

import dth.com.yun.model.DoubanMeizi;

/**
 * Created by dth
 * Des:
 * Date: 2017/2/27.
 */

public interface IDoubanMeiziView extends IBaseView {

    void loadSuccess(List<DoubanMeizi> results);

    void loadFailed();
}
