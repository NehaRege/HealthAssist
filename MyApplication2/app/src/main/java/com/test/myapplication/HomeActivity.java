package com.test.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        CustomRvAdapter.OnRecyclerViewItemClickListener {

    private TextView textViewNavHeaderName;
    private TextView textViewNavHeaderEmail;
    private ImageView imageViewPhoto;
    private FloatingActionButton fab;

    private static final int PERMISSION_REQUEST_CODE_CALENDAR = 111;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 112;

    private static final String CALENDAR_PERMISSION = Manifest.permission.WRITE_CALENDAR;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private ArrayList<String> dataList = new ArrayList<>();


    String name;
    String email;
    String photoUrl;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpToolbarAndNavigationDrawer();

        initializeViews();

        setIntent();

        dataList.add("Arizona");
        dataList.add("California");
        dataList.add("New Mexico");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");
        dataList.add("New York");

        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayoutManager);

        rvAdapter = new CustomRvAdapter(dataList,this);

        recyclerView.setAdapter(rvAdapter);

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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_user_profile) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            if(photoUrl!=null) {
                intent.putExtra("user_photo",photoUrl);
            }
            startActivity(intent);

        } else if (id == R.id.nav_book_appointment) {

            Intent intent = new Intent(this,BookAppointmentActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_uber) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                    requestPermissions(permissions, PERMISSION_REQUEST_CODE_LOCATION);
                }

            } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(HomeActivity.this, UberActivity.class);
                startActivity(intent);


            }


        } else if (id == R.id.nav_calendar) {

            if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {
                    // No explanation needed, we can request the permission.
                    String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR};
                    requestPermissions(permissions, PERMISSION_REQUEST_CODE_CALENDAR);


                    // PERMISSION_REQUEST_CODE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

                // Permission is granted, execute code normally since you have the permission.
                // For example, here we are granted the contacts permission so now we can actually access the contacts here.

                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);

            }

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

    }

    private void initializeViews() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        textViewNavHeaderEmail = (TextView) header.findViewById(R.id.nav_header_email);
        textViewNavHeaderName = (TextView) header.findViewById(R.id.nav_header_name);
        imageViewPhoto = (ImageView) header.findViewById(R.id.nav_header_photo);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


    }

    private void setIntent() {

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

            if (photoUrl != null) {

                Glide.with(HomeActivity.this)
                        .load(photoUrl)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewPhoto);

            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

        }

    }

    @TargetApi(23)
    private boolean permissionExists() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < Build.VERSION_CODES.M) {

            // Permissions are already granted during INSTALL TIME for older OS version
            return true;
        }

        int granted = checkSelfPermission(CALENDAR_PERMISSION);
        if (granted == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @TargetApi(23)
    private void requestUserForPermission() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < Build.VERSION_CODES.M) {
            // This OS version is lower then Android M, therefore we have old permission model and should not ask for permission
            return;
        }
        String[] permissions = new String[]{CALENDAR_PERMISSION};
        requestPermissions(permissions, PERMISSION_REQUEST_CODE_CALENDAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CALENDAR:
                if (permissions.length < 0) {
                    return;
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // contacts permission was granted! Let's populate the listview.
                    showCalendar();
                } else {
                    // contactss permission was denied, lets warn the user that we need this permission!
                    Toast.makeText(getApplicationContext(), "You need to grant calendar permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkPermissions(String permissionString) {

//        if (checkSelfPermission(permissionString) != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (shouldShowRequestPermissionRationale(permissionString)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//                // No explanation needed, we can request the permission.
//                String[] permissions = new String[]{permissionString};
//                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
//
//
//                // PERMISSION_REQUEST_CODE is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//
//        } else if (checkSelfPermission(permissionString) == PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is granted, execute code normally since you have the permission.
//            // For example, here we are granted the contacts permission so now we can actually access the contacts here.
//
//            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
//            builder.appendPath("time");
//            ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
//            Intent intent = new Intent(Intent.ACTION_VIEW)
//                    .setData(builder.build());
//            startActivity(intent);
//
//        }

    }

//    private void uberSetup() {
//        uberConfig();
//
//        RideRequestButton rideRequestButton = new RideRequestButton(HomeActivity.this);
//        layout.addView(rideRequestButton);
//        Activity activity = this; // If you're in a fragment you must get the containing Activity!
//        int requestCode = 1234;
//        rideRequestButton.setRequestBehavior(new RideRequestActivityBehavior(activity, requestCode));
//
//// Optional, default behavior is to use current location for pickup
//        RideParameters rideParams = new RideParameters.Builder()
//                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
//                .setPickupLocation(37.775304, -122.417522, "Uber HQ", "1455 Market Street, San Francisco")
//                .setDropoffLocation(37.795079, -122.4397805, "Embarcadero", "One Embarcadero Center, San Francisco")
//                .build();
//        rideRequestButton.setRideParameters(rideParams);
//
//    }
//
//    private void uberConfig() {
//        SessionConfiguration config = new SessionConfiguration.Builder()
//                // mandatory
//                .setClientId("yk6wfbTmXZiXndWmzASMX6MiYT-mI2CQ")
//                // required for enhanced button features
//                .setServerToken("9DSnlyJYB9XE0hW9JrySkjD7rhqLffE5jvgN2993")
//                // required for implicit grant authentication
//                .setRedirectUri("yk6wfbTmXZiXndWmzASMX6MiYT-mI2CQ://uberConnect")
//                // required scope for Ride Request Widget features
//                .setScopes(Arrays.asList(Scope.RIDE_WIDGETS))
//                // optional: set sandbox as operating environment
//                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
//                .build();
//
//        UberSdk.initialize(config);
//    }

    private void showCalendar() {
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(HomeActivity.this, "Clicked on " + dataList.get(position) + " at position " + position, Toast.LENGTH_SHORT).show();

    }
}
