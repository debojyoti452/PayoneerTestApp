package com.example.payoneertestapp.presentation.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.payoneertestapp.presentation.features.checkout.CheckoutActivity;
import com.example.payoneertestapp.presentation.features.viewmodel.CheckoutViewModel;

public abstract class AbstractBaseFragment<T extends ViewBinding> extends Fragment {

    private T binding = null;

    public T getBinding() {
        return binding;
    }

    public abstract void setVMObserver();

    protected abstract T inflateViewBinding(LayoutInflater layoutInflater, ViewGroup viewGroup);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                setVMObserver();
            }
        }, 100L);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void showLog(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }

    protected void showToast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void goBackToFindCodePage() {
        requireActivity().startActivity(
                new Intent(requireContext(), CheckoutActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        );
        requireActivity().finish();
    }
}
