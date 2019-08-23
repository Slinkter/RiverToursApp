package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
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
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

public class AddPasajeroActivity extends AppCompatActivity {
    //
    public static final String TAG = AddPasajeroActivity.class.getSimpleName();
    // El manifiesto y la lista de pasajeros debe estar insetada en local(sqlite)
    // y cuando se valla mainActivity debe actualizar
    // la base de datos remota;
    public RecyclerView rv_Pasajero;
    public RecyclerView.LayoutManager rv_layoutManager;
    public PasajeroAdapter pAdapter;
    public Button btn_AddPasajero, btn_SaveGuia;
    public TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroNBoleta, pasajeroDNI, pasajeroDestino;
    //lista Pasajero
    ArrayList<Pasajero> mListPasajero;
    String idguiaManifiesto;
    int SyncMani;
    TextView tv_guiaidmanifiestopasajero;
    //
    ProgressDialog progressDialog;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_add_pasajero);
        getSupportActionBar().hide();
        //
        buildCreateRecyclerPasajero();
        //loadListPasajero();
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
        btn_AddPasajero = findViewById(R.id.btnAddPasajero);
        btn_SaveGuia = findViewById(R.id.btnFinalizarGuia);
        //
        btn_AddPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
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
                clearTextPasajero();
                clearHintPasajero();
            }
        });
        btn_SaveGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPasajeroActivity.this, "BUTTON SAVE GUIA ", Toast.LENGTH_SHORT).show();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
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
        Toast.makeText(this, "metodo vacion", Toast.LENGTH_SHORT).show();
        /*
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getWritableDatabase();
        mySqliteDB.mySaveToLocalDBManifiesto(manifiesto, sync, db);
        mySqliteDB.close();
        */
    }

    private void buildCreateRecyclerPasajero() {
        // rv_Pasajero
        mListPasajero = new ArrayList<>();
        pAdapter = new PasajeroAdapter(mListPasajero);
        rv_layoutManager = new LinearLayoutManager(this);
        rv_Pasajero = findViewById(R.id.recyclerViewPasajero);
        rv_Pasajero.setHasFixedSize(true);
        rv_Pasajero.setLayoutManager(rv_layoutManager);
        rv_Pasajero.setAdapter(pAdapter);
        //
        pAdapter.setOnItemClickListener(new PasajeroAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                //
            }

            @Override
            public void onDeleteClick(int position) {
                removePasajero(position);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

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


    private void loadListPasajero() {
        mListPasajero.clear();
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase database = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListPasajero(database);

        while (cursor.moveToNext()) {

            if (Utils.CAMPO_GUIAID_PASAJERO.equalsIgnoreCase(idguiaManifiesto)) {
                String nombre = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NOMBRE_PASAJERO));
                String edad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_EDAD_PASAJERO));
                String ocupacion = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_OCUPACION_PASAJERO));
                String nacionalidad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NACIONALIDAD_PASAJERO));
                String numBoleta = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NUMBOLETA_PASAJERO));
                String dni = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DNI_PASAJERO));
                String destino = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_PASAJERO));
                mListPasajero.add(new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino));
            }


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
