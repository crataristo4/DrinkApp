package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;
import com.hard.code.tech.drinkapp.databinding.LayoutFavoriteItemsBinding;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    private List<Favorite> favoriteList;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutFavoriteItemsBinding layoutFavoriteItemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_favorite_items,
                parent,
                false
        );

        return new FavoriteViewHolder(layoutFavoriteItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        Favorite favorite = favoriteList.get(position);

        //bind data to items
        holder.layoutFavoriteItemsBinding.setFavoriteItem(favorite);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return favoriteList == null ? 0 : favoriteList.size();
    }

    //deletes item from the list
    public void removeItem(int position) {
        favoriteList.remove(position);
        notifyItemRemoved(position);
    }

    //restores item removed
    public void onRestoreItem(Favorite favoriteItem, int position) {

        favoriteList.add(position, favoriteItem);
        notifyItemInserted(position);

    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout viewBackground;
        public MaterialCardView viewForeground;
        LayoutFavoriteItemsBinding layoutFavoriteItemsBinding;



        FavoriteViewHolder(@NonNull LayoutFavoriteItemsBinding layoutFavoriteItemsBinding) {

            super(layoutFavoriteItemsBinding.getRoot());
            this.layoutFavoriteItemsBinding = layoutFavoriteItemsBinding;
            viewBackground = layoutFavoriteItemsBinding.viewBackground;
            viewForeground = layoutFavoriteItemsBinding.viewForeground;

        }
    }
}
