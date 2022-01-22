package com.example.sdk.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class VerificationCodeWatcher implements TextWatcher {
    private static final int DEFAULT_MAX_LENGTH = 3;
    private final EditText editText;
    private int selection = 0;

    public VerificationCodeWatcher(EditText editText) {
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MAX_LENGTH)});
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (DEFAULT_MAX_LENGTH < charSequence.length()) {
            selection++;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String textFormatted = editable.toString();
        if (DEFAULT_MAX_LENGTH < editable.length()) {
            this.editText.removeTextChangedListener(this);
            this.editText.setText(textFormatted);
            this.editText.addTextChangedListener(this);
            this.editText.setSelection(selection);
        }
    }
}
