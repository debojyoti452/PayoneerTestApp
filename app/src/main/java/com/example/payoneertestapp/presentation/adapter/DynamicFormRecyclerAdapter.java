package com.example.payoneertestapp.presentation.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payoneertestapp.databinding.FormInputLayoutBinding;
import com.example.sdk.models.InputElementsItem;
import com.example.sdk.utils.InputRenderer;

import java.util.ArrayList;
import java.util.List;

public class DynamicFormRecyclerAdapter extends RecyclerView.Adapter<DynamicFormRecyclerAdapter.DynamicFormItemView> {

    private final List<InputElementsItem> dataList;

    public DynamicFormRecyclerAdapter() {
        this.dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DynamicFormItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DynamicFormItemView(
                FormInputLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicFormItemView holder, int position) {
        holder.setData(dataList.get(position), (dataList.size() - 1));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<InputElementsItem> freshList) {
        dataList.clear();
        dataList.addAll(freshList);
        notifyDataSetChanged();
    }

    static class DynamicFormItemView extends RecyclerView.ViewHolder {
        private final FormInputLayoutBinding binding;

        public DynamicFormItemView(@NonNull FormInputLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(InputElementsItem item, int size) {
            binding.cardCodeTI.setHint(item.getName());
            binding.cardCodeET.setInputType(InputRenderer.renderInputType(item.getType()));

            if (size == (getAdapterPosition())) {
                binding.cardCodeET.setImeOptions(InputRenderer.renderInputIme(1));
            } else {
                binding.cardCodeET.setImeOptions(InputRenderer.renderInputIme(0));
            }
        }
    }
}
