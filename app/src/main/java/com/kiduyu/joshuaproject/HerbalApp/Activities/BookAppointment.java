package com.kiduyu.joshuaproject.HerbalApp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Account.LoginActivity;
import com.kiduyu.joshuaproject.HerbalApp.Account.RegisterActivity;
import com.kiduyu.joshuaproject.HerbalApp.Loading.Loading;
import com.kiduyu.joshuaproject.HerbalApp.Models.Appointment;
import com.kiduyu.joshuaproject.HerbalApp.Models.User;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.AppointmentsFragments;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.HomeFragment;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;


import java.util.Calendar;
import java.util.Objects;

public class BookAppointment extends AppCompatActivity {
    TextInputEditText inputname,userapp,description,inputphone,adress;
    private  TextInputEditText datee;
    private  TextInputEditText description_tv;
    Button saveAppointment;

    private static int TIME_OUT = 8000;
    ProgressDialog pDialog;
    private String date, time = "";
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    String username, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Book Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_book_appointment);

        inputname=findViewById(R.id.edt_app_name);
        adress=findViewById(R.id.edt_book_user_phone);
        userapp=findViewById(R.id.edt_user_owner);
        description_tv=findViewById(R.id.edt_app_description);
        pDialog= new ProgressDialog(this);

        String name= getIntent().getStringExtra("consultant");

        inputname.setText(name);
        inputname.setEnabled(false);
        String phone= getIntent().getStringExtra("consultant_phone");
        inputphone=findViewById(R.id.edt_book_consu_phone);
        inputphone.setText(phone);
        inputphone.setEnabled(false);

        if (Prevalent.currentOnlineUser==null){
            FancyToast.makeText(this,"No user Logged In", FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
        }else {
            username=Prevalent.currentOnlineUser.getFname();
            userapp.setEnabled(false);
            userapp.setText(username);
            location=Prevalent.currentOnlineUser.getPhone();
            adress.setText(location);
            adress.setEnabled(false);

        }


        datee=findViewById(R.id.edt_booking_date);
        datee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(BookAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        // Toast.makeText(Patient_BookAppointmentActivity.this, date , Toast.LENGTH_SHORT).show();
                        datee.setText(date);
                        onStart();


                    }
                }, day, month, year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });
    }

    public void SaveAppointment(View view) {
        pDialog.setTitle("Updating Account");
        pDialog.setMessage("Please wait, while we are checking the credentials.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        String cname1= getIntent().getStringExtra("consultant");
        String cname = cname1.replace("Dr.","").trim();
        String cphone= getIntent().getStringExtra("consultant_phone");
        String uname = Prevalent.currentOnlineUser.getFname();
        String uphone = Prevalent.currentOnlineUser.getPhone();
        String udescri = description_tv.getText().toString().trim();
        String udate = datee.getText().toString();
        String image= getIntent().getStringExtra("consultant_image");

        SendToDatabase(cname,cphone,uname,uphone,udescri,udate,image);




    }

    private void SendToDatabase(final String cname, final String cphone, final String uname, final String uphone, final String udescri, final String udate, final String image) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Appointments").child(cname).child(uname).exists())) {
                    Appointment appointment= new Appointment(cname,cphone,uname,uphone,udescri,udate,image);
                    RootRef.child("Appointments").child(uname).child(cname).setValue(appointment)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull final Task<Void> task) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This method will be executed once the timer is over
                                            if (Netcheck.internetChack(BookAppointment.this)) {

                                                if (task.isSuccessful()) {
                                                    FancyToast.makeText(BookAppointment.this, "Congratulations your Appointment has been created.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                                   pDialog.dismiss();
                                                   SetLayout(udescri,udate);

                                                } else {
                                                    pDialog.dismiss();
                                                    FancyToast.makeText(BookAppointment.this, "Network Error: Please try again after some time...", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                                }

                                            } else {
                                                AlertDialog.Builder builder;
                                                builder = new AlertDialog.Builder(BookAppointment.this);
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
                    FancyToast.makeText(BookAppointment.this, "Appointment already exists.", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    pDialog.dismiss();
                    FancyToast.makeText(BookAppointment.this, "Please try again using another date.", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FancyToast.makeText(BookAppointment.this, String.valueOf(databaseError), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                pDialog.dismiss();
            }
        });
    }

    private void SetLayout(String udescri, String udate) {
        datee.setText(udate);
        datee.setEnabled(false);

        description_tv.setEnabled(false);
        description_tv.setText(udescri);
    }
}