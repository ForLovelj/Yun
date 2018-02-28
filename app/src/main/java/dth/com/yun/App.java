package dth.com.yun;

import android.app.Application;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public class App extends Application{

    public static App mApplication;

    public static App getContext() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
