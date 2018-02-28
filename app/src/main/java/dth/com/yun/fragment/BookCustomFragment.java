package dth.com.yun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.example.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import dth.com.yun.R;
import dth.com.yun.activity.BookDetailActivity;
import dth.com.yun.adapter.BookAdapter;
import dth.com.yun.base.BaseFragment;
import dth.com.yun.base.BaseRecyclerViewAdapter;
import dth.com.yun.base.BaseRecyclerViewHolder;
import dth.com.yun.model.BookBean;
import dth.com.yun.presenter.IBookCustomPresenter;
import dth.com.yun.presenter.viewImpl.IBookCustomView;

/**
 * Created by dth
 * Des:
 * : Date: 2017/2/20.
 */
public class BookCustomFragment extends BaseFragment<IBookCustomPresenter> implements IBookCustomView{

    private static final String TYPE = "type";
    @Bind(R.id.xrv_book)
    XRecyclerView mXrvBook;
    private String mType;
    private int mTotal;
    private int mStart = 0;
    private int mCount = 15;
    private BookAdapter mBookAdapter;
    private boolean mIsPrepared =false;
    private boolean mIsFirst = true;

    public static BookCustomFragment newInstance(String type) {
        BookCustomFragment fragment = new BookCustomFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();

        mIsPrepared = true;

        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRecyclerView() {

        mBookAdapter = new BookAdapter(getActivity());
        mXrvBook.setLayoutManager(new GridLayoutManager(getContext(),3));
        mXrvBook.setAdapter(mBookAdapter);

        mXrvBook.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mBookAdapter.clear();
                mStart = 0;
                loadBookCustomData();
            }

            @Override
            public void onLoadMore() {
                if (mStart >= mTotal) {
                    mXrvBook.setLoadingMoreEnabled(false);
                    return;
                }
                mStart += mCount;
                loadBookCustomData();
            }
        });

        mBookAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<BookBean.BooksEntity>() {
            @Override
            public void onClick(BaseRecyclerViewHolder holder, BookBean.BooksEntity booksEntity, int position) {
                BookDetailActivity.start(getActivity(), booksEntity, (ImageView) holder.itemView.findViewById(R.id.iv_top_photo));
            }
        });
    }

    /**
     * 懒加载数据  只有第一次可见才加载
     */
    @Override
    protected void loadData() {

        if (!mIsVisible || !mIsPrepared || !mIsFirst) {
            return;
        }

        loadBookCustomData();
    }

    private void loadBookCustomData() {
        mPresenter.getCustomData(mType,mStart,mCount);
    }





    @Override
    protected int setLayoutId() {
        return R.layout.fragment_book_custom;
    }

    @Override
    protected IBookCustomPresenter getPresenter() {
        return new IBookCustomPresenter();
    }

    @Override
    public void loadSuccess(BookBean bookBean) {
        mIsFirst = false;
        showContentView();
        mXrvBook.refreshComplete();
        mTotal = bookBean.getTotal();

        mBookAdapter.addAll(bookBean.getBooks());

    }

    @Override
    protected void onRefresh() {
        mStart = 0;
        mBookAdapter.clear();
        loadBookCustomData();
    }

    @Override
    public void loadFailed() {
        showError();
    }
}
