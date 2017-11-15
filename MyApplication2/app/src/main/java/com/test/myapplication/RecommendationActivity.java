package com.test.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NehaRege on 11/14/17.
 */
public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextSym1;
    private EditText editTextSym2;
    private EditText editTextSym3;

    private Button buttonSubmit;

    private String sym1;
    private String sym2;
    private String sym3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        initializeViews();

    }

    private void initializeViews() {
        editTextSym1 = (EditText)findViewById(R.id.recommendations_activity_symp1);
        editTextSym2 = (EditText)findViewById(R.id.recommendations_activity_symp2);
        editTextSym3 = (EditText)findViewById(R.id.recommendations_activity_symp3);

        buttonSubmit = (Button) findViewById(R.id.recommendations_activity_submit);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.recommendations_activity_submit :



                break;
        }

    }

    private void getRecommendations() {

    }
}
