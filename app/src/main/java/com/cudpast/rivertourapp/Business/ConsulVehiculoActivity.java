package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.vehiculoAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Adapter.ChoferAdapter;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.ConexionSQLiteHelper;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cudpast.rivertourapp.SQLite.Utils.db_version;

public class ConsulVehiculoActivity extends AppCompatActivity {

    public static final String TAG = "CONSULTA_VEHICULO";
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private vehiculoAdapter vAdapter;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consul_vehiculo);

        pDialog = new ProgressDialog(ConsulVehiculoActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        obtenerListaChofer();
    }

    // Obtener la lista de choferes desde remote DB
    private void obtenerListaChofer() {

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<List<Vehiculo>> getListaChofer = apiInterface.getListVehiculo();
        getListaChofer
                .enqueue(new Callback<List<Vehiculo>>() {
                    @Override
                    public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // se obtiene la lista de vehiculo remotamente
                            List<Vehiculo> mList = response.body();
                            // update la lista de vehiculo en sqlite
                            for (int i = 0; i < mList.size(); i++) {
                                String nombrevehiculo = mList.get(i).getNombrevehiculo();
                                String marcaVehiculo = mList.get(i).getMarcaVehiculo();
                                String matriculaVehiculo = mList.get(i).getMatriculoVehiculo();
                                String placaVehiculo = mList.get(i).getPlacaVehiculo();

                                updateListVehiculoFromMysql(nombrevehiculo, marcaVehiculo, marcaVehiculo, placaVehiculo);

                                String cadena = "==== Chofer Nº " + i + " ====== " + "\n"
                                        + " nombrevehiculo : " + nombrevehiculo + "\n"
                                        + " marcaVehiculo : " + marcaVehiculo + "\n"
                                        + " matriculaVehiculo : " + matriculaVehiculo + "\n"
                                        + " placaVehiculo : " + placaVehiculo + "\n"
                                        + "  " + "" + "\n";

                                Log.e(TAG, cadena);
                            }
                            // mostrar lista de vehiculos remotament
                            recyclerView = findViewById(R.id.recycler_view_vehiculo);
                            vAdapter = new vehiculoAdapter(mList);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(eLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(vAdapter);
                            pDialog.dismiss();
                        } else {
                            loadListVehiculoSQLite();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                        loadListVehiculoSQLite();
                        pDialog.dismiss();
                        Log.e(TAG, " error onFailure " + t.getMessage());
                    }
                });
    }

    private void loadListVehiculoSQLite() {
        Toast.makeText(this, "hola , esta oflline", Toast.LENGTH_SHORT).show();
    }


    private void updateListVehiculoFromMysql(String nomV, String marV, String matV, String plaV) {

        try {
            //1.Conexion
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, db_version);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " + Utils.TABLA_VEHICULO + "(" +
                    Utils.CAMPO_NOMBRE_VEHICULO + "," +
                    Utils.CAMPO_MARCA_VEHICULO + "," +
                    Utils.CAMPO_MATRICULA_VEHICULO + "," +
                    Utils.CAMPO_PLACA_VEHICULO + ")" +
                    " VALUES ('" + nomV + "','" + marV + "','" + matV + "','" + plaV + "')";

            Log.e(TAG, "insert Usuario :" + insert);
            db.execSQL(insert);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}