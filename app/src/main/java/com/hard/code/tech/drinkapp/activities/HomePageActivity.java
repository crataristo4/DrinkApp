package com.hard.code.tech.drinkapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.material.navigation.NavigationView;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.MenuCategoryAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.database.datasource.CartRepository;
import com.hard.code.tech.drinkapp.database.localstorage.CartDataSource;
import com.hard.code.tech.drinkapp.database.localstorage.CartDatabase;
import com.hard.code.tech.drinkapp.databinding.ActivityHomePageBinding;
import com.hard.code.tech.drinkapp.model.Banners;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.storage.SharedPrefManager;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomePageBinding activityHomePageBinding;
    private List<MenuCategory> menuCategoryList = new ArrayList<>();
    private SliderLayout sliderLayout;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface retrofitInterface;
    private MenuCategoryAdapter menuCategoryAdapter;
    private RecyclerView recyclerView;
    private NotificationBadge badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);

        Toolbar toolbar = activityHomePageBinding.layoutAppBarHome.toolbar;
        setSupportActionBar(toolbar);


        DrawerLayout drawer = activityHomePageBinding.drawerLayout;
        NavigationView navigationView = activityHomePageBinding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //TODO bind items
        View view = navigationView.getHeaderView(0);
        TextView name = view.findViewById(R.id.txtName);
        TextView phone = view.findViewById(R.id.txtPhoneNumber);

        name.setText(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getName());
        phone.setText(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone());

        retrofitInterface = RetrofitClient.getInstance().getAPI();
        //slider
        sliderLayout = activityHomePageBinding.layoutAppBarHome.layoutContentHomePage.sliderLayout;
        //recycler view
        recyclerView = activityHomePageBinding.layoutAppBarHome.layoutContentHomePage.recyclerMenuCategory;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


//get Banners
        getDisplayBanners();

        //get Menu
        getMenuItems();

        //get toppings
        getToppings();

        //initialise room database
        initRoomDatabase();

    }

    private void initRoomDatabase() {
        RetrofitClient.cartDatabase = CartDatabase.getInstance(this);
        RetrofitClient.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(RetrofitClient.cartDatabase.cartDAO()));
    }

    private void getToppings() {
        compositeDisposable.add(
                retrofitInterface.getMenuById(RetrofitClient.TOPPINGS_ID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(drinkByIds -> RetrofitClient.toppingList = drinkByIds));
    }

    private void getMenuItems() {
        compositeDisposable.add(retrofitInterface.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MenuCategory>>() {
                    @Override
                    public void accept(List<MenuCategory> menuCategories) throws Exception {

                        displayMenuCategory(menuCategories);
                        //initItemClickListener();

                    }

                    private void displayMenuCategory(List<MenuCategory> menuCategories) {
                        menuCategoryAdapter = new MenuCategoryAdapter(menuCategories, HomePageActivity.this);
                        recyclerView.setAdapter(menuCategoryAdapter);



                    }
                }));
    }

    private void getDisplayBanners() {

        compositeDisposable.add(retrofitInterface.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banners>>() {
                    @Override
                    public void accept(List<Banners> banners) throws Exception {
                        showBanners(banners);
                    }

                    private void showBanners(List<Banners> banners) {
                        HashMap<String, String> bannerHashMap = new HashMap<>();
                        for (Banners item : banners)
                            bannerHashMap.put(item.getName(), item.getLink());

                        for (String name : bannerHashMap.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(HomePageActivity.this);
                            textSliderView.description(name)
                                    .image(bannerHashMap.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);

                            sliderLayout.addSlider(textSliderView);
                        }


                    }
                }));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = activityHomePageBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart_item, menu);

        View view = menu.findItem(R.id.action_cart).getActionView();
        badge = view.findViewById(R.id.badge);

        updateCounter();

        return true;
    }

    private void updateCounter() {

        if (badge == null) return;
        runOnUiThread(() -> {

            if (RetrofitClient.cartRepository.countCartItems() == 0) {
                badge.setVisibility(View.INVISIBLE);
            } else {
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(RetrofitClient.cartRepository.countCartItems()));
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, WelcomePageActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }


    }

    @Override
    protected void onResume() {
        updateCounter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();

    }
}

