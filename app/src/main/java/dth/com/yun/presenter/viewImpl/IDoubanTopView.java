package dth.com.yun.presenter.viewImpl;

import java.util.List;

import dth.com.yun.model.HotMovieBean;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface IDoubanTopView extends IBaseView {

    void loadSuccess(List<HotMovieBean.SubjectsEntity> subjects);

    void loadFailed();
}
