package com.hard.code.tech.drinkapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.DrinksByMenuIdAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.databinding.ActivitySearchDrinksBinding;
import com.hard.code.tech.drinkapp.model.DrinkById;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchDrinksActivity extends AppCompatActivity {

    ActivitySearchDrinksBinding activitySearchDrinksBinding;
    RecyclerView searchRecycler;
    RetrofitInterface drinksRetrofitInterface;
    CompositeDisposable compositeDisposable;
    DrinksByMenuIdAdapter drinksAdapter, searchAdapter;

    List<String> searchList = new ArrayList<>();
    List<DrinkById> drinksList = new ArrayList<>();

    MaterialSearchBar materialSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySearchDrinksBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_drinks);
        drinksRetrofitInterface = RetrofitClient.getInstance().getAPI();
        compositeDisposable = new CompositeDisposable();

        searchRecycler = activitySearchDrinksBinding.recyclerViewSearchDrinks;
        searchRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        materialSearchBar = activitySearchDrinksBinding.searchBar;
        materialSearchBar.setCardViewElevation(10);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<String> suggestions = new ArrayList<>();
                for (String searchItems : searchList) {
                    if (searchItems.toLowerCase().contains(materialSearchBar.getText())) {
                        suggestions.add(searchItems);

                    }
                }
                materialSearchBar.setLastSuggestions(suggestions);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    //restore all drinks
                    searchRecycler.setAdapter(drinksAdapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                beginSearch(text);

            }

            private void beginSearch(CharSequence text) {
                List<DrinkById> searchResult = new ArrayList<>();

                for (DrinkById drink : drinksList) {
                    if (drink.getName().contains(text)) {
                        searchResult.add(drink);
                        searchAdapter = new DrinksByMenuIdAdapter(searchResult, SearchDrinksActivity.this);
                        searchRecycler.setAdapter(searchAdapter);
                    }
                }

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        loadAllDrinks();

    }


    private void loadAllDrinks() {
        compositeDisposable.add(drinksRetrofitInterface.getAllDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DrinkById>>() {
                    @Override
                    public void accept(List<DrinkById> drinkByIds) throws Exception {

                        displayDrinks(drinkByIds);
                        loadSearch(drinkByIds);

                    }

                    private void loadSearch(List<DrinkById> drinkByIds) {
                        for (DrinkById drinks : drinkByIds) {
                            searchList.add(drinks.getName());
                            materialSearchBar.setLastSuggestions(searchList);
                        }
                    }

                    private void displayDrinks(List<DrinkById> drinkByIds) {

                        drinksList = drinkByIds;
                        drinksAdapter = new DrinksByMenuIdAdapter(drinksList, SearchDrinksActivity.this);
                        searchRecycler.setAdapter(drinksAdapter);

                    }
                }));
    }


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
