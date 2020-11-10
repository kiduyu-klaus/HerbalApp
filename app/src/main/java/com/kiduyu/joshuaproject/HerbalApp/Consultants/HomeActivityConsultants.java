package com.kiduyu.joshuaproject.HerbalApp.Consultants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.kiduyu.joshuaproject.HerbalApp.Account.LoginActivity;
import com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments.Consultant_Approve_Fragment;
import com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments.Home_Fragment;
import com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments.MyAppointments_Fragment;
import com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments.MyChats_Fragment;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.k_vet.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivityConsultants extends AppCompatActivity {
    private DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_consultants);



        toolbar=findViewById(R.id.vet_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));



        drawer= findViewById(R.id.vet_drawer_layout);
        NavigationView navigationView= findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView user= headerView.findViewById(R.id.nav_header_fullname);
        TextView phone= headerView.findViewById(R.id.nav_header_email);
        CircleImageView imageView= headerView.findViewById(R.id.user_profile_image);

        user.setText(Prevalent.currentOnlineUser.getFname());
        phone.setText(Prevalent.currentOnlineUser.getPhone());



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                switch (Item.getItemId()) {
                    case R.id.user_nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,
                                new Home_Fragment()).commit();



                        break;
                    case R.id.user_nav_approveApp:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,
                                new Consultant_Approve_Fragment()).commit();
                        toolbar.setTitle("Approve Appointments");



                        break;
                    case R.id.user_nav_my_appointments:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,
                                new MyAppointments_Fragment()).commit();
                        toolbar.setTitle("my Appointments");



                        break;
                    case R.id.user_nav_chats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,
                                new MyChats_Fragment()).commit();
                        toolbar.setTitle("My Chats");



                        break;
                    case R.id.customer_nav_signout:
                        Signout();

                        break;


                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState== null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_doctor,
                    new Home_Fragment()).commit();
            navigationView.setCheckedItem(R.id.user_nav_home);}
    }

    private void Signout() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }}
}