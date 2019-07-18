package com.cudpast.rivertourapp.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    public static final String URL = "http://appretrofitnote.000webhostapp.com";
    public static Retrofit conexion = null;

    public static Retrofit getApiRetrofitConexion() {

        if (conexion == null) {
            conexion = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return conexion;
    }
}
