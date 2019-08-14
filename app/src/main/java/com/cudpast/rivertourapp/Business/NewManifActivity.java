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


    private Button btnSaveGuia;


    private Spinner spinnerPlacaVehiculo, spinnerChofer1, spinnerChofer2;



    ArrayList<Vehiculo> listVehiculoFromSqlite;
    ArrayList<String> listVehiculoSpinner;

    ArrayList<Chofer> listChoferFromSqlite;
    ArrayList<String> listChoferSpinner;


    MySqliteDB conn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public int year_n, month_n, day_n;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);
        //
        conn = new MySqliteDB(this);



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



        //******************************************************
        btnSaveGuia = findViewById(R.id.btnSaveGuia);
        btnSaveGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewManifActivity.this, "Guia button", Toast.LENGTH_SHORT).show();
                //todo se debe insetar la guia y una lista de pasajero

            }
        });
        //******************************************************



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
            listVehiculoSpinner.add(" " + listVehiculoFromSqlite.get(i).getPlacaVehiculo());
        }
    }

    private void printListChoferSpinner() {
        listChoferSpinner = new ArrayList<String>();
        listChoferSpinner.add("Seleccione Chofer");
        for (int i = 0; i < listChoferFromSqlite.size(); i++) {
            listChoferSpinner.add(" " +  listChoferFromSqlite.get(i).getNameChofer() + "  " + listChoferFromSqlite.get(i).getLastChofer());
        }

    }

}
