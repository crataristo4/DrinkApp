package com.hard.code.tech.drinkapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.clickHandlers.LoginClickEvent;
import com.hard.code.tech.drinkapp.databinding.ActivityLoginBinding;
import com.hard.code.tech.drinkapp.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    LoginClickEvent loginClickEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginClickEvent = new LoginClickEvent(this,activityLoginBinding.edtLoginPhone);
        activityLoginBinding.setDoLogin(loginClickEvent);
    }


    boolean isBackPressed = false;

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;

        }
        this.isBackPressed = true;
        Utils.displayToast(this, "Press back again to exit");

    }


    @Override
    protected void onResume() {

        super.onResume();
        isBackPressed = false;
    }

}
