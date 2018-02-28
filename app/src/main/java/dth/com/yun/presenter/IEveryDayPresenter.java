package dth.com.yun.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import dth.com.yun.ConstantsImageUrl;
import dth.com.yun.base.BasePresenter;
import dth.com.yun.http.ApiService;
import dth.com.yun.http.HttpUtils;
import dth.com.yun.model.AndroidBean;
import dth.com.yun.model.FrontpageBean;
import dth.com.yun.model.GankIoDayBean;
import dth.com.yun.presenter.viewImpl.IEveryDayView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class IEveryDayPresenter extends BasePresenter<IEveryDayView> {

    private int year = 2017;
    private int month = 2;
    private int day = 10;

    private static final String HOME_ONE = "home_one";
    private static final String HOME_TWO = "home_two";
    private static final String HOME_SIX = "home_six";
    private List<List<AndroidBean>> mData;
    private final Calendar mCalendar;


    public IEveryDayPresenter() {
        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH) + 1;
        day = mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 首页轮播图数据
     */
    public void getBanncerPage() {
        Subscription subscribe = HttpUtils.getInstence()
                .getRebuilder()
                .baseUrl(HttpUtils.API_TING)
                .build()
                .create(ApiService.class)
                .getFrontpage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FrontpageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(FrontpageBean frontpageBean) {
                        mView.loadSuccess(frontpageBean);
                        ArrayList<String> urlList = new ArrayList<>();
                        if (frontpageBean.getResult() != null && frontpageBean.getResult().getFocus().getResult() != null) {

                            List<FrontpageBean.ResultEntity.FocusEntity.Entity> result = frontpageBean.getResult().getFocus().getResult();

                            for (FrontpageBean.ResultEntity.FocusEntity.Entity entity : result) {
                                urlList.add(entity.getRandpic());
                            }

                        }

                        mView.showBanncerPage(urlList);
                    }
                });

        addSubscription(subscribe);
    }

    /**
     * recyclerView 内容数据
     */
    public void getRecyclerViewData() {



        Subscription subscribe = HttpUtils.getInstence()
                .getRebuilder()
                .baseUrl(HttpUtils.API_GANKIO)
                .build()
                .create(ApiService.class)
                .getGankIoDay(year+"", month+"", day+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadFailed();
                    }

                    @Override
                    public void onNext(GankIoDayBean gankIoDayBean) {

                        if (gankIoDayBean.getCategory() == null || gankIoDayBean.getCategory().size() == 0) {
                            if (day > 1) {
                                day--;
                            } else if(day == 1){
                                if (month > 1) {
                                    month--;
                                    mCalendar.set(Calendar.MONTH,month);
                                    day = mCalendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
                                } else {
                                    year--;
                                    mCalendar.set(Calendar.YEAR,year);
                                    month = mCalendar.getLeastMaximum(Calendar.MONTH);
                                }

                            }
                            getRecyclerViewData();
                        } else {
                            if (mData == null) {
                                mData = new ArrayList<>();
                            }
                            GankIoDayBean.ResultsEntity results = gankIoDayBean.getResults();

                            List<List<AndroidBean>> lists =  resetData(mData, results);//数据转换最好在新的线程中转换 避免主线程卡顿 这里将就...

                            mView.showContentData(lists);
                        }
                    }
                });

        addSubscription(subscribe);
    }


    public List<List<AndroidBean>> resetData(List<List<AndroidBean>> data , GankIoDayBean.ResultsEntity results) {
        data.clear();

        ArrayList<AndroidBean> list = new ArrayList<>();
        ArrayList<AndroidBean> list2 = new ArrayList<>();
        ArrayList<AndroidBean> list3 = new ArrayList<>();
        ArrayList<AndroidBean> list4 = new ArrayList<>();
        ArrayList<AndroidBean> list5 = new ArrayList<>();
        ArrayList<AndroidBean> list6 = new ArrayList<>();
        ArrayList<AndroidBean> list7 = new ArrayList<>();
        ArrayList<AndroidBean> list8 = new ArrayList<>();

        ArrayList<AndroidBean> title = new ArrayList<>();
        ArrayList<AndroidBean> title2 = new ArrayList<>();
        ArrayList<AndroidBean> title3 = new ArrayList<>();
        ArrayList<AndroidBean> title4 = new ArrayList<>();
        ArrayList<AndroidBean> title5 = new ArrayList<>();
        ArrayList<AndroidBean> title6 = new ArrayList<>();
        ArrayList<AndroidBean> title7 = new ArrayList<>();
        ArrayList<AndroidBean> title8 = new ArrayList<>();

        //android
        List<AndroidBean> android = results.getAndroid();

        if (android != null && android.size() > 0) {

            String type = android.get(0).getType();
            AndroidBean ti = new AndroidBean();
            ti.setType_title(type);
            title.add(ti);
            data.add(title);//android标题

            for (AndroidBean androidEntity : android) {

                setRandomUrl(androidEntity, android.size());

                list.add(androidEntity);
            }
            data.add(list);//android数据
        }

        //福利
        List<AndroidBean> welfare = results.get福利();

        if (welfare != null && welfare.size() > 0) {

            String type2 = welfare.get(0).getType();
            AndroidBean ti2 = new AndroidBean();
            ti2.setType_title(type2);
            title2.add(ti2);
            data.add(title2);

            for (AndroidBean entity : welfare) {

                setRandomUrl(entity, welfare.size());

                list2.add(entity);
            }
            data.add(list2);
        }

        //ios
        List<AndroidBean> ios = results.getIOS();

        if (ios != null && ios.size() > 0) {

            String type3 = ios.get(0).getType();
            AndroidBean ti3 = new AndroidBean();
            ti3.setType_title(type3);
            title3.add(ti3);
            data.add(title3);

            for (AndroidBean iosEntity : ios) {

                setRandomUrl(iosEntity, ios.size());

                list3.add(iosEntity);
            }
            data.add(list3);
        }

        //瞎推荐
        List<AndroidBean> tuijian = results.get瞎推荐();

        if (tuijian != null && tuijian.size() > 0) {

            String type4 = tuijian.get(0).getType();
            AndroidBean ti4 = new AndroidBean();
            ti4.setType_title(type4);
            title4.add(ti4);
            data.add(title4);

            for (AndroidBean tuijianEntity : tuijian) {

                setRandomUrl(tuijianEntity, tuijian.size());

                list4.add(tuijianEntity);
            }
            data.add(list4);
        }

        //休息视频
        List<AndroidBean> video = results.get休息视频();

        if (video != null && video.size() > 0) {

            String type5 = video.get(0).getType();
            AndroidBean ti5 = new AndroidBean();
            ti5.setType_title(type5);
            title5.add(ti5);
            data.add(title5);

            for (AndroidBean videoEntity : video) {

                setRandomUrl(videoEntity,video.size());

                list5.add(videoEntity);
            }
            data.add(list5);
        }

        //前端
        List<AndroidBean> front = results.get前端();

        if (front != null && front.size() > 0) {

            String type6 = front.get(0).getType();
            AndroidBean ti6 = new AndroidBean();
            ti6.setType_title(type6);
            title6.add(ti6);
            data.add(title6);

            for (AndroidBean frontEntity : front) {

                setRandomUrl(frontEntity,front.size());

                list6.add(frontEntity);
            }
            data.add(list6);
        }

        //app
        List<AndroidBean> app = results.getApp();

        if (app != null && app.size() > 0) {

            String type7 = app.get(0).getType();
            AndroidBean ti7 = new AndroidBean();
            ti7.setType_title(type7);
            title7.add(ti7);
            data.add(title7);

            for (AndroidBean appEntity : app) {

                setRandomUrl(appEntity,app.size());

                list7.add(appEntity);
            }
            data.add(list7);
        }

        //拓展资源
        List<AndroidBean> resource = results.get拓展资源();

        if (resource != null && resource.size() > 0) {

            String type8 = resource.get(0).getType();
            AndroidBean ti8 = new AndroidBean();
            ti8.setType_title(type8);
            title8.add(ti8);
            data.add(title8);

            for (AndroidBean resourceEntity : resource) {

                setRandomUrl(resourceEntity,resource.size());

                list8.add(resourceEntity);
            }
            data.add(list8);
        }



        return data;
    }

    private void setRandomUrl(AndroidBean androidBean, int androidSize) {

        // 随机图的url
        if (androidSize == 1) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
        } else if (androidSize == 2) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
        } else if (androidSize == 3) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        }else{
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        }
    }

    /**
     * 取不同的随机图，在每次网络请求时重置
     */
    private int getRandom(int type) {
        int urlLength = 0;
        if (type == 1) {
            urlLength = ConstantsImageUrl.HOME_ONE_URLS.length;
        } else if (type == 2) {
            urlLength = ConstantsImageUrl.HOME_TWO_URLS.length;
        } else if (type == 3) {
            urlLength = ConstantsImageUrl.HOME_SIX_URLS.length;
        }


            Random random = new Random();
            int randomInt = random.nextInt(urlLength);
            return randomInt;
    }
}
