package com.hard.code.tech.drinkapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.clickHandlers.RegisterClickHandler;
import com.hard.code.tech.drinkapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    RegisterClickHandler registerClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerClickHandler = new RegisterClickHandler(this, activityRegisterBinding.edtPhone,
                activityRegisterBinding.edtName, activityRegisterBinding.datePickerDOB, activityRegisterBinding.edtAddress);
        activityRegisterBinding.setRegisterUser(registerClickHandler);
    }
}
