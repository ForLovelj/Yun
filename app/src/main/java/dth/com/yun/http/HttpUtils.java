package dth.com.yun.http;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dth.com.yun.App;
import dth.com.yun.BuildConfig;
import dth.com.yun.utils.DebugUtil;
import dth.com.yun.utils.NetWorkUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/10.
 */

public class HttpUtils {

    private static HttpUtils mHttpUtils;
    // gankio、豆瓣、动听（轮播图）
    public final static String API_GANKIO = "http://gank.io";
    public final static String API_DOUBAN = "https://api.douban.com";
    public final static String API_DONGTING = "http://api.dongting.com";//天天动听首页轮播图失效
    public final static String API_TING = "http://tingapi.ting.baidu.com/";

    public static final String BASE_DOUBAN_URL = "http://www.dbmeinv.com/dbgroup/";//豆瓣妹纸数据
    private Gson gson;
    private Retrofit.Builder mReBuilder;

    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
    public static int per_page_more = 20;

    private HttpUtils() {
        initOkHttpClient();
    }


    private void initOkHttpClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        //如果不是在正式包，添加拦截 打印响应json
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            if (TextUtils.isEmpty(message)) return;
                            String s = message.substring(0, 1);
                            //如果收到想响应是json才打印
                            if ("{".equals(s) || "[".equals(s)) {
                                DebugUtil.debug("收到响应: " + message);
                            }
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(logging);
        }

        File cacheFile = new File(App.getContext().getCacheDir().getAbsolutePath(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);

        okBuilder.cache(cache);
//        okBuilder.readTimeout(20, TimeUnit.SECONDS);
        okBuilder.connectTimeout(10, TimeUnit.SECONDS);
//        okBuilder.writeTimeout(20, TimeUnit.SECONDS);
//        okBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        OkHttpClient client = okBuilder.build();



        mReBuilder = new Retrofit.Builder();
        mReBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());
//        zhihuApi = new Retrofit.Builder()
//                .baseUrl("http://news-at.zhihu.com")
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(ZhihuApi.class);
    }

    public Retrofit.Builder getRebuilder() {
        if (mReBuilder == null) {
            initOkHttpClient();
        }

        return mReBuilder;
    }

    public static HttpUtils getInstence() {
        if (mHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (mHttpUtils == null) {
                    mHttpUtils = new HttpUtils();
                }
            }
        }
        return mHttpUtils;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (BuildConfig.DEBUG) {
                DebugUtil.debug(String.format("发送请求 %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetWorkAvailable(App.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    /*private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }


    public OkHttpClient getUnsafeOkHttpClient() {

        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Create an ssl socket factory with our all-trusting manager
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(20, TimeUnit.SECONDS);
        okBuilder.connectTimeout(10, TimeUnit.SECONDS);
        okBuilder.writeTimeout(20, TimeUnit.SECONDS);
        okBuilder.sslSocketFactory(sslSocketFactory);
        okBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient client = okBuilder.build();
        return client;

    }*/

}
