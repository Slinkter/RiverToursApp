package com.cudpast.rivertourapp.Business;

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

import com.cudpast.rivertourapp.Adapter.PasajeroAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

public class AddPasajeroActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPasajero;
    private RecyclerView.LayoutManager layoutManager;
    private PasajeroAdapter pAdapter;
    private Button btnAddPasajero;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroNBoleta, pasajeroDNI, pasajeroDestino;

    //lista Pasajero
    ArrayList<Pasajero> mListPasajero;
    ApiInterface retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pasajero);

        buildCreateRecyclerPasajero();
        loadListPasajero();

        //Bloque 2
        pasajeroNombre = findViewById(R.id.pasajeroNombre);
        pasajeroEdad = findViewById(R.id.pasajeroEdad);
        pasajeroOcupacion = findViewById(R.id.pasajeroOcupacion);
        pasajeroNacionalidad = findViewById(R.id.pasajeroNacionalidad);
        pasajeroNBoleta = findViewById(R.id.pasajeroN);
        pasajeroDNI = findViewById(R.id.pasajeroDNI);
        pasajeroDestino = findViewById(R.id.pasajeroDestino);


        btnAddPasajero = findViewById(R.id.btnAddPasajero);
        btnAddPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = pasajeroNombre.getText().toString();
                String edad = pasajeroEdad.getText().toString();
                String ocupacion = pasajeroOcupacion.getText().toString();
                String nacionalidad = pasajeroNacionalidad.getText().toString();
                String numBoleta = pasajeroNBoleta.getText().toString();
                String dni = pasajeroDNI.getText().toString();
                String destino = pasajeroDestino.getText().toString();

                Pasajero pasajero = new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino);
                createPasajero(pasajero);


                pasajeroNombre.setText("");
                pasajeroEdad.setText("");
                pasajeroOcupacion.setText("");
                pasajeroNacionalidad.setText("");
                pasajeroNBoleta.setText("");
                pasajeroDNI.setText("");
                pasajeroDestino.setText("");

                if (pasajeroNombre.getText().toString().isEmpty()) {
                    pasajeroNombre.setHint("nombre");
                }


                pasajeroEdad.setHint("edad");
                pasajeroOcupacion.setHint("ocupacion");
                pasajeroNacionalidad.setHint("nacionalidad");
                pasajeroNBoleta.setHint("boleta");
                pasajeroDNI.setHint("dni");
                pasajeroDestino.setHint("destino ");

            }
        });
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

        pAdapter.setOnItemClickListener(new PasajeroAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

    }


    private void removeItem(int position) {

        //mListPasajero.remove(position);
        deletePasajero(mListPasajero.get(position).getDni());
        pAdapter.notifyItemRemoved(position);
    }

    private void savePasajero(Pasajero pasajero) {
        /* todo :
         Caso 1 : Online
         se va a crear una lista local de pasajero , cuando se
         finalice la lista de pasajeros  el boton debe cambiar a guardar
         obtener la info de la guia y guardar la guia con la lista de pasajero

         Caso 2 : Offline
         se va a crear una lista local de pasajero ,cuando finalice la lista de pasajero,
         se guardar en local con
        */

        // primero vamos a ver la lita de pasajero

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
                        Utils.CAMPO_DESTINO_PASAJERO + ")" +
                        " VALUES ('" +
                        pasajero.getNombre() + "','" +
                        pasajero.getEdad() + "','" +
                        pasajero.getOcupacion() + "','" +
                        pasajero.getNacionalidad() + "','" +
                        pasajero.getNumBoleta() + "','" +
                        pasajero.getDni() + "','" +
                        pasajero.getDestino() + "');";
        Log.e(" ", "------> " + insert);
        //4.Insertar
        db.execSQL(insert);
        loadListPasajero();
        //5.Cerrar conexion
        db.close();


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


    private void saveToLocalStorage(Pasajero pasajero) {
        Log.e("saveToLocalStorag", "1");
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getWritableDatabase();

        mySqliteDB.mySaveToLocalDBPasajero(pasajero, db);
        loadListPasajero();
        mySqliteDB.close();
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
}
