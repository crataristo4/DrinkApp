package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.activities.DrinksByMenuIdActivity;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.databinding.LayoutMenuItemsBinding;
import com.hard.code.tech.drinkapp.model.MenuCategory;

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

        holder.layoutMenuItemsBinding.getRoot().setOnClickListener((view) -> {
            RetrofitClient.menuCategory = menuCategoryList.get(position);
            context.startActivity(new Intent(context, DrinksByMenuIdActivity.class));
        });


    }

    @Override
    public int getItemCount() {
        return menuCategoryList == null ? 0 : menuCategoryList.size();
    }


    static class MenuCategoryViewHolder extends RecyclerView.ViewHolder  {

        private LayoutMenuItemsBinding layoutMenuItemsBinding;

        MenuCategoryViewHolder(@NonNull LayoutMenuItemsBinding layoutMenuItemsBinding) {
            super(layoutMenuItemsBinding.getRoot());

            this.layoutMenuItemsBinding = layoutMenuItemsBinding;


        }


    }


}
