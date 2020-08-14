package com.kiduyu.joshuaproject.HerbalApp.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.User;
import com.kiduyu.joshuaproject.HerbalApp.Splash.MainActivity;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText name, email, number, pass;
    TextView login;
    Spinner location;
    private static int TIME_OUT = 8000;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.ed_username);
        location = findViewById(R.id.spinner);
        email = findViewById(R.id.ed_email);
        number = findViewById(R.id.ed_alternatmob);
        pass = findViewById(R.id.ed_password);

        login = findViewById(R.id.btn_sign);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        Context context;
        progressDialog= new ProgressDialog(this);

    }

    private void CreateAccount() {
        String mname = name.getText().toString().trim();
        String mlocation = location.getSelectedItem().toString().trim();
        String memail = email.getText().toString().trim();
        String mnumber = number.getText().toString().trim();
        String mpass = number.getText().toString().trim();

        if (TextUtils.isEmpty(mname)) {
            FancyToast.makeText(this, "Please write your name...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            name.setError("Name Required");
        } else if (mlocation.equals("Choose location")) {
            FancyToast.makeText(this, "Please Choose a location...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            ((TextView) location.getSelectedView()).setError("Location Required");
        } else if (TextUtils.isEmpty(memail)) {
            FancyToast.makeText(this, "Please key in your Email...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            email.setError("Email Required");
        } else if (TextUtils.isEmpty(mnumber)) {
            number.setError("Number Required");
            FancyToast.makeText(this, "Please key in your Phone number...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        } else if (TextUtils.isEmpty(mpass)) {
            pass.setError("Password Required");
            FancyToast.makeText(this, "Please key in your secret Password...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        } else {
            Loading.pleaseWait(this);
            FancyToast.makeText(this, "Please Wait...", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

            ValidateCredentials(mname, mlocation, memail, mnumber, mpass);

        }

    }

    private void ValidateCredentials(final String mname, final String mlocation, final String memail, final String mnumber, final String mpass) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(mnumber).exists())) {
                    User user = new User(mname, mlocation, memail, mnumber, mpass, "");

                    RootRef.child("Users").child(mnumber).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull final Task<Void> task) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This method will be executed once the timer is over
                                            if (Netcheck.internetChack(RegisterActivity.this)) {

                                                if (task.isSuccessful()) {
                                                    FancyToast.makeText(RegisterActivity.this, "Congratulations " + mname + " your account has been created.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                                    Loading.hideProgressDialog(RegisterActivity.this);

                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Loading.hideProgressDialog(RegisterActivity.this);
                                                    FancyToast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                                }

                                            } else {
                                                AlertDialog.Builder builder;
                                                builder = new AlertDialog.Builder(RegisterActivity.this);
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
                                    }, TIME_OUT);

                                }
                            });

                } else {
                    FancyToast.makeText(RegisterActivity.this, mnumber + " already exists.", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    Loading.hideProgressDialog(RegisterActivity.this);
                    FancyToast.makeText(RegisterActivity.this, "Please try again using another phone number.", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FancyToast.makeText(RegisterActivity.this, String.valueOf(databaseError), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }
        });
    }
}