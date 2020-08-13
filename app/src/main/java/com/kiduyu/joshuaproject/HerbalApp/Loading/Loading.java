package com.kiduyu.joshuaproject.HerbalApp.Loading;

import android.app.ProgressDialog;
import android.content.Context;

import com.kiduyu.joshuaproject.k_vet.R;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.util.Objects;

public class Loading {
    ProgressDialog progressDialog;

    public Loading (){

    }
    public static void showProgressDialog(Context context) {
        PercentageChartView progressDialog;
        progressDialog = new PercentageChartView(context);
        progressDialog.apply();

    }

}
