package com.example.sdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class InputRenderer {
    public static Integer renderInputType(String type) {
        switch (type) {
            case "numeric": {
                return 0x00000002;
            }

            case "integer": {
                return 0x00001002;
            }

            case "string": {
                return 0x00002001;
            }

            default: {
                return 0x00000000;
            }
        }
    }

    public static Integer renderInputIme(int type) {
        switch (type) {
            case 0: {
                return 0x00000005;
            }

            case 1: {
                return 0x00000006;
            }

            default: {
                return 0x00000000;
            }
        }
    }

    public static TextWatcher renderCardView(String type, EditText editText) {
        switch (type) {
            case "number": {
                return new CreditCardNumberTextWatcher(editText);
            }

            case "expiryMonth": {
                return new ExpiryTextWatcher(editText, 0);
            }

            case "expiryYear": {
                return new ExpiryTextWatcher(editText, 1);
            }

            case "verificationCode": {
                return new VerificationCodeWatcher(editText);
            }

            default: {
                return new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };
            }
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawableByName(Context context, String resourceName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(resourceName, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }
}
