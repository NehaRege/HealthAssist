package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by NehaRege on 9/11/17.
 */
public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "Dashboard";

    TextView textViewName;
    TextView textViewEmail;
    TextView textViewPhoto;
    Button buttonSignOut;

    String name;
    String email;
    String photo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: ");
        name = intent.getStringExtra("user_name");
        email = intent.getStringExtra("user_email");
        photo = intent.getStringExtra("user_photo");

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewPhoto.setText(photo);

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private void initializeViews() {
        textViewName = (TextView) findViewById(R.id.dashboard_name);
        textViewEmail = (TextView) findViewById(R.id.dashboard_email);
        textViewPhoto = (TextView) findViewById(R.id.dashboard_photo);
        buttonSignOut = (Button) findViewById(R.id.button_facebook_signout_dashboard);
    }
}
