package com.example.payoneertestapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

public class ActivityLifeCycleHandler implements Application.ActivityLifecycleCallbacks {

    private final LifeCycleListener lifeCycleListener;

    public ActivityLifeCycleHandler(LifeCycleListener lifeCycleListener) {
        this.lifeCycleListener = lifeCycleListener;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        lifeCycleListener.onApplicationStarted();
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        lifeCycleListener.onApplicationResumed();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        lifeCycleListener.onApplicationPaused();
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        lifeCycleListener.onApplicationStopped();
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /** Informs the listener about application lifecycle events. */
    interface LifeCycleListener {
        /** Called right before the application is stopped. */
        void onApplicationStopped();

        /** Called right after the application has been started. */
        void onApplicationStarted();

        /** Called when the application has gone to the background. */
        void onApplicationPaused();

        /** Called when the application has gone to the background. */
        void onApplicationResumed();
    }
}
