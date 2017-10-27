package com.test.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.myapplication.api.ApiService;
import com.test.myapplication.models.appointments.BookAppointment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NehaRege on 10/22/17.
 */
public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BookAppointmentActivity";

    private String currentUserEmail;

    private EditText editTextDoctorName;
    private EditText editTextDoctorEmail;
    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPurpose;
    private EditText editTextLocation;

    private BookAppointment newAppointment;

    private String doctorId;
    private String doctorName;
    private String purpose;
    private String date;
    private String startTime;
    private String endTime;
    private String location;

    private String patientId;
    private String status;

    private ApiService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

//        Intent intent = getIntent();
//        patientId = intent.getStringExtra("book_app_email_id");

        getSharedPrefs();

        initializeViews();

        initializeRetrofit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appointment_activity_submit:

                getAppointmentInfoFromEditText();

                createNewAppointmentObj();

                postReq();

                finish();

                break;
        }
    }

    private void getSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.KEY_SHARED_PREFS_USER_GMAIL, Context.MODE_PRIVATE);
        currentUserEmail = prefs.getString(getString(R.string.shared_pref_gmail), null);
    }

    private void initializeViews() {
        editTextDoctorName = (EditText) findViewById(R.id.appointment_activity_doctor_name);
        editTextDoctorEmail = (EditText) findViewById(R.id.appointment_activity_doctor_email);
        editTextStartTime = (EditText) findViewById(R.id.appointment_activity_start_time);
        editTextEndTime = (EditText) findViewById(R.id.appointment_activity_end_time);
        editTextLocation = (EditText) findViewById(R.id.appointment_activity_location);
        editTextPurpose = (EditText) findViewById(R.id.appointment_activity_reason);
        editTextDate = (EditText) findViewById(R.id.appointment_activity_date);

        Button buttonSubmit = (Button) findViewById(R.id.appointment_activity_submit);
        buttonSubmit.setOnClickListener(this);
    }

    private void initializeRetrofit() {
        String BASE_URL = "https://remote-health-api.herokuapp.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    private void getAppointmentInfoFromEditText() {
        doctorName = editTextDoctorName.getText().toString();
        doctorId = editTextDoctorEmail.getText().toString();
        startTime = editTextStartTime.getText().toString();
        endTime = editTextEndTime.getText().toString();
        location = editTextLocation.getText().toString();
        purpose = editTextPurpose.getText().toString();
        date = editTextDate.getText().toString();
        status = "pending";
        patientId = currentUserEmail;
    }

    private void createNewAppointmentObj() {
        newAppointment = new BookAppointment();
        newAppointment.setDate(date);
        newAppointment.setDoctorId(doctorId);
        newAppointment.setDoctorName(doctorName);
        newAppointment.setEndTime(endTime);
        newAppointment.setLocation(location);
        newAppointment.setPurpose(purpose);
        newAppointment.setStartTime(startTime);
        newAppointment.setPatientId(patientId);
        newAppointment.setStatus(status);
    }

    private void postReq() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Call<BookAppointment> call = service.createNewAppointment(newAppointment);

            call.enqueue(new Callback<BookAppointment>() {
                @Override
                public void onResponse(Call<BookAppointment> call, Response<BookAppointment> response) {

                    try {
                        Log.d(TAG, "*********************** onResponse: Post Success *******************");

                        Toast.makeText(BookAppointmentActivity.this, "Appointment Added Successfully!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BookAppointment> call, Throwable t) {
                    Log.d(TAG, "onFailure: Retrofit call failed");
                    t.printStackTrace();
                }
            });

        } else {
            Log.d(TAG, "getUserInfoApi: Failed : Network problem");
        }
    }

}
