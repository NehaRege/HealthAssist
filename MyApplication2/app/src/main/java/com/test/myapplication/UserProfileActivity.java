package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by NehaRege on 10/19/17.
 */
public class UserProfileActivity extends AppCompatActivity {

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

        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        photoUrl = intent.getStringExtra("user_photo");

        initializeViews();

        setUserPhoto();

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
}
