package com.test.myapplication.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;


import com.test.myapplication.CustomRvAdapter;
import com.test.myapplication.R;
import com.test.myapplication.api.ApiService;
import com.test.myapplication.models.appointments.Appointment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NehaRege on 10/26/17.
 */
public class ApprovedFragment extends Fragment implements CustomRvAdapter.OnRecyclerViewItemClickListener {

    private static final String TAG = "ApprovedFragment";
    private static String BASE_URL_APPOINTMENT = "https://remote-health-api.herokuapp.com";

    private ArrayList<Appointment> dataListAppointment = new ArrayList<>();

    String currentUserEmail;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public ApprovedFragment() {
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_approved_appointments, container, false);

        View rootView = inflater.inflate(R.layout.fragment_approved_appointments, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        rvLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rvLayoutManager);

        loadAppointments("neharege28@gmail.com");

        rvAdapter = new CustomRvAdapter(dataListAppointment, this );

        recyclerView.setAdapter(rvAdapter);

        return rootView;

    }


    @Override
    public void onItemClick(int position) {

    }

    private void loadAppointments(String emailId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_APPOINTMENT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<ArrayList<Appointment>> call = service.getAppointments(emailId);

        call.enqueue(new Callback<ArrayList<Appointment>>() {
            @Override
            public void onResponse(Call<ArrayList<Appointment>> call, Response<ArrayList<Appointment>> response) {

                try {

                    Log.d(TAG, "----------------onResponse: Success - Appointments -------------------");

                    dataListAppointment.clear();

//                    dataListAppointment = response.body();

                    dataListAppointment.addAll(response.body());

                    rvAdapter.notifyDataSetChanged();

//                    rvAdapter = new CustomRvAdapter(dataListAppointment, );
//
//                    recyclerView.setAdapter(rvAdapter);

                    Log.d(TAG, "onResponse: data list body = " + response.body().size());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Appointment>> call, Throwable t) {
                t.printStackTrace();

                Log.d(TAG, "onFailure: Retrofit call failed: ");
            }
        });
    }
}
