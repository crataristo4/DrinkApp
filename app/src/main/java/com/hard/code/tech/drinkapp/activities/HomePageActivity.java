package com.hard.code.tech.drinkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.google.gson.Gson;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.MenuCategoryAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.database.datasource.CartRepository;
import com.hard.code.tech.drinkapp.database.datasource.FavoriteRepository;
import com.hard.code.tech.drinkapp.database.localstorage.CartDataSource;
import com.hard.code.tech.drinkapp.database.localstorage.DrinksRoomDatabase;
import com.hard.code.tech.drinkapp.database.localstorage.FavoriteDataSource;
import com.hard.code.tech.drinkapp.databinding.ActivityHomePageBinding;
import com.hard.code.tech.drinkapp.databinding.NavHeaderHomePageBinding;
import com.hard.code.tech.drinkapp.model.Banners;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.model.UserResponse;
import com.hard.code.tech.drinkapp.storage.SharedPrefManager;
import com.hard.code.tech.drinkapp.utils.ProgressRequestBody;
import com.hard.code.tech.drinkapp.utils.UploadCallBack;
import com.hard.code.tech.drinkapp.utils.Utils;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hard.code.tech.drinkapp.utils.mConstants.FILE_REQUEST_PERMISSION;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UploadCallBack {
    private ActivityHomePageBinding activityHomePageBinding;
    private List<MenuCategory> menuCategoryList = new ArrayList<>();
    private SliderLayout sliderLayout;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface retrofitInterface;
    private MenuCategoryAdapter menuCategoryAdapter;
    private RecyclerView recyclerView;
    private NotificationBadge badge;
    boolean isBackPressed = false;
    private AppCompatImageView cartIcon;
    private CircleImageView image;
    private Uri fileSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);

        Toolbar toolbar = activityHomePageBinding.layoutAppBarHome.toolbar;
        setSupportActionBar(toolbar);

        Log.i("Phone ", SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone()
                + "Image :: " + SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getImageUrl());


        DrawerLayout drawer = activityHomePageBinding.drawerLayout;
        NavigationView navigationView = activityHomePageBinding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        NavHeaderHomePageBinding navHeaderHomePageBinding = DataBindingUtil.bind(view);


        if (navHeaderHomePageBinding != null) {

            image = navHeaderHomePageBinding.image;
            navHeaderHomePageBinding.txtName.setText(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getName());
            navHeaderHomePageBinding.txtPhoneNumber.setText(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone());

            navHeaderHomePageBinding.txtPhoneNumber.setOnClickListener(v -> {
                Utils.displayToast(this, "clicked me");
            });


            image.setOnClickListener(view1 -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),
                            "Select a photo"), FILE_REQUEST_PERMISSION);

                }

            });


            Log.i("onCreate: ", SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getImageUrl());


            if (SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getImageUrl() != null) {




               /* Glide.with(HomePageActivity.this)
                        .load(new StringBuilder(RetrofitClient.BASE_URL)
                                .append("imageUrl/")
                                .append(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone()))
                        .into(image);*/
                //.append(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getImageUrl()))


            } else if (SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getImageUrl() == null) {
                image.setImageResource(R.drawable.defaultphoto);
            }

        }


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
        RetrofitClient.drinksRoomDatabase = DrinksRoomDatabase.getInstance(this);

        RetrofitClient.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(
                RetrofitClient.drinksRoomDatabase.cartDAO())
        );

        RetrofitClient.favoriteRepository = FavoriteRepository.getInstance(
                FavoriteDataSource.getInstance(RetrofitClient.drinksRoomDatabase.favoriteDAO())
        );

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
            if (isBackPressed) {
                super.onBackPressed();
                return;

            }
            this.isBackPressed = true;
            Utils.displayToast(this, "Press back again to exit");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart_item, menu);

        View view = menu.findItem(R.id.action_cart).getActionView();
        badge = view.findViewById(R.id.badge);
        cartIcon = view.findViewById(R.id.cartIcon);

        cartIcon.setOnClickListener(viewIcon -> {
            startActivity(new Intent(HomePageActivity.this, CartItemActivity.class));
        });

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
        if (id == R.id.action_cart) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            Utils.displayAlertDialog(this, "Log out", "Are you sure you want to log out?"
                    , "YES", "NO", (dialog, which) -> {
                        if (which == -1) {
                            SharedPrefManager.getInstance(HomePageActivity.this).clear();
                            startActivity(new Intent(HomePageActivity.this, SplashScreenActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else if (which == -2) {
                            dialog.dismiss();
                        }

                    });

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == FILE_REQUEST_PERMISSION) {

                if (data != null) {

                    fileSelected = data.getData();

                    if ((fileSelected != null) && !fileSelected.getPath().isEmpty()) {

                        /*Glide.with(HomePageActivity.this).load(fileSelected)
                                .into(image);*/

                        image.setImageURI(fileSelected);
                        uploadFile();

                    } else {
                        Utils.displayToast(HomePageActivity.this, "Cannot upload file try again..");
                    }
                }
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadFile() {

        if (fileSelected != null) {

            File file = FileUtils.getFile(this, fileSelected);

            String filename = SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone() +
                    FileUtils.getExtension(file.toString());

            Log.i("File name : ", file.toString()
                    + " :: " + FileUtils.getExtension(file.toString()) + " ::" + filename);

            ProgressRequestBody requestFile = new ProgressRequestBody(file, this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("image", filename, requestFile);

            //final MultipartBody.Part phone = MultipartBody.Part.createFormData("phone",
            // String.valueOf(SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone()));

            final String phone = SharedPrefManager.getInstance(HomePageActivity.this).getUsa().getPhone();

            new Thread(() -> {

                retrofitInterface.uploadFile(phone, body).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                        UserResponse userResponse = response.body();

                        assert userResponse != null;
                        if (!userResponse.isError()) {

                            SharedPrefManager.getInstance(HomePageActivity.this)
                                    .saveUsers(userResponse.getUsers());
                            Utils.displayToast(HomePageActivity.this, userResponse.getMessage());
                            Log.i("response", new Gson().toJson(userResponse.getUsers()));
                            Log.i("Image", new Gson().toJson(userResponse.getUsers().getImageUrl()));


                        } else {
                            Utils.displayToast(HomePageActivity.this, userResponse.getMessage());
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {

                        Utils.displayToast(HomePageActivity.this, t.getLocalizedMessage());
                        Log.i("Error", Objects.requireNonNull(t.getLocalizedMessage()));

                    }
                });


            }).start();


        }
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

        super.onResume();
        updateCounter();
        isBackPressed = false;
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();

    }

    @Override
    public void onProgressUpdate(int percentage) {

    }


}

