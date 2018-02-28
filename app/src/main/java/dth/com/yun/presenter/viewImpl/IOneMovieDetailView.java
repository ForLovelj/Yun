package dth.com.yun.presenter.viewImpl;

import dth.com.yun.model.MovieDetailBean;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/17.
 */

public interface IOneMovieDetailView extends IBaseView {

    void loadSuccess(MovieDetailBean movieDetailBean);

    void loadFailed();
}
