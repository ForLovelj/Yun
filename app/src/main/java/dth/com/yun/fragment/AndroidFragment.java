package dth.com.yun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.AndroidAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.GankIoDataBean;
import dth.com.yun.presenter.IAndroidPresenter;
import dth.com.yun.presenter.viewImpl.IAndroidView;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */
public class AndroidFragment extends BaseFragment<IAndroidPresenter> implements IAndroidView {

    private static final String TYPE = "type";
    @Bind(R.id.xrv_android)
    XRecyclerView mXrvAndroid;
    private String mType = "Android";
    private int    mPage = 1;
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private AndroidAdapter mAndroidAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(TYPE);
        }
    }

    public static AndroidFragment newInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        initListener();

        //准备就绪
        mIsPrepared = true;
    }

    private void initListener() {

        mXrvAndroid.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mAndroidAdapter.clear();
                loadAndroidData();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadAndroidData();
            }
        });
    }

    private void initRecyclerView() {
        mAndroidAdapter = new AndroidAdapter();
        mXrvAndroid.setLayoutManager(new LinearLayoutManager(getContext()));
        mXrvAndroid.setAdapter(mAndroidAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    protected IAndroidPresenter getPresenter() {
        return new IAndroidPresenter();
    }

    @Override
    protected void loadData() {

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        loadAndroidData();
    }

    public void loadAndroidData() {

        mPresenter.getGankIoData(mType, mPage, HttpUtils.per_page);
    }

    @Override
    protected void onRefresh() {
        mPage = 1;
        mAndroidAdapter.clear();
        loadAndroidData();
    }

    @Override
    public void loadSuccess(List<GankIoDataBean.ResultsEntity> results) {
        mIsFirst = false;
        showContentView();
        mXrvAndroid.refreshComplete();
        mAndroidAdapter.addAll(results);

    }

    @Override
    public void loadFailed() {
        showError();
    }


}
