package com.criticalblue.auth.demo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

public class AlertBox {
    public static void show(Context context, String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, listener)
                .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }
}
