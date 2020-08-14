package com.kiduyu.joshuaproject.HerbalApp.Home;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kiduyu.joshuaproject.HerbalApp.StatusBar.StatusBar;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.ChatsFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.ConsultantsFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.HerbsFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.HomeFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.PaymentsFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.ProfileFragment;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.TipsFragment;
import com.kiduyu.joshuaproject.k_vet.BuildConfig;
import com.kiduyu.joshuaproject.k_vet.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView txtActiontitle;
    CircleImageView circleImageView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_home);

        txtActiontitle = findViewById(R.id.txt_actiontitle);
        drawerLayout = findViewById(R.id.drawer_layout);
        circleImageView = findViewById(R.id.profile_image_message_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        relativeLayout= findViewById(R.id.layoutid);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void callFragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack("adds").commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void ClickNavigation(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressedd();
                break;
            case R.id.lvl_home:
                txtActiontitle.setText("Home");
                Glide.with(this).load(R.drawable.ic_home).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new HomeFragment();
                callFragment(fragment);
                break;
            case R.id.myprofile:
                txtActiontitle.setText("Profile");
                Glide.with(this).load(R.drawable.ic_profile).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new ProfileFragment();
                callFragment(fragment);
                break;
            case R.id.herbs:
                txtActiontitle.setText("Herbs Details");
                Glide.with(this).load(R.drawable.ic_herb).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new HerbsFragment();
                callFragment(fragment);
                break;
            case R.id.chats:
                txtActiontitle.setText("All My Chats");
                Glide.with(this).load(R.drawable.ic_chat).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new ChatsFragment();
                callFragment(fragment);
                break;
            case R.id.consultants:
                txtActiontitle.setText("All Consultants");
                Glide.with(this).load(R.drawable.ic_doctor).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new ConsultantsFragment();
                callFragment(fragment);

                break;
            case R.id.Tips:
                txtActiontitle.setText("Herbal Tips");
                Glide.with(this).load(R.drawable.ic_tip).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new TipsFragment();
                callFragment(fragment);
                break;
            case R.id.payments:
                txtActiontitle.setText("Buy Goods");
                Glide.with(this).load(R.drawable.ic_card_giftcard_white_24dp).into(circleImageView);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFF3F0F0"));
                fragment = new PaymentsFragment();
                callFragment(fragment);

                break;
            case R.id.logout:

                break;

            case R.id.img_noti:
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                break;
            case R.id.share:
                shareApp();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private void onBackPressedd() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

}