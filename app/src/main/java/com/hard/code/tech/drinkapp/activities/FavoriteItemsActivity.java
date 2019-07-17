package com.hard.code.tech.drinkapp.activities;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.Interface.RecyclerItemTouchHelper;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.FavoriteAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;
import com.hard.code.tech.drinkapp.databinding.ActivityFavoriteItemsBinding;
import com.hard.code.tech.drinkapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteItemsActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    ActivityFavoriteItemsBinding activityFavoriteItemsBinding;
    FavoriteAdapter favoriteAdapter;
    List<Favorite> favoriteList = new ArrayList<>();
    RecyclerView recyclerViewFavoriteItems;
    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFavoriteItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_items);

        compositeDisposable = new CompositeDisposable();

        recyclerViewFavoriteItems = activityFavoriteItemsBinding.recyclerViewFavorites;
        recyclerViewFavoriteItems.setHasFixedSize(true);
        recyclerViewFavoriteItems.setLayoutManager(new LinearLayoutManager(this));


        loadFavoriteItems();
        swipeLeftToDelete();

    }

    private void loadFavoriteItems() {

        compositeDisposable.add(RetrofitClient.favoriteRepository.getFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayItems)
        );


    }

    private void displayItems(List<Favorite> favorites) {
        favoriteList = favorites;

        favoriteAdapter = new FavoriteAdapter(FavoriteItemsActivity.this, favorites);

        if (favoriteAdapter.getItemCount() == 0) {
            activityFavoriteItemsBinding.showEmptyFavorite.setVisibility(View.VISIBLE);

        } else {
            recyclerViewFavoriteItems.setAdapter(favoriteAdapter);
            activityFavoriteItemsBinding.showEmptyFavorite.setVisibility(View.GONE);

        }

    }

    private void swipeLeftToDelete() {
        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewFavoriteItems);


        //swipe to delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerViewFavoriteItems);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof  FavoriteAdapter.FavoriteViewHolder){

            String nameOfItem = favoriteList.get(viewHolder.getAdapterPosition()).name;

            Favorite itemDeleted = favoriteList.get(viewHolder.getAdapterPosition());

            int deletedIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            favoriteAdapter.removeItem(deletedIndex);
            //delete from database
            RetrofitClient.favoriteRepository.deleteFavorite(itemDeleted);

            Utils.makeSnackbar(activityFavoriteItemsBinding.constraintLayout,
                    nameOfItem + " has been deleted from favorites",
                    "UNDO DELETE", view -> {

                        favoriteAdapter.onRestoreItem(itemDeleted,deletedIndex);
                        //restore to database
                        RetrofitClient.favoriteRepository.insertFavorite(itemDeleted);

                    });



        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //reload items again if resumed ...
        loadFavoriteItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
