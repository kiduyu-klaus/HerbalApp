package com.kiduyu.joshuaproject.HerbalApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Models.Favourite;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.Objects;

public class HerbsDetails extends AppCompatActivity {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbs_details);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Herbs Details");
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.statuscolor));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statuscolor));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.statuscolor)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pDialog = new ProgressDialog(this);
        String herbsname = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        String disease = getIntent().getStringExtra("disease");
        String descr = getIntent().getStringExtra("description");

        TextView name = findViewById(R.id.details_name);
        name.setText(herbsname);

        TextView desc_tv = findViewById(R.id.details_desr);
        desc_tv.setText(descr);
        TextView disease_tv = findViewById(R.id.text_tap_rate);
        disease_tv.setText(disease);
        ImageView imageView = findViewById(R.id.image_detailskid);
        Glide.with(this).load(image).into(imageView);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void AddToFavourite(View view) {
        pDialog.setTitle("Adding to favourites");
        pDialog.setMessage("Please wait, while we are checking the credentials.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        final String herbsname = getIntent().getStringExtra("name");
        final String image = getIntent().getStringExtra("image");
        final String disease = getIntent().getStringExtra("disease");
        final String descr = getIntent().getStringExtra("description");

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uniqueid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                Favourite favourite = new Favourite(herbsname, disease, descr, image);
                RootRef.child("Favourites").child(uniqueid).child(herbsname).setValue(favourite).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(HerbsDetails.this, "Added to favourites Successfully.", Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.INVISIBLE);
                            pDialog.dismiss();

                        } else {
                            //progressBar.setVisibility(View.INVISIBLE);
                            pDialog.dismiss();
                            Toast.makeText(HerbsDetails.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pDialog.dismiss();
                Toast.makeText(HerbsDetails.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

    }
}