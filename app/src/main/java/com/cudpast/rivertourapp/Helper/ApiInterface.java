package com.cudpast.rivertourapp.Helper;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.ChoferList;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.User;
import com.cudpast.rivertourapp.Model.Vehiculo;

import java.util.List;

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


    @GET("loadUserRiver.php")
    Call<User> getUser(@Query("uid") String uid);

    ////////////////////////////
    @GET("saveChoferRiver.php")
    Call<Chofer> insertChofer(
            @Query("nameChofer") String nameChofer,
            @Query("lastChofer") String lastChofer,
            @Query("dniChofer") String dniChofer,
            @Query("brevete") String brevete,
            @Query("numphone") String numphone);

    @GET("loadChoferRiver.php")
    Call<List<Chofer>> getListChofer();

    ///////////////////////////

    @GET("saveVehiculoRiver.php")
    Call<Vehiculo> insertVehiculo(
            @Query("nombreVehiculo") String nombreVehiculo,
            @Query("marcaVehiculo") String marcaVehiculo,
            @Query("matriculaVehiculo") String matriculaVehiculo,
            @Query("placaVehiculo") String placaVehiculo);

    @GET("loadVehiculoRiver.php")
    Call<List<Vehiculo>> getListVehiculo();

    ///////////////////////////
    @GET("saveManifiestoRiver.php")
    Call<Manifiesto> insertManifiesto(
            @Query("idGuiaMani") String idGuiaMani,
            @Query("fechaMani") String fechaMani,
            @Query("destinoMani") String destinoMani,
            @Query("vehiculoMani") String vehiculoMani,
            @Query("choferMani") String choferMani);

}
