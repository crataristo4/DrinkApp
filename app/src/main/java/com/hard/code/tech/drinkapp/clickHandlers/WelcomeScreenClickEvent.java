package com.hard.code.tech.drinkapp.clickHandlers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hard.code.tech.drinkapp.activities.LoginActivity;
import com.hard.code.tech.drinkapp.activities.RegisterActivity;

public class WelcomeScreenClickEvent {
    private Context context;

    public WelcomeScreenClickEvent(Context context) {
        this.context = context;

    }


    public void gotoSignUp(View view) {
        context.startActivity(new Intent(context, RegisterActivity.class));

    }

    public void gotoLogin(View view) {
        context.startActivity(new Intent(context, LoginActivity.class));

    }
}
