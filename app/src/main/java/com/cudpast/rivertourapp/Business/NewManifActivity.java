package com.cudpast.rivertourapp.Business;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.PasajeroAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class NewManifActivity extends AppCompatActivity {

    public static final String TAG = NewManifActivity.class.getSimpleName();

    private TextView guiaNombreVehiculo, guiaMatricula, guiaMarca, guiaGuia, guiaFecha, guiaChofer1, guiaChofer2, guiaBrevete1, guiaBrevete2, guiaDestino;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroNBoleta, pasajeroDNI, pasajeroDestino;

    private Button btnSaveGuia;
    private Button btnAddPasajero;

    private Spinner spinnerPlacaVehiculo, spinnerChofer1, spinnerChofer2;
    private RecyclerView recyclerViewPasajero;
    private RecyclerView.LayoutManager layoutManager;
    private PasajeroAdapter pAdapter;


    ArrayList<Vehiculo> listVehiculoFromSqlite;
    ArrayList<String> listVehiculoSpinner;

    ArrayList<Chofer> listChoferFromSqlite;
    ArrayList<String> listChoferSpinner;


    MySqliteDB conn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public int year_n, month_n, day_n;


    //lista Pasajero
    ArrayList<Pasajero> mListPasajero;
    ApiInterface retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);
        //
        conn = new MySqliteDB(this);

        buildCreateRecyclerPasajero();
        loadListPasajero();

        //Bloque 1
        spinnerPlacaVehiculo = findViewById(R.id.guiaPlacaVehiculo);
        guiaNombreVehiculo = findViewById(R.id.guiaNombreVehiculo);
        guiaMatricula = findViewById(R.id.guiaMatricula);
        guiaMarca = findViewById(R.id.guiaMarca);
        //
        guiaGuia = findViewById(R.id.guiaGuia);
        guiaFecha = findViewById(R.id.guiaFecha);
        spinnerChofer1 = findViewById(R.id.guiaChofer1);
        spinnerChofer2 = findViewById(R.id.guiaChofer2);
        guiaBrevete1 = findViewById(R.id.guiaBrevete1);
        guiaBrevete2 = findViewById(R.id.guiaBrevete2);
        guiaDestino = findViewById(R.id.guiaDestino);

        //Bloque 2
        pasajeroNombre = findViewById(R.id.pasajeroNombre);
        pasajeroEdad = findViewById(R.id.pasajeroEdad);
        pasajeroOcupacion = findViewById(R.id.pasajeroOcupacion);
        pasajeroNacionalidad = findViewById(R.id.pasajeroNacionalidad);
        pasajeroNBoleta = findViewById(R.id.pasajeroN);
        pasajeroDNI = findViewById(R.id.pasajeroDNI);
        pasajeroDestino = findViewById(R.id.pasajeroDestino);
        guiaDestino = findViewById(R.id.guiaDestino);

        //******************************************************
        btnSaveGuia = findViewById(R.id.btnSaveGuia);
        btnSaveGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewManifActivity.this, "Guia button", Toast.LENGTH_SHORT).show();
            }
        });
        //******************************************************
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

                pasajeroNombre.setText(" ");
                pasajeroEdad.setText(" ");
                pasajeroOcupacion.setText(" ");
                pasajeroNacionalidad.setText(" ");
                pasajeroNBoleta.setText(" ");
                pasajeroDNI.setText(" ");
                pasajeroDestino.setText(" ");

            }
        });


        //******************************************************
        guiaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewManifActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechauser = year + "/" + month + "/" + dayOfMonth;
                year_n = year;
                month_n = month;
                day_n = dayOfMonth;
                guiaFecha.setText(fechauser);
            }
        };

        //******************************************************
        getListVehiculoFromSqlite();
        getListChoferFromSqlite();
        //Spinner Vehiculo--------------------------------------
        ArrayAdapter<CharSequence> adapterVehiculo;
        adapterVehiculo = new ArrayAdapter(this, R.layout.spinner_vehiculo_item, listVehiculoSpinner);
        spinnerPlacaVehiculo.setAdapter(adapterVehiculo);
        spinnerPlacaVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    guiaNombreVehiculo.setText(listVehiculoFromSqlite.get(position - 1).getNombrevehiculo());
                    guiaMatricula.setText(listVehiculoFromSqlite.get(position - 1).getMatriculaVehiculo());
                    guiaMarca.setText(listVehiculoFromSqlite.get(position - 1).getMarcaVehiculo());
                } else {
                    guiaNombreVehiculo.setText(" ");
                    guiaMatricula.setText(" ");
                    guiaMarca.setText(" ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Chofer----------------------------------------
        ArrayAdapter<CharSequence> adapterChofer;
        adapterChofer = new ArrayAdapter(this, R.layout.spinner_vehiculo_item, listChoferSpinner);
        spinnerChofer1.setAdapter(adapterChofer);
        spinnerChofer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    guiaBrevete1.setText(listChoferFromSqlite.get(position - 1).getBrevete());
                } else {
                    guiaBrevete1.setText(" ");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void getListVehiculoFromSqlite() {
        SQLiteDatabase db;
        Vehiculo vehiculo;
        Cursor cursor;

        db = conn.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM  " + Utils.TABLA_VEHICULO, null);
        listVehiculoFromSqlite = new ArrayList<Vehiculo>();
        while (cursor.moveToNext()) {
            vehiculo = new Vehiculo();
            vehiculo.setNombrevehiculo(cursor.getString(1));
            vehiculo.setMarcaVehiculo(cursor.getString(2));
            vehiculo.setMatriculaVehiculo(cursor.getString(3));
            vehiculo.setPlacaVehiculo(cursor.getString(4));
            listVehiculoFromSqlite.add(vehiculo);

        }
        printListVehiculoSpinner();
    }

    private void getListChoferFromSqlite() {

        SQLiteDatabase db;
        Chofer chofer;
        Cursor cursor;

        db = conn.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM  " + Utils.TABLA_CHOFER, null);
        listChoferFromSqlite = new ArrayList<Chofer>();
        while (cursor.moveToNext()) {
            chofer = new Chofer();
            chofer.setNameChofer(cursor.getString(1));
            chofer.setLastChofer(cursor.getString(2));
            chofer.setDniChofer(cursor.getString(3));
            chofer.setBrevete(cursor.getString(4));
            chofer.setNumphone(cursor.getString(5));
            listChoferFromSqlite.add(chofer);
        }
        printListChoferSpinner();

    }

    private void printListVehiculoSpinner() {
        listVehiculoSpinner = new ArrayList<String>();
        listVehiculoSpinner.add("Seleccione Vehiculo");
        for (int i = 0; i < listVehiculoFromSqlite.size(); i++) {
            listVehiculoSpinner.add(listVehiculoFromSqlite.get(i).getPlacaVehiculo());
        }
    }

    private void printListChoferSpinner() {
        listChoferSpinner = new ArrayList<String>();
        listChoferSpinner.add("Seleccione Chofer");
        for (int i = 0; i < listChoferFromSqlite.size(); i++) {
            listChoferSpinner.add(listChoferFromSqlite.get(i).getNameChofer() + " - " + listChoferFromSqlite.get(i).getLastChofer());
        }

    }

}
