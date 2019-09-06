package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.ManifiestoAdapter;
import com.cudpast.rivertourapp.Adapter.PasajeroAdapter;
import com.cudpast.rivertourapp.Adapter.VehiculoAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_MARCA_VEHICULO;
import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_MATRICULA_VEHICULO;
import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_NOMBRE_VEHICULO;
import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_PLACA_VEHICULO;

public class ConsultManiActivity extends AppCompatActivity {

    public static final String TAG = ConsultManiActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ManifiestoAdapter mAdapter;
    private ApiInterface apiInterface;
    List<Manifiesto> mListOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consult_mani);
        pDialog = new ProgressDialog(ConsultManiActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        mListOff = new ArrayList<Manifiesto>();
        obtenerListaManifiestoOnline();
    }

    private void obtenerListaManifiestoOnline() {
        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<List<Manifiesto>> getListaManifiesto = apiInterface.getListManifiesto();
        getListaManifiesto.enqueue(new Callback<List<Manifiesto>>() {
            @Override
            public void onResponse(Call<List<Manifiesto>> call, Response<List<Manifiesto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Manifiesto> mListaManifiesto = response.body();
                    Log.e(TAG, " " + mListaManifiesto);
                    destroyDBManifiesto();
                    for (int i = 0; i < mListaManifiesto.size(); i++) {
                        //
                        String idguia = mListaManifiesto.get(i).getIdGuiaMani();
                        String fechaMani = mListaManifiesto.get(i).getFechaMani();
                        String destinoMani = mListaManifiesto.get(i).getDestinoMani();
                        String vehiculoMani = mListaManifiesto.get(i).getVehiculoMani();
                        String choferMani = mListaManifiesto.get(i).getChoferMani();
                        //
                        updateListManifiesto(idguia, fechaMani, destinoMani, vehiculoMani, choferMani);
                        //
                        String cadena = "\n" + "==== Manifiesto Nº " + i + " ====== " + "\n"
                                + " idguia : " + idguia + "\n"
                                + " fechaMani : " + fechaMani + "\n"
                                + " destinoMani : " + destinoMani + "\n"
                                + " vehiculoMani : " + vehiculoMani + "\n"
                                + " choferMani : " + choferMani + "\n"
                                + "  " + "" + "\n";

                        Log.e(TAG, cadena);
                    }
                    recyclerView = findViewById(R.id.recycler_view_manifiesto);
                    mAdapter = new ManifiestoAdapter(mListaManifiesto);
                    mAdapter.notifyDataSetChanged();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                } else {
                    loadListManifiestoOffline();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Manifiesto>> call, Throwable t) {
                loadListManifiestoOffline();
                pDialog.dismiss();
            }
        });
    }

    private void loadListManifiestoOffline() {
        mListOff.clear();
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListManifiesto(db);

        while (cursor.moveToNext()) {
            Manifiesto manifiesto = new Manifiesto();
            //
            String idguia = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_ID_GUIA));
            String fechaMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_FECHA_MANIFIESTO));
            String destinoMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_MANIFIESTO));
            String vehiculoMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_VEHICULO_MANIFIESTO));
            String choferMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_CHOFER_MANIFIESTO));
            //
            manifiesto.setIdGuiaMani(idguia);
            manifiesto.setFechaMani(fechaMani);
            manifiesto.setDestinoMani(destinoMani);
            manifiesto.setVehiculoMani(vehiculoMani);
            manifiesto.setChoferMani(choferMani);
            //
            mListOff.add(manifiesto);
        }

        recyclerView = findViewById(R.id.recycler_view_manifiesto);
        mAdapter = new ManifiestoAdapter(mListOff);
        mAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        cursor.close();
        mySqliteDB.close();
        Toast.makeText(this, "Esta offline", Toast.LENGTH_SHORT).show();


    }


    private void updateListManifiesto(String idguia, String fechaMani, String destinoMani, String vehiculoMani, String choferMani) {
        try {
            MySqliteDB conn = new MySqliteDB(this);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " + Utils.TABLA_MANIFIESTO + "( " +
                    Utils.CAMPO_ID_GUIA + "," +
                    Utils.CAMPO_FECHA_MANIFIESTO + "," +
                    Utils.CAMPO_DESTINO_MANIFIESTO + "," +
                    Utils.CAMPO_VEHICULO_MANIFIESTO + "," +
                    Utils.CAMPO_CHOFER_MANIFIESTO + ")" +
                    " VALUES ('" +
                    idguia + "','" +
                    fechaMani + "','" +
                    destinoMani + "','" +
                    vehiculoMani + "','" +
                    choferMani + "')";
            db.execSQL(insert);
            db.close();
            Log.e(TAG, " INSERT : updateListManifiesto \n " + insert);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR :  updateListManifiesto");
        }
    }

    private void destroyDBManifiesto() {
        Log.e(TAG, " destroyDBManifiesto :  eliminar  drop table manifiesto ");
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        mySqliteDB.deleteTableManifiesto();
    }


}
