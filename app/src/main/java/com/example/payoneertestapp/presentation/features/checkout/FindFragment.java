package com.example.payoneertestapp.presentation.features.checkout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.payoneertestapp.R;
import com.example.payoneertestapp.databinding.FindFragmentBinding;
import com.example.payoneertestapp.presentation.base.AbstractBaseFragment;
import com.example.payoneertestapp.presentation.features.viewmodel.CheckoutViewModel;
import com.example.payoneertestapp.presentation.utils.Utils;

public class FindFragment extends AbstractBaseFragment<FindFragmentBinding> implements View.OnClickListener {

    private CheckoutViewModel checkoutViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
        getBinding().findBtn.setOnClickListener(this);

        getBinding().codeInputLayout.cardCodeET
                .setOnEditorActionListener((textView, i, keyEvent) -> {
                    boolean opTapped = false;
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        redirectToSearch();
                        opTapped = true;
                    }
                    return opTapped;
                });


    }

    @Override
    public void setVMObserver() {
        checkoutViewModel.getLiveApplicableData().observe(getViewLifecycleOwner(), response -> {
            if (response.first == null) {
                populate(true, response.second);
            } else {
                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_findFragment_to_formFragment);
            }
            Utils.hideProgressCenter(getBinding().findBtn);
        });
    }

    @Override
    public FindFragmentBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return FindFragmentBinding.inflate(layoutInflater);
    }

    @Override
    public void onClick(View view) {
        redirectToSearch();
    }

    private void redirectToSearch() {
        String code = getBinding().codeInputLayout.cardCodeET.getText().toString().trim();

        if (!TextUtils.isEmpty(code)) {
            populate(false, "");
            Utils.showProgressCenter(getBinding().findBtn);
            checkoutViewModel.openSDK(code);
        } else {
            populate(true, getString(R.string.field_empty));
        }
    }

    private void populate(Boolean isError, String message) {
        getBinding().codeInputLayout.cardCodeTI.setError(message);
        getBinding().codeInputLayout.cardCodeTI.setErrorEnabled(isError);
    }
}
