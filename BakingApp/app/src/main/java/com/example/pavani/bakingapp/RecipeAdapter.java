package com.example.pavani.bakingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pavani on 4/6/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    Context mContext;
    OnItemClickListener mItemClickListener;
    private ArrayList<Recipies> mRecipies;


    public RecipeAdapter(Context context, ArrayList<Recipies> list) {
        this.mContext = context;
        this.mRecipies = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipies_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.ViewHolder holder, int position) {
        final Recipies recipe = mRecipies.get(position);
        holder.recipeName.setText(recipe.name);
    }

    @Override
    public int getItemCount() {
        return mRecipies.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipeName)
        public TextView recipeName;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }

        }
    }


}

