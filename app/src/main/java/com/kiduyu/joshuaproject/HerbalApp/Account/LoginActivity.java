package com.kiduyu.joshuaproject.HerbalApp.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Home.HomeActivity;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.User;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText phonenumber, pass;
    private CheckBox chkBoxRememberMe;
    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_login);

        phonenumber = findViewById(R.id.ed_username_logon);
        pass = findViewById(R.id.ed_password_login);
        login_btn = findViewById(R.id.btn_login_logon);

        chkBoxRememberMe = findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

    }

    public void signup(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void LoginUser() {
        String phone = phonenumber.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            phonenumber.setError("Phone Number Is Required..");
            FancyToast.makeText(this, "Phone Number Is Required..", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            pass.setError("Password Is Required..");
            FancyToast.makeText(this, "Password Is Required..", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            return;
        } else {
            Loading.showProgressDialog(this,true);

            AllowAccessToAccount(phone, password);


        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if (chkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uniqueid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                if (dataSnapshot.child("Users").child(uniqueid).exists()) {
                    User usersData = dataSnapshot.child("Users").child(uniqueid).getValue(User.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPass().equals(password)) {
                            FancyToast.makeText(LoginActivity.this, "Congratulations " + usersData.getFname() + " your account has been Verified.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            Loading.showProgressDialog(LoginActivity.this,false);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                            FancyToast.makeText(LoginActivity.this, "Wrong Password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }

                    } else {
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FancyToast.makeText(LoginActivity.this, "Wrong Username or phone", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                    }

                } else {
                    FancyToast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FancyToast.makeText(LoginActivity.this, String.valueOf(databaseError), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


            }
        });

    }
}
