package com.swufe.czj.imitateqq;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogFactory {


    public static ProgressDialog creatRequestDialog(final Context context, String tip) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("加载中");
        progressDialog.setMessage(tip);
        progressDialog.setCancelable(true);
        progressDialog.show();
        return progressDialog;

    }

    public static void nomalDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", null)
                .create()
                .show();
    }
}