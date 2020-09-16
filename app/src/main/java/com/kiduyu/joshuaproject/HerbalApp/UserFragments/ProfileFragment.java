package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.fragment.app.Fragment;

import com.kiduyu.joshuaproject.HerbalApp.Models.User;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;

public class ProfileFragment extends Fragment {
    EditText edtFullName;
    EditText edtEmail;
    EditText edtPassword;
    Button edt_profile, save;
    EditText edtMobile;
    Toolbar toolbar;
    ProgressDialog pDialog;
    String maddress="";
    Menu menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        pDialog = new ProgressDialog(getActivity());
        edtFullName = view.findViewById(R.id.edt_name);
        edtFullName.setEnabled(false);
        save = view.findViewById(R.id.edt_profile_save);
        edt_profile = view.findViewById(R.id.edt_profile);
        edtEmail = view.findViewById(R.id.edt_email);
        edtEmail.setEnabled(false);
        edtPassword = view.findViewById(R.id.edt_password);
        edtPassword.setEnabled(false);
        edtMobile = view.findViewById(R.id.edt_phone);
        edtMobile.setEnabled(false);
       // String uniqueid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        GetProfile();

        edt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtFullName.setEnabled(true);
                edtFullName.setText("");
                edtFullName.setMaxLines(1);
                edtFullName.setHint("Input New Full Name");
                edtFullName.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtEmail.setEnabled(true);
                edtEmail.setText("");
                edtEmail.setMaxLines(1);
                edtEmail.setHint("Input New Email");
                edtEmail.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtPassword.setEnabled(true);
                edtPassword.setText("");
                edtPassword.setMaxLines(1);
                edtPassword.setHint("Input New password");
                edtPassword.setHintTextColor(getResources().getColor(R.color.dialog_color));


                edtMobile.setEnabled(true);
                edtMobile.setText("");
                edtMobile.setMaxLines(1);
                edtMobile.setHint("Input New mobile number");
                edtMobile.setHintTextColor(getResources().getColor(R.color.dialog_color));


                edt_profile.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtFullName.getText().toString();
                String phone = edtMobile.getText().toString();
                String memail = edtMobile.getText().toString();
                String password = edtPassword.getText().toString();



                if (TextUtils.isEmpty(name))
                {
                    edtFullName.setError("Name Is Required..");
                    return;
                }
                else if (TextUtils.isEmpty(phone))
                {
                    edtMobile.setError("Phone Number Is Required..");
                    return;
                }else if (TextUtils.isEmpty(memail))
                {
                    edtMobile.setError("Phone Number Is Required..");
                    return;
                }
                else if (TextUtils.isEmpty(password))
                {
                    edtPassword.setError("Password Is Required..");
                    return;
                }
                else
                {
                    String first3 ="";
                    first3=phone.substring(0 , 3);
                    if (!first3.equals("254")){
                        edtMobile.setError("Phone Number must start with 254..");
                        return;
                    }else {
                        pDialog.setTitle("Updating Account");
                        pDialog.setMessage("Please wait, while we are checking the credentials.");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        //String uniqueid= UUID.randomUUID().toString();
                        String uniqueid= Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                        Log.d("TAG", "onCreate: "+uniqueid);

                       ValidatephoneNumber(name, phone,memail,maddress, password);
                    }

                }
            }
        });
        return view;
    }

    private void ValidatephoneNumber(final String name, final String phone, final String memail, final String maddress, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uniqueid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                User userdata = new User(name,maddress,memail,phone,password,"");
                RootRef.child("Users").child(uniqueid).setValue(userdata)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Congratulations "+name+", your account has been Updated.", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,new ProfileFragment()).commit();
                        }
                        else
                        {
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void GetProfile() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child("Users").child(Netcheck.getIMEI(getActivity())).exists()) {
                    User usersData = dataSnapshot.child("Users").child(Netcheck.getIMEI(getActivity())).getValue(User.class);
                    edtFullName.setText(usersData.getFname());
                    edtFullName.setTextSize(23.00f);

                    maddress=usersData.getArea();
                    edtEmail.setText(usersData.getEmail());
                    edtEmail.setTextSize(23.00f);

                    edtPassword.setText(usersData.getPass());
                    edtPassword.setTextSize(23.00f);

                    edtMobile.setText(usersData.getPhone());
                    edtMobile.setTextSize(23.00f);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}