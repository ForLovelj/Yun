package dth.com.yun.presenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.DoubanMeizi;
import dth.com.yun.presenter.viewImpl.IDoubanMeiziView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.type;

/**
 * Created by dth
 * Des:
 * Date: 2017/2/27.
 */

public class IDoubanMeiziPresenter extends BasePresenter<IDoubanMeiziView> {


    /**
     * 获取豆瓣妹纸数据，因为要用jsoup解析，所以接收ResponseBody
     * @param cid
     * @param page
     */
    public void getDouBanMeiZhi(int cid, int page) {
        ApiService apiService = new Retrofit.Builder()
                .baseUrl(HttpUtils.BASE_DOUBAN_URL)
                .client(new OkHttpClient())
                .build()
                .create(ApiService.class);

        Subscription subscribe = HttpUtils.getInstence().getRebuilder()
                .baseUrl(HttpUtils.BASE_DOUBAN_URL)
                .build()
                .create(ApiService.class)
                .getDoubanMeizi(cid, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        List<DoubanMeizi> doubanMeizis = JsoupDoubanMeizi(responseBody);
                        mView.loadSuccess(doubanMeizis);
                    }
                });

        addSubscription(subscribe);

    }

    /**
     * 解析html
     * @param responseBody
     * @return 返回List<DoubanMeizi>数据
     */
    public List<DoubanMeizi>  JsoupDoubanMeizi(ResponseBody responseBody) {

        List<DoubanMeizi> list = new ArrayList<>();
        try {

            String html = responseBody.string();
            Document parse = Jsoup.parse(html);
            Elements elements = parse.select("div[class=thumbnail]>div[class=img_single]>a>img");
            DoubanMeizi meizi;
            for (Element e : elements) {
                String src = e.attr("src");
                String title = e.attr("title");

                meizi = new DoubanMeizi();
                meizi.setUrl(src);
                meizi.setTitle(title);
                meizi.setType(type);

                list.add(meizi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }
}
