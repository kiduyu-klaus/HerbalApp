package com.kiduyu.joshuaproject.HerbalApp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.k_vet.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBar.changeStatusBarColor(this);
        Loading.showProgressDialog(this);
    }
}
