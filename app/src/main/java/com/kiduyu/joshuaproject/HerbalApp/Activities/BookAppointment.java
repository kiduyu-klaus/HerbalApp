package com.kiduyu.joshuaproject.HerbalApp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;
import java.util.Objects;

public class BookAppointment extends AppCompatActivity {
    TextInputEditText inputname,userapp,description,inputphone,adress;
    private static TextInputEditText datee;
    Button saveAppointment;
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
                DialogFragment newfragment = new SelectDateFragment();
                newfragment.show(getSupportFragmentManager(), "DatePicker");

            }

        });
    }
        public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                int yy= calendar.get(Calendar.YEAR);
                int mm=calendar.get(Calendar.MONTH);
                int dd=calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(),this,yy,mm,dd);
            }

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datee.setText(month+"/"+dayOfMonth+"/"+year);

            }


        }
}