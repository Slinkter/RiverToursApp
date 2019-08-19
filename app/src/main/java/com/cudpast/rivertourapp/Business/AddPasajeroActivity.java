package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.PasajeroAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.MainActivity;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPasajeroActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPasajero;
    private RecyclerView.LayoutManager layoutManager;
    private PasajeroAdapter pAdapter;
    private Button btnAddPasajero, btnFinalizarGuia;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroNBoleta, pasajeroDNI, pasajeroDestino;

    //lista Pasajero
    ArrayList<Pasajero> mListPasajero;
    ApiInterface retrofitAPI;

    String idguiaManifiesto, FechaMani, DestinoMani, VehiculoMani, ChoferMani;
    int SyncMani;
    TextView tv_guiaidmanifiestopasajero;

    //
    ApiInterface apiInterface;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pasajero);
        getSupportActionBar().hide();

        buildCreateRecyclerPasajero();
        loadListPasajero();
        // Info de Manifiesto de la guia id
        if (getIntent() != null) {
            //se tiene el manifiesto
            idguiaManifiesto = getIntent().getStringExtra("idguiaManifiesto");
            SyncMani = 0;
            //
            tv_guiaidmanifiestopasajero = findViewById(R.id.tv_guiaidmanifiestopasajero);
            tv_guiaidmanifiestopasajero.setText(idguiaManifiesto);
            Log.e("TAG ", "valor de intent : " + idguiaManifiesto);
        }
        //Bloque 2
        pasajeroNombre = findViewById(R.id.pasajeroNombre);
        pasajeroEdad = findViewById(R.id.pasajeroEdad);
        pasajeroOcupacion = findViewById(R.id.pasajeroOcupacion);
        pasajeroNacionalidad = findViewById(R.id.pasajeroNacionalidad);
        pasajeroNBoleta = findViewById(R.id.pasajeroN);
        pasajeroDNI = findViewById(R.id.pasajeroDNI);
        pasajeroDestino = findViewById(R.id.pasajeroDestino);
        //
        btnAddPasajero = findViewById(R.id.btnAddPasajero);
        btnAddPasajero
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nombre = pasajeroNombre.getText().toString();
                        String edad = pasajeroEdad.getText().toString();
                        String ocupacion = pasajeroOcupacion.getText().toString();
                        String nacionalidad = pasajeroNacionalidad.getText().toString();
                        String numBoleta = pasajeroNBoleta.getText().toString();
                        String dni = pasajeroDNI.getText().toString();
                        String destino = pasajeroDestino.getText().toString();
                        //
                        Pasajero pasajero = new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino);
                        //
                        createPasajero(pasajero);
                        //
                        clearTextPasajero();
                        clearHintPasajero();

                    }
                });

        btnFinalizarGuia = findViewById(R.id.btnFinalizarGuia);

        btnFinalizarGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         /*
         todo :
         Caso 1 : Online
         se va a crear una lista local de pasajero , cuando se
         finalice la lista de pasajeros  el boton debe cambiar a guardar
         obtener la info de la guia y guardar la guia con la lista de pasajero

         Caso 2 : Offline
         se va a crear una lista local de pasajero ,cuando finalice la lista de pasajero,
         se guardar en local con
        */


            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }


    private void revisar() {
        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<Manifiesto> userInsert = apiInterface.insertManifiesto(idguiaManifiesto, FechaMani, DestinoMani, VehiculoMani, ChoferMani);
        userInsert.enqueue(new Callback<Manifiesto>() {
            @Override
            public void onResponse(Call<Manifiesto> call, Response<Manifiesto> response) {
                Log.e(" response", " " + response.message());
                Log.e(" response", " " + response.toString());
                Log.e(" response", " " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    //
                    Manifiesto manifiesto = new Manifiesto();
                    manifiesto.setIdGuiaMani(idguiaManifiesto);
                    manifiesto.setFechaMani(FechaMani);
                    manifiesto.setDestinoMani(DestinoMani);
                    manifiesto.setVehiculoMani(VehiculoMani);
                    manifiesto.setChoferMani(ChoferMani);
                    //
                    if (success) {
                        saveToLocalStorage(manifiesto, Utils.SYNC_STATUS_OK_MANIFIESTO);
                        //
                        Log.e("remoteBD", " onResponadse : Success");
                        Toast.makeText(AddPasajeroActivity.this, "", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                        Intent intent = new Intent(AddPasajeroActivity.this, MainActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {
                        saveToLocalStorage(manifiesto, Utils.SYNC_STATUS_FAILIDE_MANIFIESTO);
                        //
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(AddPasajeroActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Manifiesto> call, Throwable t) {

                Toast.makeText(AddPasajeroActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("remoteBD", " onResponse : fail" + t.toString() + "\n " + t.getCause());
                Log.e("remoteBD", " onResponse : fail");
                Log.e("onFailure", " response =  " + t.getMessage());
            }
        });
    }


    private void clearTextPasajero() {
        pasajeroNombre.setText("");
        pasajeroEdad.setText("");
        pasajeroOcupacion.setText("");
        pasajeroNacionalidad.setText("");
        pasajeroNBoleta.setText("");
        pasajeroDNI.setText("");
        pasajeroDestino.setText("");
    }

    private void clearHintPasajero() {

        if (pasajeroNombre.getText().toString().isEmpty()) {
            pasajeroNombre.setHint("nombre");
            pasajeroEdad.setHint("edad");
            pasajeroOcupacion.setHint("ocupacion");
            pasajeroNacionalidad.setHint("nacionalidad");
            pasajeroNBoleta.setHint("boleta");
            pasajeroDNI.setHint("dni");
            pasajeroDestino.setHint("destino ");
        }
    }

    private void saveToLocalStorage(Manifiesto manifiesto, int sync) {
        // todo : Lunes
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getWritableDatabase();
        mySqliteDB.mySaveToLocalDBManifiesto(manifiesto, sync, db);
        mySqliteDB.close();
    }

    private void buildCreateRecyclerPasajero() {
        // recyclerViewPasajero
        mListPasajero = new ArrayList<>();
        recyclerViewPasajero = findViewById(R.id.recyclerViewPasajero);
        recyclerViewPasajero.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPasajero.setLayoutManager(layoutManager);
        pAdapter = new PasajeroAdapter(mListPasajero);
        recyclerViewPasajero.setAdapter(pAdapter);
        //
        pAdapter.setOnItemClickListener(new PasajeroAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Eliminar metodo
            }

            @Override
            public void onDeleteClick(int position) {
                removePasajero(position);
            }
        });

    }


    private void loadListPasajero() {
        mListPasajero.clear();
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase database = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.readFromLocalDatabasePasajero(database);

        while (cursor.moveToNext()) {

            String nombre = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NOMBRE_PASAJERO));
            String edad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_EDAD_PASAJERO));
            String ocupacion = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_OCUPACION_PASAJERO));
            String nacionalidad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NACIONALIDAD_PASAJERO));
            String numBoleta = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NUMBOLETA_PASAJERO));
            String dni = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DNI_PASAJERO));
            String destino = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_PASAJERO));

            mListPasajero.add(new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino));
        }
        pAdapter.notifyDataSetChanged();
        cursor.close();
        mySqliteDB.close();
    }

    private void createPasajero(Pasajero pasajero) {
        //1.Conexion
        MySqliteDB conn = new MySqliteDB(this);
        //2.Escribir en la database
        SQLiteDatabase db = conn.getWritableDatabase();
        //3.Cogigo para insert into usuario (id,nombre,telefono) values (123 , 'dasd','543534')
        String insert =
                "INSERT INTO " +
                        Utils.TABLA_PASAJERO + "(" +
                        Utils.CAMPO_NOMBRE_PASAJERO + "," +
                        Utils.CAMPO_EDAD_PASAJERO + "," +
                        Utils.CAMPO_OCUPACION_PASAJERO + "," +
                        Utils.CAMPO_NACIONALIDAD_PASAJERO + "," +
                        Utils.CAMPO_NUMBOLETA_PASAJERO + "," +
                        Utils.CAMPO_DNI_PASAJERO + "," +
                        Utils.CAMPO_DESTINO_PASAJERO + "," +
                        Utils.CAMPO_GUIAID_PASAJERO + ")" +
                        " VALUES ('" +
                        pasajero.getNombrePasajero() + "','" +
                        pasajero.getEdadPasajero() + "','" +
                        pasajero.getOcupacionPasajero() + "','" +
                        pasajero.getNacionalidadPasajero() + "','" +
                        pasajero.getNumBoleta() + "','" +
                        pasajero.getDniPasajero() + "','" +
                        pasajero.getDestinoPasajero() + "','" +
                        idguiaManifiesto + "');";
        Log.e("TAG", "------> " + insert);
        //4.Insertar
        db.execSQL(insert);
        loadListPasajero();
        //5.Cerrar conexion
        db.close();
    }

    private void removePasajero(int position) {
        String dni = mListPasajero.get(position).getDniPasajero();
        deletePasajero(dni);
        pAdapter.notifyItemRemoved(position);
    }

    private void deletePasajero(String dni) {
        //1.Conexion
        MySqliteDB conn = new MySqliteDB(this);
        //2.Escribir en la database
        SQLiteDatabase db = conn.getWritableDatabase();
        //3.Sentencia sql
        String sql = "DELETE FROM " + Utils.TABLA_PASAJERO + " WHERE " + Utils.CAMPO_DNI_PASAJERO + "='" + dni + "'";
        db.execSQL(sql);
        //4.Ejecutar SQL
        db.execSQL(sql);
        loadListPasajero();
        //5.Cerrar conexion
        db.close();
    }
}
