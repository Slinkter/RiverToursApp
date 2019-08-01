package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cudpast.rivertourapp.Adapter.vehiculoAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Adapter.ChoferAdapter;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsulVehiculoActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private vehiculoAdapter vAdapter;

    public static final String TAG = "CONSULTA_VEHICULO";

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
        getListaChofer.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<Vehiculo> mList = response.body();
                    recyclerView = findViewById(R.id.recycler_view_vehiculo);
                    vAdapter = new vehiculoAdapter(mList);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(vAdapter);
                    for (int i = 0; i < mList.size(); i++) {
                        String nombrevehiculo = mList.get(i).getNombrevehiculo();
                        String marcaVehiculo = mList.get(i).getMarcaVehiculo();
                        String matriculaVehiculo = mList.get(i).getMatriculoVehiculo();
                        String placaVehiculo = mList.get(i).getPlacaVehiculo();
                        String cadena = " " + "\n"
                                + " nombrevehiculo : " + nombrevehiculo + "\n"
                                + " marcaVehiculo : " + marcaVehiculo + "\n"
                                + " matriculaVehiculo : " + matriculaVehiculo + "\n"
                                + " placaVehiculo : " + placaVehiculo + "\n"
                                + "  " + "" + "\n";
                        Log.e(TAG, "==== Chofer Nº" + i + " ======");
                        Log.e(TAG, cadena);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                pDialog.dismiss();
                Log.e(TAG, " error onFailure " + t.getMessage());
            }
        });
    }
}
