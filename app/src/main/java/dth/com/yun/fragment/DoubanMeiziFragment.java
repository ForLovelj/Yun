package dth.com.yun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.activity.DoubanMeiziDetailActivity;
import dth.com.yun.adapter.DoubanMeiziAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.http.rx.RxBus;
import dth.com.yun.model.DoubanMeizi;
import dth.com.yun.presenter.IDoubanMeiziPresenter;
import dth.com.yun.presenter.viewImpl.IDoubanMeiziView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by dth
 * Des:豆瓣妹子列表界面
 * Date: 2017/2/27.
 */

public class DoubanMeiziFragment extends BaseFragment<IDoubanMeiziPresenter> implements IDoubanMeiziView {


    @Bind(R.id.xrv_doubanmeizhi)
    XRecyclerView mXrvDoubanmeizhi;

    public static final String EXTRA_CID = "extra_cid";

    public static final String EXTRA_TYPE = "extra_type";
    private int mCid;
    private int pageNum = 20;

    private int page = 1;
    private DoubanMeiziAdapter mDoubanMeiziAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    private int imageIndex;
    private int mType;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCid = bundle.getInt(EXTRA_CID);
            mType = bundle.getInt(EXTRA_TYPE);
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();

        Subscription subscribe = RxBus.getDefault()
                .toObservable(Intent.class)
                .subscribe(new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        imageIndex = intent.getIntExtra("index", -1);
                        //缓存了6个Fragment 发一次消息执行了6次call
                        Log.d("aa", "call: "+imageIndex);
                        scrollIndex();
                        mDoubanMeiziAdapter.notifyDataSetChanged();

                    }
                });

        RxBus.getDefault().toObservable(mType,Intent.class)
                .compose(this.<Intent>bindToLifecycle())
                .subscribe(new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        //根据每个Fragment的type来接收消息  保证只执行一次
                        imageIndex = intent.getIntExtra("index", -1);
                        Log.d("aa", "call----: "+imageIndex);
                        scrollIndex();
                        if (page * pageNum - pageNum - 1 > 0) {
                            mDoubanMeiziAdapter.notifyItemRangeChanged(page * pageNum - pageNum - 1, pageNum);
                        } else {
                            mDoubanMeiziAdapter.notifyDataSetChanged();
                        }
                    }
                });


        mPresenter.addSubscription(subscribe);

        mIsPrepared = true;

        loadData();
    }

    private void initRecyclerView() {
        mDoubanMeiziAdapter = new DoubanMeiziAdapter();
        mXrvDoubanmeizhi.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mXrvDoubanmeizhi.setLayoutManager(mLayoutManager);
        mXrvDoubanmeizhi.setAdapter(mDoubanMeiziAdapter);

        mXrvDoubanmeizhi.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mDoubanMeiziAdapter.clear();
                loadMeiZhiData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadMeiZhiData();
            }
        });

        mDoubanMeiziAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<DoubanMeizi>() {
            @Override
            public void onClick(BaseRecyclerViewHolder holder, DoubanMeizi doubanMeizi, int position) {

                DoubanMeiziDetailActivity.start(getActivity(),mDoubanMeiziAdapter.getData(),
                        mDoubanMeiziAdapter.getData().get(position).getUrl(),
                        position,mType, (ImageView) holder.itemView.findViewById(R.id.iv_meizhi));

//                TestActivity.start(getActivity(),mDoubanMeiziAdapter.getData(),
//                        mDoubanMeiziAdapter.getData().get(position).getUrl(),
//                        position, (ImageView) holder.itemView.findViewById(R.id.iv_meizhi));

            }
        });


        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                Log.d("aa", "onMapSharedElements: ------Exit");
                String newTransitionName = mDoubanMeiziAdapter.getData().get(imageIndex).getUrl();
                View newSharedView = mXrvDoubanmeizhi.findViewWithTag(newTransitionName);
                if (newSharedView != null) {
                    names.clear();
                    names.add(newTransitionName);
                    sharedElements.clear();
                    sharedElements.put(newTransitionName, newSharedView);
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_douban_meizhi;
    }

    @Override
    protected IDoubanMeiziPresenter getPresenter() {
        return new IDoubanMeiziPresenter();
    }

    @Override
    protected void loadData() {

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        mPresenter.getDouBanMeiZhi(mCid,page);
    }

    private void loadMeiZhiData() {
        mPresenter.getDouBanMeiZhi(mCid,page);
    }

    public static DoubanMeiziFragment newInstance(int cid, int type) {

        DoubanMeiziFragment fragment = new DoubanMeiziFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CID, cid);
        bundle.putInt(EXTRA_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void loadSuccess(List<DoubanMeizi> results) {
        mIsFirst = false;
        mXrvDoubanmeizhi.refreshComplete();
        showContentView();
        mDoubanMeiziAdapter.addAll(results);


    }

    @Override
    public void loadFailed() {
        showError();
    }

    @Override
    protected void onRefresh() {
        page = 1;
        mDoubanMeiziAdapter.clear();
        loadMeiZhiData();
    }

    /**
     * 滚动到指定position
     *
     */
    public void scrollIndex() {


        if (imageIndex != -1 && mXrvDoubanmeizhi != null) {
//            mXrvDoubanmeizhi.scrollToPosition(imageIndex);
            mLayoutManager.scrollToPositionWithOffset(imageIndex,0);
            mXrvDoubanmeizhi.getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {

                            mXrvDoubanmeizhi.getViewTreeObserver().removeOnPreDrawListener(this);
                            mXrvDoubanmeizhi.requestLayout();

                            return true;
                        }
                    });
        }
    }

}
