package dth.com.yun.presenter.viewImpl;

import java.util.List;

import dth.com.yun.model.GankIoDataBean;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public interface IWelfareView extends IBaseView {

    void loadSuccess(List<GankIoDataBean.ResultsEntity> results);

    void loadFailed();
}
