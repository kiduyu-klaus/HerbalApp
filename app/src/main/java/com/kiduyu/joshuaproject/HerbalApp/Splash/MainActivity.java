package com.kiduyu.joshuaproject.HerbalApp.Splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kiduyu.joshuaproject.HerbalApp.Account.LoginActivity;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (Netcheck.internetChack(MainActivity.this)) {
                   /* if (user != null) {
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(FirstActivity.this, InfoActivity.class);
                        startActivity(i);
                    }
                    finish();

                    */

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(MainActivity.this);
                    //Setting message manually and performing action on button click
                    builder.setMessage("Please Check Your Internet Connection")
                            .setCancelable(false)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        }, SPLASH_TIME_OUT);


    }



}
