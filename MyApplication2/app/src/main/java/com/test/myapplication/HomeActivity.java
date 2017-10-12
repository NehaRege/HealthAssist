package com.test.myapplication;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewNavHeaderName;
    private TextView textViewNavHeaderEmail;
    private ImageView imageViewPhoto;
    private FloatingActionButton fab;

    String name;
    String email;
    String photoUrl;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpToolbarAndNavigationDrawer();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_calendar) {
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            startActivity(intent);

//            long eventID = 200;
//
//            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
//            Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
//            startActivity(intent);
        } else if (id == R.id.logout_drawer) {

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setUpToolbarAndNavigationDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        textViewNavHeaderEmail = (TextView) header.findViewById(R.id.nav_header_email);
        textViewNavHeaderName = (TextView) header.findViewById(R.id.nav_header_name);
        imageViewPhoto = (ImageView) header.findViewById(R.id.nav_header_photo);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        setIntentNameEmail();

    }

    private void setIntentNameEmail() {

        intent = getIntent();

        if (intent.hasExtra("user_name")) {
            name = intent.getStringExtra("user_name");
            textViewNavHeaderName.setText(name);
        }

        if (intent.hasExtra("user_email")) {
            email = intent.getStringExtra("user_email");
            textViewNavHeaderEmail.setText(email);
        }

        if (intent.hasExtra("user_email_gmail")) {
            email = intent.getStringExtra("user_email_gmail");
            textViewNavHeaderEmail.setText(email);
        }

        if (intent.hasExtra("user_name_gmail")) {
            name = intent.getStringExtra("user_name_gmail");
            textViewNavHeaderName.setText(name);
        }

        if (intent.hasExtra("user_photo_gmail")) {
            photoUrl = intent.getStringExtra("user_photo_gmail");

            if(photoUrl!=null) {


                Glide.with(HomeActivity.this)
                        .load(photoUrl)
                        .into(imageViewPhoto);

//                Glide.with(context)
//                        .load("http://via.placeholder.com/300.png")
//                        .into(ivImg);
            }
        }



    }
}
