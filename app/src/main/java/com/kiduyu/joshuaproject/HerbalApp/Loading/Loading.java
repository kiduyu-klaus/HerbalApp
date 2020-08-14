package com.kiduyu.joshuaproject.HerbalApp.Loading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;


import com.kiduyu.joshuaproject.k_vet.R;

import java.util.Objects;
import java.util.logging.Handler;

public class Loading {



    public Loading(){


    }

    public static void showProgressDialog(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

    }
    public static void pleaseWait(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.please_wait);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

    }

    public static void hideProgressDialog(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.dismiss();

    }




}
