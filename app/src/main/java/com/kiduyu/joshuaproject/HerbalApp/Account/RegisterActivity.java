package com.kiduyu.joshuaproject.HerbalApp.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText name,email,number,pass;
    TextView login;
    Spinner location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.ed_username);
        location=findViewById(R.id.spinner);
        email=findViewById(R.id.ed_email);
        number=findViewById(R.id.ed_alternatmob);
        pass=findViewById(R.id.ed_password);

        login=findViewById(R.id.btn_sign);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });



    }

    private void CreateAccount() {
        String mname = name.getText().toString().trim();
        String mlocation = location.getSelectedItem().toString().trim();
        String memail = email.getText().toString().trim();
        String mnumber = number.getText().toString().trim();
        String mpass = number.getText().toString().trim();

        if (TextUtils.isEmpty(mname))
        {
            FancyToast.makeText(this, "Please write your name...", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            name.setError("Name Required");
        }
        else if (mlocation.equals("Choose location"))
        {
            FancyToast.makeText(this, "Please Choose a location...", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            ((TextView)location.getSelectedView()).setError("Location Required");
        }
        else if (TextUtils.isEmpty(memail))
        {
            FancyToast.makeText(this, "Please key in your Email...", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            email.setError("Email Required");
        }
        else if (TextUtils.isEmpty(mnumber))
        {
            number.setError("Number Required");
            FancyToast.makeText(this, "Please key in your Phone number...", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        }else if (TextUtils.isEmpty(mpass))
        {
            pass.setError("Password Required");
            FancyToast.makeText(this, "Please key in your secret Password...", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        }
        else
        {
            Loading.pleaseWait(this);
            FancyToast.makeText(this, "Please Wait...", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

            ValidateCredentials(mname,mlocation,memail,mnumber,mpass);

        }

    }

    private void ValidateCredentials(String mname, String mlocation, String memail, String mnumber, String mpass) {

        DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        
    }
}