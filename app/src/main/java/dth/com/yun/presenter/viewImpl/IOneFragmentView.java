package dth.com.yun.presenter.viewImpl;

import java.util.List;

import dth.com.yun.model.HotMovieBean;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public interface IOneFragmentView extends IBaseView {

    void loadSuccess(List<HotMovieBean.SubjectsEntity> subjects);

    void loadFailed();
}
