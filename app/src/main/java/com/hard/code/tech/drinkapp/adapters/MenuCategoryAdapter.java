package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.activities.DrinksByMenuIdActivity;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.databinding.LayoutMenuItemsBinding;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.utils.Utils;

import java.util.List;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder> {

    private List<MenuCategory> menuCategoryList;
    private Context context;

    public MenuCategoryAdapter(List<MenuCategory> menuCategoryList, Context context) {
        this.menuCategoryList = menuCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutMenuItemsBinding layoutMenuItemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_menu_items, parent, false);

        return new MenuCategoryViewHolder(layoutMenuItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCategoryViewHolder holder, int position) {

        MenuCategory menuCategory = menuCategoryList.get(position);

        holder.layoutMenuItemsBinding.setMenuCategory(menuCategory);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawableColor());
        requestOptions.error(Utils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(menuCategory.getLink())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.layoutMenuItemsBinding.progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.layoutMenuItemsBinding.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())

                .into(holder.layoutMenuItemsBinding.categoryImage);


        holder.layoutMenuItemsBinding.getRoot().setOnClickListener((view) -> {
            RetrofitClient.menuCategory = menuCategoryList.get(position);
            context.startActivity(new Intent(context, DrinksByMenuIdActivity.class));
        });


    }

    @Override
    public int getItemCount() {
        return menuCategoryList == null ? 0 : menuCategoryList.size();
    }


    static class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        private LayoutMenuItemsBinding layoutMenuItemsBinding;

        MenuCategoryViewHolder(@NonNull LayoutMenuItemsBinding layoutMenuItemsBinding) {
            super(layoutMenuItemsBinding.getRoot());

            this.layoutMenuItemsBinding = layoutMenuItemsBinding;
        }


    }

    /*@BindingAdapter("imageUrl")
    public static void loadImages(AppCompatImageView imageView, String link) {
        Context context = imageView.getContext();


    }*/

}
