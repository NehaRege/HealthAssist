package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by NehaRege on 9/11/17.
 */
public class DashboardActivity extends AppCompatActivity implements CustomRvAdapter.OnRecyclerViewItemClickListener {

    private static final String TAG = "Dashboard";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private ArrayList<String> dataList = new ArrayList<>();


    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhoto;

    private TextView textViewName_G;
    private TextView textViewEmail_G;
    private TextView textViewPhoto_G;

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

        if(intent.hasExtra("user_name_gmail")) {
            name_gmail = intent.getStringExtra("user_name_gmail");
            textViewName_G.setText(name_gmail);
        }

        if(intent.hasExtra("user_email_gmail")) {
            email_gmail = intent.getStringExtra("user_email_gmail");
            textViewEmail_G.setText(email_gmail);
        }

//        dataList.add("Arizona");
//        dataList.add("California");
//        dataList.add("New Mexico");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");
//        dataList.add("New York");

//        rvLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(rvLayoutManager);

//        rvAdapter = new CustomRvAdapter(dataList,this);
//
//        recyclerView.setAdapter(rvAdapter);

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(DashboardActivity.this, "Clicked on " + dataList.get(position) + " at position " + position, Toast.LENGTH_SHORT).show();
    }
}
