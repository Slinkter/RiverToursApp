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
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.VehiculoAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
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

public class ConsulVehiculoActivity extends AppCompatActivity {

    public static final String TAG = ConsulVehiculoActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private VehiculoAdapter vAdapter;
    private ApiInterface apiInterface;
    List<Vehiculo> mListOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consul_vehiculo);
        //
        pDialog = new ProgressDialog(ConsulVehiculoActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        //
        mListOff = new ArrayList<Vehiculo>();
        loadListVehiculoOnline();

    }

    // Obtener la lista de choferes desde remote DB
    private void loadListVehiculoOnline() {

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<List<Vehiculo>> getListaChofer = apiInterface.getListVehiculo();
        getListaChofer
                .enqueue(new Callback<List<Vehiculo>>() {
                    @Override
                    public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // se obtiene la lista de vehiculo remotamente
                            List<Vehiculo> mList = response.body();
                            destroyDBVehiculo();
                            // update la lista de vehiculo en sqlite
                            for (int i = 0; i < mList.size(); i++) {
                                String nombrevehiculo = mList.get(i).getNombrevehiculo();
                                String marcaVehiculo = mList.get(i).getMarcaVehiculo();
                                String matriculaVehiculo = mList.get(i).getMatriculaVehiculo();
                                String placaVehiculo = mList.get(i).getPlacaVehiculo();

                                updateListVehiculoFromMysql(nombrevehiculo, marcaVehiculo, matriculaVehiculo, placaVehiculo);

                                String cadena = "==== Vehiculo Nº " + i + " ====== " + "\n"
                                        + " nombrevehiculo : " + nombrevehiculo + "\n"
                                        + " marcaVehiculo : " + marcaVehiculo + "\n"
                                        + " matriculaVehiculo : " + matriculaVehiculo + "\n"
                                        + " placaVehiculo : " + placaVehiculo + "\n"
                                        + "  " + "" + "\n";

                                Log.e(TAG, cadena);
                                //
                            }
                            // mostrar lista de vehiculos remotament
                            recyclerView = findViewById(R.id.recycler_view_vehiculo);
                            vAdapter = new VehiculoAdapter(mList);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(eLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(vAdapter);
                            pDialog.dismiss();
                        } else {
                            loadListVehiculoOffline();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                        loadListVehiculoOffline();
                        pDialog.dismiss();
                        Log.e(TAG, " error onFailure " + t.getMessage());
                    }
                });
    }

    private void destroyDBVehiculo() {
        Log.e(TAG, " eliminar  drop table vehiculo ");
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        mySqliteDB.deleteTableVehiculo();
    }

    private void loadListVehiculoOffline() {
        mListOff.clear();

        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListVehiculo(db);


        while (cursor.moveToNext()) {
            Vehiculo vehiculo = new Vehiculo();

            String nombreV = cursor.getString(cursor.getColumnIndex(CAMPO_NOMBRE_VEHICULO));
            String marcaV = cursor.getString(cursor.getColumnIndex(CAMPO_MARCA_VEHICULO));
            String matriV = cursor.getString(cursor.getColumnIndex(CAMPO_MATRICULA_VEHICULO));
            String placaV = cursor.getString(cursor.getColumnIndex(CAMPO_PLACA_VEHICULO));

            vehiculo.setNombrevehiculo(nombreV);
            vehiculo.setMarcaVehiculo(marcaV);
            vehiculo.setMatriculaVehiculo(matriV);
            vehiculo.setPlacaVehiculo(placaV);

            Log.e(TAG, "vehiculo 1 --> " + nombreV);
            Log.e(TAG, "vehiculo 2 --> " + marcaV);
            Log.e(TAG, "vehiculo 3 --> " + matriV);
            Log.e(TAG, "vehiculo 3 --> " + placaV);

            vehiculo.setNombrevehiculo(cursor.getString(0));
            vehiculo.setMarcaVehiculo(cursor.getString(1));
            vehiculo.setMatriculaVehiculo(cursor.getString(2));
            vehiculo.setPlacaVehiculo(cursor.getString(3));

            mListOff.add(vehiculo);

        }

        //
        recyclerView = findViewById(R.id.recycler_view_vehiculo);
        vAdapter = new VehiculoAdapter(mListOff);
        vAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vAdapter);
        cursor.close();
        mySqliteDB.close();
        Toast.makeText(this, "hola , esta oflline", Toast.LENGTH_SHORT).show();
    }

    private void updateListVehiculoFromMysql(String nomV, String marV, String matV, String plaV) {

        try {
            //1.Conexion
            MySqliteDB conn = new MySqliteDB(this);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " + Utils.TABLA_VEHICULO + "( " +
                    CAMPO_NOMBRE_VEHICULO + "," +
                    Utils.CAMPO_MARCA_VEHICULO + "," +
                    Utils.CAMPO_MATRICULA_VEHICULO + "," +
                    Utils.CAMPO_PLACA_VEHICULO + ")" +
                    " VALUES ('" +
                    nomV + "','" +
                    marV + "','" +
                    matV + "','" +
                    plaV + "')";

            Log.e(TAG, "el vehiculo se inserto  :" + insert);

            db.execSQL(insert);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}