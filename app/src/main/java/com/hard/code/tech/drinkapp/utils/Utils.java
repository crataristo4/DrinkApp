package com.hard.code.tech.drinkapp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Random;

public class Utils {

    private static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawableColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }


    public static void displayToast(Context ctx,String text){
        Toast toast= Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static ProgressDialog displayProgress(Context ctx, String message) {
        ProgressDialog loading = new ProgressDialog(ctx);
        loading.setCancelable(false);
        loading.setMessage(message);
        // loading.show();

        return loading;
    }
}
