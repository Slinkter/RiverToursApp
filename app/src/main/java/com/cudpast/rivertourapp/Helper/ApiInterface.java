package com.cudpast.rivertourapp.Helper;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Content-Type: application/json")


    //Metodos  de 9009Webhost

    @GET("saveUserRiver.php")
    Call<User> insertUser(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("dni") String dni,
            @Query("correo") String correo,
            @Query("numphone") String numphone,
            @Query("uid") String uid);


    @GET("loadUserRiver.php")
    Call<User> getUser(@Query("uid") String uid);

    @GET("saveChoferRiver.php")
    Call<Chofer> insertChofer(
            @Query("nameChofer") String nameChofer,
            @Query("lastChofer") String lastChofer,
            @Query("dniChofer") String dniChofer,
            @Query("brevete") String brevete,
            @Query("numphone") String numphone);


}
