package com.kiduyu.joshuaproject.HerbalApp.Loading;

import android.app.ProgressDialog;
import android.content.Context;

import com.kiduyu.joshuaproject.k_vet.R;

import java.util.Objects;

public class Loading {
    ProgressDialog progressDialog;

    public Loading (){

    }
    public static void showProgressDialog(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading);
        progressDialog.setCanceledOnTouchOutside(false);

    }

}
