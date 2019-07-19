package com.cudpast.rivertourapp.Helper;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("saveUserRiver.php")
    Call<User> insertUser(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("dni") String dni,
            @Query("correo") String correo,
            @Query("numphone") String numphone,
            @Query("uid") String uid);


    @Headers("Content-Type: application/json")
    @GET("loadUserRiver.php")
    Call<User> getUser(@Query("uid") String uid);

    @GET("saveChoferRiver.php")
    Call<Chofer> insertChofer(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("dni") String dni,
            @Query("brevete") String brevete,
            @Query("numphone") String numphone);





}
