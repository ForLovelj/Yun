package dth.com.yun.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.adapter.AndroidAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.GankIoDataBean;
import dth.com.yun.presenter.ICustomPresenter;
import dth.com.yun.presenter.viewImpl.ICustomView;
import dth.com.yun.utils.SPUtils;

/**
 * Created by dth.
 * Des: 干货定制页面
 * Date: 2017/2/10.
 */
public class CustomFragment extends BaseFragment<ICustomPresenter> implements ICustomView {

    @Bind(R.id.xrv_custom)
    XRecyclerView mXrvCustom;
    private String mType = "all";
    private int    mPage = 1;
    private AndroidAdapter mAndroidAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected ICustomPresenter getPresenter() {
        return new ICustomPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        initListener();

        //准备完成
        mIsPrepared = true;
    }

    private void initListener() {
        mXrvCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mAndroidAdapter.clear();
                loadDataCustom();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadDataCustom();
            }
        });
    }

    @Override
    protected void loadData() {

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        mPresenter.getGankIoData(mType,mPage, HttpUtils.per_page);
    }

    protected void loadDataCustom() {

        mPresenter.getGankIoData(mType,mPage,HttpUtils.per_page);
    }

    private void changeContent(TextView textView, String content) {
        textView.setText(content);
        mType = content;
        mPage = 1;
        mAndroidAdapter.clear();
        SPUtils.putString("gank_cala", content);
        showLoading();
        loadDataCustom();
    }


    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString("gank_cala", "全部");
        if (clickText.equals(selectType)) {
            Toast.makeText(mContext, "当前已经是" + selectType + "分类", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void initRecyclerView() {
        mAndroidAdapter = new AndroidAdapter();
        String type = SPUtils.getString("gank_cala", "全部");
        mAndroidAdapter.setAll("全部".equals(type));

        View headerView = View.inflate(mContext, R.layout.header_item_gank_custom, null);
        mXrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXrvCustom.setAdapter(mAndroidAdapter);
        final TextView txName = (TextView) headerView.findViewById(R.id.tx_name);
        txName.setText(type);
        View view = headerView.findViewById(R.id.ll_choose_catalogue);
        mXrvCustom.addHeaderView(headerView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                        .sheet(R.menu.gank_bottomsheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case R.id.gank_all:
                                        if (isOtherType("全部")) {
                                            txName.setText("全部");
                                            mType = "all";// 全部传 all
                                            mPage = 1;
                                            mAndroidAdapter.clear();
                                            mAndroidAdapter.setAll(true);
                                            SPUtils.putString("gank_cala", "全部");
                                            showLoading();
                                            loadDataCustom();
                                        }
                                        break;
                                    case R.id.gank_ios:
                                        if (isOtherType("IOS")) {
                                            txName.setText("IOS");
                                            mType = "iOS";// 这里有严格大小写
                                            mPage = 1;
                                            mAndroidAdapter.clear();
                                            mAndroidAdapter.setAll(false);
                                            SPUtils.putString("gank_cala", "IOS");
                                            showLoading();
                                            loadDataCustom();
                                        }
                                        break;
                                    case R.id.gank_qian:
                                        if (isOtherType("前端")) {
                                            mAndroidAdapter.setAll(false);
                                            changeContent(txName, "前端");
                                        }
                                        break;
                                    case R.id.gank_app:
                                        if (isOtherType("App")) {
                                            mAndroidAdapter.setAll(false);
                                            changeContent(txName, "App");
                                        }
                                        break;
                                    case R.id.gank_movie:
                                        if (isOtherType("休息视频")) {

                                            mAndroidAdapter.setAll(false);
                                            changeContent(txName, "休息视频");
                                        }
                                        break;
                                    case R.id.gank_resouce:
                                        if (isOtherType("拓展资源")) {
                                            mAndroidAdapter.setAll(false);
                                            changeContent(txName, "拓展资源");
                                        }
                                        break;
                                }

                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onRefresh() {
        mPage = 1;
        mAndroidAdapter.clear();
        loadDataCustom();
    }

    @Override
    public void loadSuccess(List<GankIoDataBean.ResultsEntity> results) {
        mIsFirst = false;
        showContentView();
        mXrvCustom.refreshComplete();
        mAndroidAdapter.addAll(results);
    }

    @Override
    public void loadFailed() {
        showError();
    }



}
