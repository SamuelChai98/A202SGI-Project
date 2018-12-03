package com.inti.student.cricfeed;

import android.app.Application;
import android.os.SystemClock;

public class SplashDuration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //set splash screen duration
        SystemClock.sleep(2000);
    }
}
