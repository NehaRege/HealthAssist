package com.test.myapplication;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.test.myapplication.api.ApiService;
import com.test.myapplication.fragments.DatePickerFragment;
import com.test.myapplication.models.appointments.BookAppointment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NehaRege on 10/22/17.
 */
public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "BookAppointmentActivity";

    private String currentUserEmail;

    private EditText editTextDoctorName;
    private EditText editTextDoctorEmail;
//    private EditText editTextDate;
    //    private EditText editTextStartTime;
//    private EditText editTextEndTime;
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

    private TimePicker timePickerStartTime;
    private TimePicker timePickerEndTime;

    private TextView buttonDate;


//    private Spinner spinnerHr;
//    private Spinner spinnerMin;

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

                Toast.makeText(getApplicationContext(), "Button clicked: start time is: " + startTime, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Button clicked: end time is: " + endTime, Toast.LENGTH_SHORT).show();

                getAppointmentInfoFromEditText();

                createNewAppointmentObj();

                Log.d(TAG, "onClick: making post req");


                postReq();

                finish();

                break;

            case R.id.appointment_activity_date_button:

                showDatePickerDialog(view);

                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void getSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.KEY_SHARED_PREFS_USER_GMAIL, Context.MODE_PRIVATE);
        currentUserEmail = prefs.getString(getString(R.string.shared_pref_gmail), null);
    }

    private void initializeViews() {
        editTextDoctorName = (EditText) findViewById(R.id.appointment_activity_doctor_name);
        editTextDoctorEmail = (EditText) findViewById(R.id.appointment_activity_doctor_email);
//        editTextStartTime = (EditText) findViewById(R.id.appointment_activity_start_time);
//        editTextEndTime = (EditText) findViewById(R.id.appointment_activity_end_time);
        editTextLocation = (EditText) findViewById(R.id.appointment_activity_location);
        editTextPurpose = (EditText) findViewById(R.id.appointment_activity_reason);
//        editTextDate = (EditText) findViewById(R.id.appointment_activity_date);

        buttonDate = (TextView) findViewById(R.id.appointment_activity_date_button);

        timePickerStartTime = (TimePicker) findViewById(R.id.appointment_activity_start_time_picker);
        timePickerEndTime = (TimePicker) findViewById(R.id.appointment_activity_end_time_picker);
        timePickerStartTime.setIs24HourView(true);
        timePickerEndTime.setIs24HourView(true);

        timePickerStartTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                startTime = hourOfDay + ":" + minute;
            }
        });

        timePickerEndTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                endTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            }
        });


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
//        startTime = editTextStartTime.getText().toString();
//        endTime = editTextEndTime.getText().toString();
        location = editTextLocation.getText().toString();
        purpose = editTextPurpose.getText().toString();
//        date = editTextDate.getText().toString();
        status = "pending";

        patientId = currentUserEmail;
//        patientId = "neharege28@gmail.com";
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

        Log.d(TAG, "createNewAppointmentObj: obj = " + newAppointment.toString());

    }

    private void postReq() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Call<BookAppointment> call = service.createNewAppointment(newAppointment);

            Log.d(TAG, "postReq: appointment obj = " + newAppointment.toString());

            call.enqueue(new Callback<BookAppointment>() {
                @Override
                public void onResponse(Call<BookAppointment> call, Response<BookAppointment> response) {

                    try {
                        Log.d(TAG, "*********************** onResponse: Post Appointment Success *******************");

                        Toast.makeText(BookAppointmentActivity.this, "Appointment added successfully! ", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "onResponse: purpose = " + response.message());

                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception");
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);

    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        date = dateFormat.format(calendar.getTime());

        buttonDate.setText(date);

    }
}
