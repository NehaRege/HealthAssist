package com.test.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by NehaRege on 10/22/17.
 */
public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextDoctor;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextReason;
    private EditText editTextLocation;

    private Button buttonSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initializeViews();
    }

    private void initializeViews() {
        editTextDoctor = (EditText) findViewById(R.id.appointment_activity_doctor);
        editTextTime = (EditText) findViewById(R.id.appointment_activity_time);
        editTextLocation = (EditText) findViewById(R.id.appointment_activity_location);
        editTextReason = (EditText) findViewById(R.id.appointment_activity_reason);
        editTextDate = (EditText) findViewById(R.id.appointment_activity_date);

        buttonSubmit = (Button) findViewById(R.id.appointment_activity_submit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.appointment_activity_submit:

                Toast.makeText(BookAppointmentActivity.this, "Submitted !", Toast.LENGTH_SHORT).show();

                break;

        }

    }
}
