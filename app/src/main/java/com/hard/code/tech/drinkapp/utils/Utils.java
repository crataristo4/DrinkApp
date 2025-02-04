package com.hard.code.tech.drinkapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.hard.code.tech.drinkapp.R;

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

    public static void displayAlertDialog(Context context, String title, String msg, String btnPos, String btnNeg, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        if (btnPos != null) builder.setPositiveButton(btnPos, onClickListener);
        if (btnNeg != null) builder.setNegativeButton(btnNeg, onClickListener);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public static void makeSnackbar(View view, String message, String action, View.OnClickListener onClickListener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, onClickListener);
        snackbar.setActionTextColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();


    }
}
