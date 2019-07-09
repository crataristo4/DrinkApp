package com.hard.code.tech.drinkapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.clickHandlers.WelcomeScreenClickEvent;
import com.hard.code.tech.drinkapp.databinding.ActivityWelcomeBinding;
import com.hard.code.tech.drinkapp.storage.SharedPrefManager;

public class WelcomePageActivity extends AppCompatActivity {
    ActivityWelcomeBinding mainBinding;
    WelcomeScreenClickEvent registerClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Splash Screen activity displays in a full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        registerClickHandler = new WelcomeScreenClickEvent(this);
        mainBinding.setDoAction(registerClickHandler);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, HomePageActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }
    }


}
