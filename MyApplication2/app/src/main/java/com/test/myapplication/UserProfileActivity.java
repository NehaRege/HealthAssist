package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by NehaRege on 10/19/17.
 */
public class UserProfileActivity extends AppCompatActivity {

    private ImageView imageViewPhoto;

    private String photoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();

        photoUrl = intent.getStringExtra("user_photo");

        initializeViews();









    }

    private void initializeViews() {
        imageViewPhoto = (ImageView) findViewById(R.id.user_profile_photo);


        if (photoUrl != null) {

            Glide.with(this)
                    .load(photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageViewPhoto);

        }

    }
}
