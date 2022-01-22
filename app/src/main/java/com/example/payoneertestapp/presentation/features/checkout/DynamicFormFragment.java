package com.example.payoneertestapp.presentation.features.checkout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.payoneertestapp.databinding.FormFragmentBinding;
import com.example.payoneertestapp.presentation.adapter.DynamicFormRecyclerAdapter;
import com.example.payoneertestapp.presentation.base.AbstractBaseFragment;
import com.example.payoneertestapp.presentation.features.viewmodel.CheckoutViewModel;

public class DynamicFormFragment extends AbstractBaseFragment<FormFragmentBinding> {

    private CheckoutViewModel checkoutViewModel;
    private DynamicFormRecyclerAdapter formRecyclerAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
        formRecyclerAdapter = new DynamicFormRecyclerAdapter();
        getBinding().dynamicRVList.setLayoutManager(new LinearLayoutManager(requireContext()));
        getBinding().dynamicRVList.setAdapter(formRecyclerAdapter);
    }

    @Override
    public void setVMObserver() {
        checkoutViewModel.getLiveApplicableData().observe(getViewLifecycleOwner(), response -> {
            if (!response.first.getInputElements().isEmpty()) {
                formRecyclerAdapter.addData(response.first.getInputElements());
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    getBinding().dynamicListLoader.setVisibility(View.INVISIBLE);
                    getBinding().dynamicRVCard.setVisibility(View.VISIBLE);
                }, 1000L);

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        goBackToFindCodePage();
                    }
                });
    }

    @Override
    public FormFragmentBinding inflateViewBinding(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return FormFragmentBinding.inflate(layoutInflater);
    }
}
