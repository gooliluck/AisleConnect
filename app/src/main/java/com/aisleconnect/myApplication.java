package com.aisleconnect;

import android.app.Application;
import android.util.Log;

/**
 * Created by jp on 2017/8/7.
 */

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalData.getInstance();
        Log.e("myApplication","running");
    }
}
