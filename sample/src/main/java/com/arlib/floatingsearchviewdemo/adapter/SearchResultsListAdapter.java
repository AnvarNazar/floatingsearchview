package com.arlib.floatingsearchviewdemo.adapter;

/**
 * Copyright (C) 2015 Ari C.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;
import com.arlib.floatingsearchviewdemo.R;
import com.arlib.floatingsearchviewdemo.data.ColorWrapper;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.SearchViewHolder> {

    private List<ColorWrapper> mDataSet = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    private OnItemClickListener mItemsOnClickListener;

    public SearchResultsListAdapter(OnItemClickListener mItemsOnClickListener) {
        this.mItemsOnClickListener = mItemsOnClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_list_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ColorWrapper colorSuggestion = mDataSet.get(position);
        holder.mColorName.setText(colorSuggestion.getName());
        holder.mColorValue.setText(colorSuggestion.getHex());

        int color = Color.parseColor(colorSuggestion.getHex());
        holder.mColorName.setTextColor(color);
        holder.mColorValue.setTextColor(color);

        if (mLastAnimatedItemPosition < position) {
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if (mItemsOnClickListener != null) {
            holder.itemView.setOnClickListener((View v) -> {
                mItemsOnClickListener.onClick(mDataSet.get(position));
            });
        }
    }

    public void swapData(List<ColorWrapper> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(ColorWrapper colorWrapper);
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        final TextView mColorName;
        final TextView mColorValue;
        final View mTextContainer;

        SearchViewHolder(View view) {
            super(view);
            mColorName = view.findViewById(R.id.color_name);
            mColorValue = view.findViewById(R.id.color_value);
            mTextContainer = view.findViewById(R.id.text_container);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }
}
