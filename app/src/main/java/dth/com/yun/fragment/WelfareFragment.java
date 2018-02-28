package dth.com.yun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.activity.ImageActivity;
import dth.com.yun.adapter.WelfareAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.GankIoDataBean;
import dth.com.yun.presenter.IWelfarePresenter;
import dth.com.yun.presenter.viewImpl.IWelfareView;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */
public class WelfareFragment extends BaseFragment<IWelfarePresenter> implements IWelfareView{

    @Bind(R.id.xrv_welfare)
    XRecyclerView mXrvWelfare;
    private WelfareAdapter mWelfareAdapter;
    private int mPage = 1;
    private String mTpye = "福利";
    private boolean mIsPrepared =false;
    private boolean mIsFirst = true;
    ArrayList<String> imgList = new ArrayList<>();//保存图片url 传递给图片activity
    //    List<GankIoDataBean.ResultsEntity> mData = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected IWelfarePresenter getPresenter() {
        return new IWelfarePresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWelfareAdapter = new WelfareAdapter(getContext());
//        mXrvWelfare.setLayoutManager(new LinearLayoutManager(getContext()));
//        mXrvWelfare.setLayoutManager(new GridLayoutManager(getContext(),2));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mXrvWelfare.setLayoutManager(layoutManager);
        mXrvWelfare.setAdapter(mWelfareAdapter);

        initListener();
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        mPresenter.getGankIoData(mTpye,mPage, HttpUtils.per_page);

    }

    /**
     * 加载跟多时的方法
     */
    public void LoadWelfareData() {
        mPresenter.getGankIoData(mTpye,mPage, HttpUtils.per_page);
    }

    private void initListener() {
        mXrvWelfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mWelfareAdapter.clear();
                imgList.clear();
                mPage = 1;
                LoadWelfareData();
            }

            @Override
            public void onLoadMore() {
                mPage++;
//                if (mPage > 5) {//模拟总页数
//                    mXrvWelfare.noMoreLoading();
//                    return;
//                }
                LoadWelfareData();

            }
        });

        mWelfareAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<GankIoDataBean.ResultsEntity>() {
            @Override
            public void onClick(BaseRecyclerViewHolder holder, GankIoDataBean.ResultsEntity resultsEntity, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putStringArrayList("imageuri", imgList);
                Intent intent = new Intent(getContext(), ImageActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);

            }
        });
    }


    @Override
    public void loadSuccess(List<GankIoDataBean.ResultsEntity> results) {
        mIsFirst = false;
        mXrvWelfare.refreshComplete();
        showContentView();
        mWelfareAdapter.addAll(results);

        for (GankIoDataBean.ResultsEntity result : results) {
            imgList.add(result.getUrl());
        }
//        mData.addAll(results);
//        mWelfareAdapter.setData(mData);
    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    protected void onRefresh() {
        mPage = 1;
        mWelfareAdapter.clear();
        imgList.clear();
        LoadWelfareData();
    }
}
