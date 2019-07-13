package com.hard.code.tech.drinkapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.clickHandlers.WelcomeScreenClickEvent;
import com.hard.code.tech.drinkapp.databinding.ActivityWelcomeBinding;
import com.hard.code.tech.drinkapp.storage.SharedPrefManager;
import com.hard.code.tech.drinkapp.utils.Utils;

import static com.hard.code.tech.drinkapp.utils.mConstants.REQUEST_PERMISSION;

public class WelcomePageActivity extends AppCompatActivity {

    ActivityWelcomeBinding mainBinding;
    WelcomeScreenClickEvent registerClickHandler;


    boolean isBackPressed = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Utils.displayToast(WelcomePageActivity.this, "Permission granted");

                } else {
                    Utils.displayToast(WelcomePageActivity.this, "Permission denied");
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, HomePageActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Splash Screen activity displays in a full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        registerClickHandler = new WelcomeScreenClickEvent(this);
        mainBinding.setDoAction(registerClickHandler);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);

            }

        }


    }


}
