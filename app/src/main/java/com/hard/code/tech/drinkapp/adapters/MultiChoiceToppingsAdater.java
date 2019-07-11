package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.databinding.LayooutToppingsBinding;
import com.hard.code.tech.drinkapp.model.DrinkById;

import java.util.List;

public class MultiChoiceToppingsAdater extends RecyclerView.Adapter<MultiChoiceToppingsAdater.MultiChoiceToppingsViewHolder> {

    private Context context;
    private List<DrinkById> toppingList;

    public MultiChoiceToppingsAdater(Context context, List<DrinkById> toppingList) {
        this.context = context;
        this.toppingList = toppingList;
    }

    @NonNull
    @Override
    public MultiChoiceToppingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayooutToppingsBinding layooutToppingsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layoout_toppings, parent, false);

        return new MultiChoiceToppingsViewHolder(layooutToppingsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceToppingsViewHolder holder, int position) {
        DrinkById toppings = toppingList.get(position);

        holder.layooutToppingsBinding.setToppings(toppings);
        Log.i("Toppings", toppings.getName());

        holder.layooutToppingsBinding.checkToppings.setOnCheckedChangeListener((compoundButton, isChecked) -> {

            if (isChecked) {

                RetrofitClient.addedToppings.add(compoundButton.getText().toString());
                RetrofitClient.toppingsPrice += Double.parseDouble(
                        String.valueOf(toppingList.get(position).getPrice())
                );

                Log.i("On Toppings checked", String.valueOf(RetrofitClient.toppingsPrice));
            } else {

                RetrofitClient.addedToppings.remove(compoundButton.getText().toString());
                RetrofitClient.toppingsPrice -= Double.parseDouble(
                        String.valueOf(toppingList.get(position).getPrice())
                );
            }

        });

    }

    @Override
    public int getItemCount() {
        return toppingList == null ? 0 : toppingList.size();
    }

    static class MultiChoiceToppingsViewHolder extends RecyclerView.ViewHolder {

        LayooutToppingsBinding layooutToppingsBinding;

        MultiChoiceToppingsViewHolder(@NonNull LayooutToppingsBinding layooutToppingsBinding) {
            super(layooutToppingsBinding.getRoot());

            this.layooutToppingsBinding = layooutToppingsBinding;
        }
    }
}
