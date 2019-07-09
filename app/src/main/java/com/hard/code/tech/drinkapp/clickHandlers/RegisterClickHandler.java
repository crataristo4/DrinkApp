package com.hard.code.tech.drinkapp.clickHandlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.hard.code.tech.drinkapp.activities.HomePageActivity;
import com.hard.code.tech.drinkapp.activities.LoginActivity;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.api.RetrofitInterface;
import com.hard.code.tech.drinkapp.model.Users;
import com.hard.code.tech.drinkapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterClickHandler {
    private Context context;
    private DatePicker datePicker;
    private TextInputLayout txtPhone,  txtName, txtAddress;



    public RegisterClickHandler(Context context, TextInputLayout txtPhone,
                                TextInputLayout txtName,DatePicker datePicker,
                                TextInputLayout txtAddress) {
        this.context = context;
        this.txtPhone = txtPhone;
        this.txtName = txtName;
        this.datePicker = datePicker;
        this.txtAddress = txtAddress;
    }


    private void validateInputs(final View view) {
        final ProgressDialog loading = Utils.displayProgress(view.getContext(),
                "please wait while we register you...");

        String phone = txtPhone.getEditText().getText().toString();
        String dob =   datePicker.getYear() +"/" + (datePicker.getMonth() + 1)+"/"+ datePicker.getDayOfMonth();

        String name = txtName.getEditText().getText().toString();
        String address = txtAddress.getEditText().getText().toString();


        if (!phone.isEmpty() &&
                !dob.isEmpty() &&
                !name.isEmpty() && !address.isEmpty()) {

            txtPhone.setErrorEnabled(false);
            txtName.setErrorEnabled(false);
            txtAddress.setErrorEnabled(false);

            loading.show();

            RetrofitInterface retrofitInterface = RetrofitClient.getInstance().getAPI();

            Call<Users> call = retrofitInterface.createUser(phone, name,dob , address);

            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    Users defaultResponse = response.body();

                    if (response.isSuccessful() && TextUtils.isEmpty(defaultResponse.getError_msg())) {
                        loading.dismiss();
                        clear();

                        //RetrofitClient.currentUser = response.body();

                        context.startActivity(new Intent(context, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    }

                }

                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    loading.dismiss();
                    Utils.displayToast(view.getContext(), t.getMessage());

                }
            });


        } else if (phone.isEmpty()) {
            txtPhone.setErrorEnabled(true);
            txtPhone.setError("please enter your phone number");
        }  else if (name.isEmpty()) {
            txtName.setErrorEnabled(true);
            txtName.setError("please enter your full name");
        } else if (address.isEmpty()) {
            txtAddress.setErrorEnabled(true);
            txtAddress.setError("please enter your address");
        } else if (phone.length() > 12 || phone.length() < 10){
            txtPhone.setErrorEnabled(true);
            txtPhone.setError("please check your phone number");
        }

    }

    public void onRegisterUserClick(View view) {
        validateInputs(view);
    }


    void clear() {
        txtPhone.getEditText().setText("");
        txtName.getEditText().setText("");
        txtAddress.getEditText().setText("");

    }



}
