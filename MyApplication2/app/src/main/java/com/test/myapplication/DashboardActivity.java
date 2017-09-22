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
//    Button buttonSignOut;

    String name;
    String email;
    String photo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();

//        if(getActionBar() != null) {
//            Intent i = new Intent();
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//            setResult(RESULT_OK, i);
//            finish();
//        }

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: ");
        name = intent.getStringExtra("user_name");
        email = intent.getStringExtra("user_email");
        photo = intent.getStringExtra("user_photo");

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewPhoto.setText(photo);

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
//        buttonSignOut = (Button) findViewById(R.id.button_facebook_signout_dashboard);
    }
}
