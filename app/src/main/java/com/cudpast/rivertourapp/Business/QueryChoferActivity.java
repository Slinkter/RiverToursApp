package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.MainActivity;
import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Adapter.ChoferAdapter;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryChoferActivity extends AppCompatActivity {

    public static final String TAG = QueryChoferActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ChoferAdapter cAdapter;
    private ApiInterface apiInterface;
    private List<Chofer> mListOff;
    Call<List<Chofer>> getListChofer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consul_chofer);
        init();
    }


    private void init() {
        //.
        pDialog = new ProgressDialog(QueryChoferActivity.this);
        pDialog.setMessage("Actualizando datos...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        //.
        mListOff = new ArrayList<Chofer>();
        obtenerListaChoferOnline();
    }


    // Obtener la lista de choferes Online
    private void obtenerListaChoferOnline() {

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        getListChofer = apiInterface.getListChofer();
        getListChofer.enqueue(new Callback<List<Chofer>>() {
            @Override
            public void onResponse(Call<List<Chofer>> call, Response<List<Chofer>> response) {
                // se obtener un array (lista) desde el html
                if (response.isSuccessful() && response.body() != null) {
                    List<Chofer> mListChofer = response.body();
                    //
                    destroyDBChofer();
                    //
                    for (int i = 0; i < mListChofer.size(); i++) {
                        String nombreChofer = mListChofer.get(i).getNameChofer();
                        String lastChofer = mListChofer.get(i).getLastChofer();
                        String dniChofer = mListChofer.get(i).getDniChofer();
                        String breveteChofer = mListChofer.get(i).getBrevete();
                        String telefonochofer = mListChofer.get(i).getNumphone();
                        //
                        updateListChofer(nombreChofer, lastChofer, dniChofer, breveteChofer, telefonochofer);
                        String cadena = "==== Chofer Nº " + i + " ====== " + "\n"
                                + " nombrevehiculo : " + nombreChofer + "\n"
                                + " marcaVehiculo : " + lastChofer + "\n"
                                + " matriculaVehiculo : " + dniChofer + "\n"
                                + " matriculaVehiculo : " + breveteChofer + "\n"
                                + " placaVehiculo : " + telefonochofer + "\n"
                                + "  " + "" + "\n";
                        Log.e(TAG, cadena);
                    }

                    recyclerView = findViewById(R.id.recycler_view_chofer);
                    cAdapter = new ChoferAdapter(mListChofer);
                    cAdapter.notifyDataSetChanged();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cAdapter);
                    pDialog.dismiss();
                    //
                } else {
                    loadListChoferOffline();
                    pDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<Chofer>> call, Throwable t) {
                loadListChoferOffline();
                pDialog.dismiss();
                Log.e(TAG, " error onFailure " + t.getMessage());
            }
        });
    }

    // Obtener la lista de choferes Offline
    private void loadListChoferOffline() {
        mListOff.clear();

        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListChofer(db);

        while (cursor.moveToNext()) {
            Chofer chofer = new Chofer();
            //
            String nombreChofer = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NOMBRE_CHOFER));
            String apellidoChofer = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_APELLIDO_CHOFER));
            String dniChofer = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DNI_CHOFER));
            String breveteChofer = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_BREVETE_CHOFER));
            String telefonoChofer = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_TELEFONO_CHOFER));
            //
            chofer.setNameChofer(nombreChofer);
            chofer.setLastChofer(apellidoChofer);
            chofer.setDniChofer(dniChofer);
            chofer.setBrevete(breveteChofer);
            chofer.setNumphone(telefonoChofer);
            //
            mListOff.add(chofer);

        }

        recyclerView = findViewById(R.id.recycler_view_chofer);
        cAdapter = new ChoferAdapter(mListOff);
        cAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);
        cursor.close();
        mySqliteDB.close();
        Toast.makeText(this, "Esta offline", Toast.LENGTH_SHORT).show();


    }

    //
    private void updateListChofer(String nombreChofer, String apellidoChofer, String dniChofer, String breveteChofer, String telefonoChofer) {

        try {
            MySqliteDB conn = new MySqliteDB(this);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " + Utils.TABLA_CHOFER + "( " +
                    Utils.CAMPO_NOMBRE_CHOFER + "," +
                    Utils.CAMPO_APELLIDO_CHOFER + "," +
                    Utils.CAMPO_DNI_CHOFER + "," +
                    Utils.CAMPO_BREVETE_CHOFER + "," +
                    Utils.CAMPO_TELEFONO_CHOFER + ")" +
                    " VALUES ('" +
                    nombreChofer + "','" +
                    apellidoChofer + "','" +
                    dniChofer + "','" +
                    breveteChofer + "','" +
                    telefonoChofer + "')";

            db.execSQL(insert);
            db.close();
            Log.e(TAG, " si inserto el chofer : " + insert);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, " NO inserto el chofer : ");
        }

    }

    //
    private void destroyDBChofer() {
        Log.e(TAG, " eliminar  drop table chofer ");
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        mySqliteDB.deleteTableChofer();
    }

    //
    public void backToMain(View view) {
        Intent i = new Intent(QueryChoferActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
