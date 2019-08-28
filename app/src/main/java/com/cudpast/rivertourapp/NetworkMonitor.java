package com.cudpast.rivertourapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkMonitor extends BroadcastReceiver {

    ApiInterface apiInterface;
    public static final String TAG = NetworkMonitor.class.getSimpleName();

    // Sobre escribir método :
    @Override
    public void onReceive(Context context, Intent intent) {

        if (checkNetworkConnection(context)) {
            //1.Obtener lista de Manifiesto
            MySqliteDB conn = new MySqliteDB(context);
            SQLiteDatabase db = conn.getWritableDatabase();
            Cursor cursor = conn.getListManifiesto(db);
            //2.Recoger la lista de maniesto , si sync = 0  insertar remoto
            while (cursor.moveToNext()) {
                int sync_status = cursor.getInt(cursor.getColumnIndex(Utils.CAMPO_SYNC_STATUS_MANIFIESTO));
                if (sync_status == Utils.SYNC_STATUS_OK_MANIFIESTO) {
                    //sync = 1;
                    Log.e(TAG, "SYNC_STATUS_OK_MANIFIESTO");
                } else if (sync_status == Utils.SYNC_STATUS_FAILIDE_MANIFIESTO) {
                    // Obtener en cada fila si tiene sync = 0 ;
                    Log.e(TAG, "SYNC_STATUS_FAILIDE_MANIFIESTO");
                    String idGuiaMani = "";
                    String fechaMani = "";
                    String destinoMani = "";
                    String vehiculoMani = "";
                    String choferMani = "";
                    //
                    Manifiesto manifiesto = new Manifiesto();
                    manifiesto.setIdGuiaMani(idGuiaMani);
                    manifiesto.setFechaMani(fechaMani);
                    manifiesto.setDestinoMani(destinoMani);
                    manifiesto.setVehiculoMani(vehiculoMani);
                    manifiesto.setChoferMani(choferMani);
                    //
                    saveManifiestoOnline(manifiesto, context, conn);
                    //
                }
            }
        }
    }


    private void saveManifiestoOnline(final Manifiesto manifiesto, final Context context, final MySqliteDB conn) {

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        //3.Insertar Manifesto
        Call<Manifiesto> manifiestoOnline;
        manifiestoOnline = apiInterface.insertManifiesto(manifiesto.getIdGuiaMani(), manifiesto.getFechaMani(), manifiesto.getDestinoMani(), manifiesto.getVehiculoMani(), manifiesto.getChoferMani());
        manifiestoOnline.enqueue(new Callback<Manifiesto>() {
            @Override
            public void onResponse(Call<Manifiesto> call, Response<Manifiesto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        Log.e(TAG, "onResponse : Insert Manifiesto ");
                        // obtener lista de pasajero de la guia  con el sync = 0 pasar su idGuia;
                        ArrayList<Pasajero> mListPasajero = myGetListPasajeroByIdGuia(context, manifiesto.getIdGuiaMani());
                        for (int i = 0; i < mListPasajero.size(); i++) {
                            Call<Pasajero> pasajeroInsert;
                            pasajeroInsert = apiInterface.insertPasajero(
                                    mListPasajero.get(i).getNombrePasajero(),
                                    mListPasajero.get(i).getApellidoPasajero(),
                                    mListPasajero.get(i).getEdadPasajero(),
                                    mListPasajero.get(i).getOcupacionPasajero(),
                                    mListPasajero.get(i).getNacionalidadPasajero(),
                                    mListPasajero.get(i).getNumBoleta(),
                                    mListPasajero.get(i).getDniPasajero(),
                                    mListPasajero.get(i).getDestinoPasajero(),
                                    manifiesto.getIdGuiaMani());
                            //4.Insertar Pasajero
                            pasajeroInsert.enqueue(new Callback<Pasajero>() {
                                @Override
                                public void onResponse(Call<Pasajero> call, Response<Pasajero> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Boolean success = response.body().getSuccess();
                                        if (success) {
                                            // 5. Actualizar el sync = 1(falta)
                                            Log.e(TAG, "onResponse : Insert Pasajero ");
                                            conn.myUpdateManifiesto(manifiesto.getIdGuiaMani(), Utils.SYNC_STATUS_OK_MANIFIESTO, conn.getWritableDatabase());
                                            context.sendBroadcast(new Intent(Utils.UI_UPDATE_BROADCAST));
                                        } else {
                                            Log.e(TAG, " onResponse : Insert no Pasajero ");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Pasajero> call, Throwable t) {
                                    Log.e(TAG, " onFailure :" + t.getMessage());
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Manifiesto> call, Throwable t) {
                Log.e(TAG, " onFailure :  " + t.getMessage());
            }
        });

    }


    private ArrayList<Pasajero> myGetListPasajeroByIdGuia(Context context, String idGuia) {

        ArrayList<Pasajero> mLista = new ArrayList<>();

        MySqliteDB mySqliteDB = new MySqliteDB(context);
        SQLiteDatabase database = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListPasajero(database);

        while (cursor.moveToNext()) {
            String idGuiaPasajero = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_GUIAID_PASAJERO));
            if (idGuiaPasajero == idGuia) {

                String nombre = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NOMBRE_PASAJERO));
                String edad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_EDAD_PASAJERO));
                String ocupacion = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_OCUPACION_PASAJERO));
                String nacionalidad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NACIONALIDAD_PASAJERO));
                String numBoleta = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NUMBOLETA_PASAJERO));
                String dni = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DNI_PASAJERO));
                String destino = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_PASAJERO));
                mLista.add(new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino));
            }

        }
        cursor.close();
        mySqliteDB.close();
        return mLista;
    }


    public boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    // Revisar

    /*
    private Manifiesto getManifiestoById() {
        //1.Conexion
        MySqliteDB conn = new MySqliteDB(this);
        //2.Escribir en la database
        SQLiteDatabase db = conn.getWritableDatabase();
        //3.Código
        String sql = "select * from " + Utils.TABLA_MANIFIESTO + " where " + Utils.CAMPO_ID_GUIA + " = " + "'" + idguiaManifiesto + "'";
        Log.e("getManifeisto() ", sql);
        Cursor c = db.rawQuery(sql, null);
        //
        Manifiesto manifiesto = new Manifiesto();
        if (c.moveToFirst()) {
            do {
                //
                String CAMPO_ID_GUIA = c.getString(1);
                String CAMPO_FECHA_MANIFIESTO = c.getString(2);
                String CAMPO_DESTINO_MANIFIESTO = c.getString(3);
                String CAMPO_VEHICULO_MANIFIESTO = c.getString(4);
                String CAMPO_CHOFER_MANIFIESTO = c.getString(5);
                int CAMPO_SYNC_STATUS_MANIFIESTO = c.getInt(6);
                //
                manifiesto.setIdGuiaMani(CAMPO_ID_GUIA);
                manifiesto.setFechaMani(CAMPO_FECHA_MANIFIESTO);
                manifiesto.setDestinoMani(CAMPO_DESTINO_MANIFIESTO);
                manifiesto.setVehiculoMani(CAMPO_VEHICULO_MANIFIESTO);
                manifiesto.setChoferMani(CAMPO_CHOFER_MANIFIESTO);
                manifiesto.setSync_status(CAMPO_SYNC_STATUS_MANIFIESTO);
                //
                Log.e("getManifiestoById", "\n" +
                        CAMPO_ID_GUIA + " \n" +
                        CAMPO_FECHA_MANIFIESTO + " \n" +
                        CAMPO_DESTINO_MANIFIESTO + " \n" +
                        CAMPO_VEHICULO_MANIFIESTO + " \n" +
                        CAMPO_CHOFER_MANIFIESTO + " \n" +
                        CAMPO_SYNC_STATUS_MANIFIESTO + " \n");

            } while (c.moveToNext());
        }
        c.close();
        //5.Cerrar conexion
        db.close();
        return manifiesto;
    }

    */


}
