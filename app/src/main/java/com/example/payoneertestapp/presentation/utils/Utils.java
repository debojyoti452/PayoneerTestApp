package com.example.payoneertestapp.presentation.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import com.example.payoneertestapp.R;
import com.github.razir.progressbutton.DrawableButton;
import com.github.razir.progressbutton.DrawableButtonExtensionsKt;

import kotlin.Unit;

public class Utils {
    public static void showProgressCenter(final Button button) {
        DrawableButtonExtensionsKt.showProgress(button, progressParams -> {
            progressParams.setProgressColor(Color.WHITE);
            progressParams.setGravity(DrawableButton.GRAVITY_TEXT_START);
            progressParams.setButtonText(button.getContext().getString(R.string.loading));
            return Unit.INSTANCE;
        });
        button.setEnabled(false);
    }

    public static void hideProgressCenter(final Button button) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            button.setEnabled(true);
            DrawableButtonExtensionsKt.hideProgress(button, R.string.find);
        }, 100L);
    }
}
