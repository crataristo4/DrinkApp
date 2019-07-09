package com.hard.code.tech.drinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.clickHandlers.LoginClickEvent;
import com.hard.code.tech.drinkapp.databinding.ActivityLoginBinding;

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


}
