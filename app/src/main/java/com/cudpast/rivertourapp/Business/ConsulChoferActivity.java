package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Adapter.ChoferAdapter;
import com.cudpast.rivertourapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsulChoferActivity extends AppCompatActivity {

    public static final String TAG = "CONSULTACHOFER";


    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ChoferAdapter cAdapter;
    private ApiInterface apiInterface;

    List<Chofer> mListOff ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consul_chofer);
        //.
        pDialog = new ProgressDialog(ConsulChoferActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        //.
        mListOff = new ArrayList<Chofer>();
        obtenerListaVehiculo();

    }

    // Obtener la lista de choferes desde remote DB
    private void obtenerListaVehiculo() {

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<List<Chofer>> getListaChofer = apiInterface.getListChofer();
        getListaChofer.enqueue(new Callback<List<Chofer>>() {
            @Override
            public void onResponse(Call<List<Chofer>> call, Response<List<Chofer>> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<Chofer> students = response.body();
                    recyclerView = findViewById(R.id.recycler_view_chofer);
                    cAdapter = new ChoferAdapter(students);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cAdapter);
                    for (int i = 0; i < students.size(); i++) {
                        String name = students.get(i).getNameChofer();
                        String last = students.get(i).getLastChofer();
                        String dni = students.get(i).getDniChofer();
                        String brevete = students.get(i).getBrevete();
                        String num = students.get(i).getNumphone();


                        String cadena = " " + "\n"
                                + " name : " + name + "\n"
                                + " last : " + last + "\n"
                                + " dni : " + dni + "\n"
                                + " brevete : " + brevete + "\n"
                                + " num : " + num + "\n";
                        Log.e(TAG, "==== Chofer Nº" + i + " ======");
                        Log.e(TAG, cadena);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<Chofer>> call, Throwable t) {
                pDialog.dismiss();
                Log.e(TAG, " error onFailure " + t.getMessage());
            }
        });
    }




}
