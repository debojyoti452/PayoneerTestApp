package com.example.payoneertestapp;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication implements ActivityLifeCycleHandler.LifeCycleListener {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifeCycleHandler(this));
    }

    @Override
    public void onApplicationStopped() {

    }

    @Override
    public void onApplicationStarted() {

    }

    @Override
    public void onApplicationPaused() {

    }

    @Override
    public void onApplicationResumed() {

    }
}
