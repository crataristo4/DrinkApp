package com.hard.code.tech.drinkapp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {
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
