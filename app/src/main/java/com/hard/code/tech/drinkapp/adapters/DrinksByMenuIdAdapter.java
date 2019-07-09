package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.databinding.LayoutDrinksByMenuIdBinding;
import com.hard.code.tech.drinkapp.model.DrinkById;

import java.util.List;

public class DrinksByMenuIdAdapter extends RecyclerView.Adapter<DrinksByMenuIdAdapter.DrinksViewHolder> {

    private List<DrinkById> drinkByIdList;
    private Context context;

    public DrinksByMenuIdAdapter(List<DrinkById> drinkByIdList, Context context) {
        this.drinkByIdList = drinkByIdList;
        this.context = context;
    }

    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.layout_drinks_by_menu_id, parent, false);

        return new DrinksViewHolder(layoutDrinksByMenuIdBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {

        DrinkById drinkById = drinkByIdList.get(position);
        holder.layoutDrinksByMenuIdBinding.setDrinks(drinkById);

    }

    @Override
    public int getItemCount() {
        return drinkByIdList == null ? 0 : drinkByIdList.size();
    }

    static class DrinksViewHolder extends RecyclerView.ViewHolder {

        private LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding;

        DrinksViewHolder(@NonNull LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding) {
            super(layoutDrinksByMenuIdBinding.getRoot());

            this.layoutDrinksByMenuIdBinding = layoutDrinksByMenuIdBinding;


        }


    }

}
