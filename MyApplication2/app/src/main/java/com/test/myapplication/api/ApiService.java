package com.test.myapplication.api;

import com.test.myapplication.models.appointments.Appointment;
import com.test.myapplication.models.user.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by NehaRege on 10/25/17.
 */
public interface ApiService {

    @GET("/api/users/{emailId}")
    Call<User> getUser(@Path("emailId") String emailId);

//    https://remote-health-api.herokuapp.com/api/appointments/patient/neharege28@gmail.com

    @GET("/api/appointments/patient/{emailId}")
    Call<ArrayList<Appointment>> getAppointments(@Path("emailId") String emailId);

//    @GET("/api/searchtypes/{Id}/filters")
//    Call<FilterResponse> getFilterList(@Path("Id") long customerId,
//                                       @Query("Type") String responseType,
//                                       @Query("SearchText") String searchText);


}
