package com.hard.code.tech.drinkapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.DrinksByMenuIdAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.databinding.ActivityDrinksByMenuIdBinding;
import com.hard.code.tech.drinkapp.model.DrinkById;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinksByMenuIdActivity extends AppCompatActivity {

    private ActivityDrinksByMenuIdBinding activityDrinksByMenuIdBinding;
    private RecyclerView recyclerView;
    DrinksByMenuIdAdapter drinksByMenuIdAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface retrofitInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDrinksByMenuIdBinding = DataBindingUtil.setContentView(this, R.layout.activity_drinks_by_menu_id);

        recyclerView = activityDrinksByMenuIdBinding.recyclerMenuById;
        retrofitInterface = RetrofitClient.getInstance().getAPI();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getDrinksById(RetrofitClient.menuCategory.getId());

        TextView catName = activityDrinksByMenuIdBinding.menuName;
        if (RetrofitClient.menuCategory.getName() != null)
            catName.setText(RetrofitClient.menuCategory.getName());
        else
            catName.setText(getString(R.string.menu_cat));

    }

    private void getDrinksById(String id) {
        compositeDisposable.add(retrofitInterface.getMenuById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DrinkById>>() {
                    @Override
                    public void accept(List<DrinkById> drinkByIds) throws Exception {
                        displayDrinks(drinkByIds);
                    }

                    private void displayDrinks(List<DrinkById> drinkByIds) {

                        drinksByMenuIdAdapter = new DrinksByMenuIdAdapter(drinkByIds, DrinksByMenuIdActivity.this);
                        recyclerView.setAdapter(drinksByMenuIdAdapter);
                    }
                }));
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
