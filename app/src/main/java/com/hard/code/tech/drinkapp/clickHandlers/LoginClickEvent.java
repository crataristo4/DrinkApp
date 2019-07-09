package com.hard.code.tech.drinkapp.clickHandlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.hard.code.tech.drinkapp.activities.HomePageActivity;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.model.UserResponse;
import com.hard.code.tech.drinkapp.model.Users;
import com.hard.code.tech.drinkapp.storage.SharedPrefManager;
import com.hard.code.tech.drinkapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClickEvent {
    private static final String TAG = "LoginClickEvent";
    private Context context;
    private TextInputLayout txtPhone;


    public LoginClickEvent(Context context, TextInputLayout txtPhone) {
        this.context = context;
        this.txtPhone = txtPhone;

    }


    private void validateInputs(final View view) {
        final ProgressDialog loading = Utils.displayProgress(view.getContext(),
                "please wait while we register you...");

        String phone = txtPhone.getEditText().getText().toString();


        if (!phone.isEmpty()
        ) {

            txtPhone.setErrorEnabled(false);


            loading.show();

            RetrofitInterface retrofitInterface = RetrofitClient.getInstance().getAPI();



            Call<UserResponse> getUserInfo = retrofitInterface.getUsersInfo(phone);

            getUserInfo.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    if (!userResponse.isError()) {
                        loading.dismiss();
                        clear();

                        context.startActivity(new Intent(context, HomePageActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        SharedPrefManager.getInstance(view.getContext())
                                .saveUsers(userResponse.getUsers());

                    } else {
                        Utils.displayToast(context, response.message());

                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                    loading.dismiss();
                    Utils.displayToast(context, t.getLocalizedMessage());
                }
            });


        } else if (phone.isEmpty()) {
            txtPhone.setErrorEnabled(true);
            txtPhone.setError("please enter your phone number");
        }

    }

    public void LoginClickEvent(View view) {
        validateInputs(view);
    }


    void clear() {
        txtPhone.getEditText().setText("");


    }
}
