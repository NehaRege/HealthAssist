package com.test.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.myapplication.api.ApiService;
import com.test.myapplication.models.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NehaRege on 10/19/17.
 */
public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static String BASE_URL_USER = "https://remote-health-api.herokuapp.com";
//    private static String BASE_URL_USER = "https://remote-health-api.herokuapp.com/api";
//    private static String BASE_URL_USER = "https://remote-health-api.herokuapp.com/api/users/";

    String currentUserEmail;

    private ImageView imageViewPhoto;

    private TextView textViewName;
    private TextView textViewGender;
    private TextView textViewLocation;
    private TextView textViewDob;
    private TextView textViewEthnicity;
    private TextView textViewAge;
    private TextView textViewUserType;
    private TextView textViewEmail;
    private TextView textViewNumber;
    private TextView textViewDoctor;

    private RadioGroup radioGroupAlcohol;
    private RadioGroup radioGroupBleeding;
    private RadioGroup radioGroupCancer;
    private RadioGroup radioGroupDiabetes;
    private RadioGroup radioGroupEpilepsy;
    private RadioGroup radioGroupBp;
    private RadioGroup radioGroupHeart;
    private RadioGroup radioGroupMigraine;
    private RadioGroup radioGroupGender;

    private RadioButton radioButtonAlcohol;
    private RadioButton radioButtonBleeding;
    private RadioButton radioButtonCancer;
    private RadioButton radioButtonDiabetes;
    private RadioButton radioButtonEpilepsy;
    private RadioButton radioButtonBp;
    private RadioButton radioButtonHeart;
    private RadioButton radioButtonMigraine;
    private RadioButton radioButtonIsFemale;

    private String photoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        photoUrl = intent.getStringExtra("user_photo");
        currentUserEmail = intent.getStringExtra("user_profile_email");

        initializeViews();

        setUserPhoto();

        getUserInfoApi("neharege28@gmail.com");
        getUserInfoApi(currentUserEmail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {

        imageViewPhoto = (ImageView) findViewById(R.id.user_profile_activity_photo);

        textViewName = (TextView) findViewById(R.id.user_profile_activity_name);
//        textViewGender = (TextView) findViewById(R.id.user_profile_activity_gender);
        radioGroupGender = (RadioGroup) findViewById(R.id.profile_activity_radio_group_gender);
        textViewLocation = (TextView) findViewById(R.id.user_profile_activity_location);
        textViewDob = (TextView) findViewById(R.id.user_profile_activity_dob);
        textViewEthnicity = (TextView) findViewById(R.id.user_profile_activity_ethnicity);
        textViewAge = (TextView) findViewById(R.id.user_profile_activity_age);
        textViewUserType = (TextView) findViewById(R.id.user_profile_activity_user_type);
        textViewEmail = (TextView) findViewById(R.id.user_profile_activity_email);
        textViewNumber = (TextView) findViewById(R.id.user_profile_activity_number);
        textViewDoctor = (TextView) findViewById(R.id.user_profile_activity_doctor);

        radioGroupAlcohol = (RadioGroup) findViewById(R.id.profile_activity_radio_group_alcohol);
        radioGroupBleeding = (RadioGroup) findViewById(R.id.profile_activity_radio_group_bleeding);
        radioGroupCancer = (RadioGroup) findViewById(R.id.profile_activity_radio_group_cancer);
        radioGroupDiabetes = (RadioGroup) findViewById(R.id.profile_activity_radio_group_diabetes);
        radioGroupEpilepsy = (RadioGroup) findViewById(R.id.profile_activity_radio_group_epilepsy);
        radioGroupBp = (RadioGroup) findViewById(R.id.profile_activity_radio_group_bp);
        radioGroupHeart = (RadioGroup) findViewById(R.id.profile_activity_radio_group_heart);
        radioGroupMigraine = (RadioGroup) findViewById(R.id.profile_activity_radio_group_migraine);

    }

    private void setUserPhoto() {
        if (photoUrl != null) {
            Glide.with(this)
                    .load(photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageViewPhoto);
        }

    }

    private void setUserDetails(User currentUserInfo) {
        textViewName.setText(currentUserInfo.getName().getFirstName());
        textViewLocation.setText(currentUserInfo.getAddress().getCity());
        textViewEthnicity.setText(currentUserInfo.getMedicalRecord().getEthnicity());
        String age = Integer.toString(currentUserInfo.getMedicalRecord().getAge());
        textViewAge.setText(age);
        textViewUserType.setText(currentUserInfo.getUserType());
        textViewEmail.setText(currentUserInfo.getId());
        textViewNumber.setText(currentUserInfo.getPhoneNumber());
        textViewDoctor.setText(currentUserInfo.getDoctorId());

    }

    private void getUserInfoApi(String emailId) {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_USER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = retrofit.create(ApiService.class);

            Call<User> call = service.getUser(emailId);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    try {

                        Log.d(TAG, "*********************** onResponse: Success *******************");

                        Log.d(TAG, "onResponse: ethnicity = " + response.body().getMedicalRecord().getEthnicity());

                        setUserDetails(response.body());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();

                    Log.d(TAG, "onFailure: Retrofit call failed: ");

                }
            });


        } else {

            Log.d(TAG, "getUserInfoApi: Failed : Network problem");
        }

    }

}
