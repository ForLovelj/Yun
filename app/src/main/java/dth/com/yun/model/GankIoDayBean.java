package dth.com.yun.model;

import java.util.List;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class GankIoDayBean {


    /**
     * category : ["休息视频","瞎推荐","iOS","Android","福利"]
     * error : false
     * results : {"Android":[{"_id":"589d2bcd421aa9270bc7332c","createdAt":"2017-02-10T10:56:13.792Z","desc":"Android 信用卡提交效果。","images":["http://img.gank.io/0df0d67f-6d39-4880-9a44-b2531ccb3a75"],"publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"Android","url":"https://github.com/adonixis/android-sumbit-credit-card-flow","used":true,"who":"代码家"},{"_id":"589d2bed421aa92710db9613","createdAt":"2017-02-10T10:56:45.622Z","desc":"简洁优雅的网络状态提示。","images":["http://img.gank.io/93211cbf-d4af-4bc4-ba76-621dfb1dfe40"],"publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"Android","url":"https://github.com/iammert/StatusView","used":true,"who":"代码家"}],"iOS":[{"_id":"589d2af5421aa9270bc7332b","createdAt":"2017-02-10T10:52:37.898Z","desc":"Swift 发送邮件 📧","images":["http://img.gank.io/41577a74-2e4b-4f20-9cee-5f5d3a2a2ce4"],"publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"iOS","url":"https://github.com/onevcat/Hedwig","used":true,"who":"代码家"},{"_id":"589d2b9f421aa92710db9612","createdAt":"2017-02-10T10:55:27.144Z","desc":"弹出一个醒目而优雅的通知框。","publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"iOS","url":"https://github.com/candostdagdeviren/CDAlertView","used":true,"who":"代码家"}],"休息视频":[{"_id":"58974706421aa970bed462c2","createdAt":"2017-02-05T23:38:46.879Z","desc":"不要给我讲理，我就是道理...也是说出了万千女生的心声...哈哈哈~真理都在女人嘴里，无法反驳...[笑cry][笑cry]","publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"休息视频","url":"http://weibo.com/tv/v/cf39d9a371f7329cb7736bd1b16293de?fid=1034:cf39d9a371f7329cb7736bd1b16293de","used":true,"who":"lxxself"}],"瞎推荐":[{"_id":"589c0e76421aa92dbe257232","createdAt":"2017-02-09T14:38:46.687Z","desc":"阿里巴巴Java开发手册","publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"瞎推荐","url":"http://deadlion.cn/2017/02/05/Alibaba-Java-Code-Style.html","used":true,"who":"wuzheng"}],"福利":[{"_id":"589d31a2421aa9270bc7332e","createdAt":"2017-02-10T11:21:06.747Z","desc":"2-10","publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-10-16465759_171779496648995_128281069584646144_n.jpg","used":true,"who":"代码家"}]}
     */

    private boolean error;
    private ResultsEntity results;
    private List<String>  category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsEntity getResults() {
        return results;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsEntity {
        /**
         * _id : 589d2bcd421aa9270bc7332c
         * createdAt : 2017-02-10T10:56:13.792Z
         * desc : Android 信用卡提交效果。
         * images : ["http://img.gank.io/0df0d67f-6d39-4880-9a44-b2531ccb3a75"]
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : Android
         * url : https://github.com/adonixis/android-sumbit-credit-card-flow
         * used : true
         * who : 代码家
         */

        private List<AndroidBean> Android;
        /**
         * _id : 589d2af5421aa9270bc7332b
         * createdAt : 2017-02-10T10:52:37.898Z
         * desc : Swift 发送邮件 📧
         * images : ["http://img.gank.io/41577a74-2e4b-4f20-9cee-5f5d3a2a2ce4"]
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : iOS
         * url : https://github.com/onevcat/Hedwig
         * used : true
         * who : 代码家
         */

        private List<AndroidBean>     iOS;
        /**
         * _id : 58974706421aa970bed462c2
         * createdAt : 2017-02-05T23:38:46.879Z
         * desc : 不要给我讲理，我就是道理...也是说出了万千女生的心声...哈哈哈~真理都在女人嘴里，无法反驳...[笑cry][笑cry]
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : 休息视频
         * url : http://weibo.com/tv/v/cf39d9a371f7329cb7736bd1b16293de?fid=1034:cf39d9a371f7329cb7736bd1b16293de
         * used : true
         * who : lxxself
         */

        private List<AndroidBean>    休息视频;
        /**
         * _id : 589c0e76421aa92dbe257232
         * createdAt : 2017-02-09T14:38:46.687Z
         * desc : 阿里巴巴Java开发手册
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : 瞎推荐
         * url : http://deadlion.cn/2017/02/05/Alibaba-Java-Code-Style.html
         * used : true
         * who : wuzheng
         */

        private List<AndroidBean>     瞎推荐;
        /**
         * _id : 589d31a2421aa9270bc7332e
         * createdAt : 2017-02-10T11:21:06.747Z
         * desc : 2-10
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-10-16465759_171779496648995_128281069584646144_n.jpg
         * used : true
         * who : 代码家
         */

        private List<AndroidBean>      福利;

        private List<AndroidBean> 前端;

        private List<AndroidBean> App;

        private List<AndroidBean> 拓展资源;


        public List<AndroidBean> get拓展资源() {
            return 拓展资源;
        }

        public void set拓展资源(List<AndroidBean> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }


        public List<AndroidBean> get前端() {
            return 前端;
        }

        public void set前端(List<AndroidBean> 前端) {
            this.前端 = 前端;
        }

        public List<AndroidBean> getApp() {
            return App;
        }

        public void setApp(List<AndroidBean> app) {
            App = app;
        }



        public List<AndroidBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<AndroidBean> Android) {
            this.Android = Android;
        }

        public List<AndroidBean> getIOS() {
            return iOS;
        }

        public void setIOS(List<AndroidBean> iOS) {
            this.iOS = iOS;
        }

        public List<AndroidBean> get休息视频() {
            return 休息视频;
        }

        public void set休息视频(List<AndroidBean> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public List<AndroidBean> get瞎推荐() {
            return 瞎推荐;
        }

        public void set瞎推荐(List<AndroidBean> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public List<AndroidBean> get福利() {
            return 福利;
        }

        public void set福利(List<AndroidBean> 福利) {
            this.福利 = 福利;
        }


    }
}
