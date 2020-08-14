package com.kiduyu.joshuaproject.HerbalApp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.k_vet.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_login);
    }

    public void signup(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
