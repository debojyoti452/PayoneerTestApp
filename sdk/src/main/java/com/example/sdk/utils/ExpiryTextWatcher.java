package com.example.sdk.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @Enum Type => 0 = MONTH
 * @Enum Type => 1 = YEAR
 */
public class ExpiryTextWatcher implements TextWatcher {
    private static final int DEFAULT_YEAR_LENGTH = 4;
    private static final int DEFAULT_MONTH_LENGTH = 2;
    private final EditText editText;
    private final int type;
    private int selection;

    public ExpiryTextWatcher(EditText editText, int type) {
        this.editText = editText;
        this.type = type;
        selection = 0;
        if (type == 0) {
            this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MONTH_LENGTH)});
        } else if (type == 1) {
            this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_YEAR_LENGTH)});
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (type == 0) {
            if (DEFAULT_MONTH_LENGTH < charSequence.length()) {
                selection++;
            }
        } else if (type == 1) {
            if (DEFAULT_YEAR_LENGTH < charSequence.length()) {
                selection++;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String textFormatted = editable.toString();
        if (type == 0) {
            if (DEFAULT_MONTH_LENGTH < editable.length()) {
                this.editText.removeTextChangedListener(this);
                this.editText.setText(textFormatted);
                this.editText.addTextChangedListener(this);
                this.editText.setSelection(selection);
            }
        } else if (type == 1) {
            if (DEFAULT_YEAR_LENGTH < editable.length()) {
                this.editText.removeTextChangedListener(this);
                this.editText.setText(textFormatted);
                this.editText.addTextChangedListener(this);
                this.editText.setSelection(selection);
            }
        }
    }
}
