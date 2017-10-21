package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by NehaRege on 9/11/17.
 */
public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "Dashboard";

    TextView textViewName;
    TextView textViewEmail;
    TextView textViewPhoto;

    TextView textViewName_G;
    TextView textViewEmail_G;
    TextView textViewPhoto_G;

    String name;
    String email;

    String name_gmail;
    String email_gmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();

        Intent intent = getIntent();

        if(intent.hasExtra("user_name")) {
            name = intent.getStringExtra("user_name");
            textViewName.setText(name);
        }

        if(intent.hasExtra("user_email")) {
            email = intent.getStringExtra("user_email");
            textViewEmail.setText(email);
        }

//        if(intent.hasExtra("user_photo")) {
//            photo = intent.getStringExtra("user_photo");
//            textViewPhoto.setText(photo);
//        }

        if(intent.hasExtra("user_name_gmail")) {
            name_gmail = intent.getStringExtra("user_name_gmail");
            textViewName_G.setText(name_gmail);
        }

//        if(intent.hasExtra("user_photo_gmail")) {
//            photo_gmail = intent.getStringExtra("user_photo_gmail");
//            textViewPhoto_G.setText(photo_gmail);
//        }

        if(intent.hasExtra("user_email_gmail")) {
            email_gmail = intent.getStringExtra("user_email_gmail");
            textViewEmail_G.setText(email_gmail);
        }









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_name) {
            Toast.makeText(DashboardActivity.this, "action_name", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.logout) {

            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        textViewName = (TextView) findViewById(R.id.dashboard_name);
        textViewEmail = (TextView) findViewById(R.id.dashboard_email);
        textViewPhoto = (TextView) findViewById(R.id.dashboard_photo);

        textViewName_G = (TextView) findViewById(R.id.dashboard_name_gmail);
        textViewEmail_G = (TextView) findViewById(R.id.dashboard_email_gmail);
        textViewPhoto_G = (TextView) findViewById(R.id.dashboard_photo_gmail);
//        buttonSignOut = (Button) findViewById(R.id.button_facebook_signout_dashboard);
    }
}
