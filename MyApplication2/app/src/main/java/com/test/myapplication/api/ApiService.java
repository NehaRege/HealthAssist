package com.test.myapplication.api;

import com.test.myapplication.models.appointments.Appointment;
import com.test.myapplication.models.appointments.BookAppointment;
import com.test.myapplication.models.user.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by NehaRege on 10/25/17.
 */
public interface ApiService {

    @GET("/api/users/{emailId}")
    Call<User> getUser(@Path("emailId") String emailId);

    @GET("/api/appointments/patient/{emailId}")
    Call<ArrayList<Appointment>> getAppointments(@Path("emailId") String emailId);

    @POST("/api/appointments")
    Call<BookAppointment> createNewAppointment(@Body BookAppointment newAppointment);


}
