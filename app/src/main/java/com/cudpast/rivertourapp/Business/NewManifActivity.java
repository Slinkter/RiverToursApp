package com.cudpast.rivertourapp.Business;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class NewManifActivity extends AppCompatActivity {

    public static final String TAG = NewManifActivity.class.getSimpleName();

    private TextView tv_nombreVehiculo, tv_matriculaVehiculo, tv_marcaVehiculo, tv_breveteChofer;
    private EditText et_guiaGuia, et_guiaFecha, et_guiaDestino;
    private Button btnSaveGuia;
    private Spinner spinner_PlacaVehiculo, spinnerChofer1;

    ArrayList<Vehiculo> listVehiculoFromSqlite;
    ArrayList<String> listVehiculoSpinner;

    ArrayList<Chofer> listChoferFromSqlite;
    ArrayList<String> listChoferSpinner;

    MySqliteDB conn;
    public DatePickerDialog.OnDateSetListener mDateSetListener;

    private Animation animation;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);
        //
        conn = new MySqliteDB(this);
        //0
        et_guiaGuia = findViewById(R.id.et_guiaGuia);
        et_guiaDestino = findViewById(R.id.et_guiaDestino);
        et_guiaFecha = findViewById(R.id.et_guiaFecha);
        //1
        spinner_PlacaVehiculo = findViewById(R.id.guiaPlacaVehiculo);
        tv_nombreVehiculo = findViewById(R.id.tv_nombreVehiculo);
        tv_matriculaVehiculo = findViewById(R.id.tv_matriculaVehiculo);
        tv_marcaVehiculo = findViewById(R.id.tv_marcaVehiculo);
        //2
        spinnerChofer1 = findViewById(R.id.guiaChofer1);
        tv_breveteChofer = findViewById(R.id.tv_breveteChofer);

        //submitForm
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        //******************************************************
        et_guiaFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                Log.e(TAG, "YEAR = " + year);
                Log.e(TAG, "MONTH = " + month);
                Log.e(TAG, "DAY = " + day);


                dialog = new DatePickerDialog(
                        NewManifActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //     String fecha_manifiesto = year + "/" + (month +1) + "/" + dayOfMonth;
                String fecha_manifiesto = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_guiaFecha.setText(fecha_manifiesto);
            }
        };

        //******************************************************
        getListVehiculoFromSqlite();
        getListChoferFromSqlite();
        //Spinner Vehiculo
        ArrayAdapter<CharSequence> adapterVehiculo;
        adapterVehiculo = new ArrayAdapter(this, R.layout.spinner_vehiculo_item, listVehiculoSpinner);
        spinner_PlacaVehiculo.setAdapter(adapterVehiculo);
        spinner_PlacaVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    tv_nombreVehiculo.setText(listVehiculoFromSqlite.get(position - 1).getNombrevehiculo());
                    tv_matriculaVehiculo.setText(listVehiculoFromSqlite.get(position - 1).getMatriculaVehiculo());
                    tv_marcaVehiculo.setText(listVehiculoFromSqlite.get(position - 1).getMarcaVehiculo());
                } else {
                    tv_nombreVehiculo.setText(" ");
                    tv_matriculaVehiculo.setText(" ");
                    tv_marcaVehiculo.setText(" ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Chofer
        ArrayAdapter<CharSequence> adapterChofer;
        adapterChofer = new ArrayAdapter(this, R.layout.spinner_vehiculo_item, listChoferSpinner);
        spinnerChofer1.setAdapter(adapterChofer);
        spinnerChofer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    tv_breveteChofer.setText(listChoferFromSqlite.get(position - 1).getBrevete());
                } else {
                    tv_breveteChofer.setText(" ");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Insertar Manifiesto
        btnSaveGuia = findViewById(R.id.btnSaveGuia);
        btnSaveGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submitForm()) {
                    Intent intent = new Intent(NewManifActivity.this, AddPasajeroActivity.class);
                    startActivity(intent);
                }


            }
        });


    }

    //***********************************
    // Validacion

    private boolean submitForm() {

        if (!checkGuia()) {
            et_guiaGuia.setAnimation(animation);
            et_guiaGuia.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }

        /*
        if (!checkFecha()) {

        }


        if (!checkDestino()) {

        }

        if (!checkVehiculo()) {

        }

        if (!checkChofer()) {

        }
*/

        return true;
    }


    private boolean checkGuia() {
        if (et_guiaGuia.getText().toString().trim().isEmpty()) {
            et_guiaGuia.setError("Ingresar Guia");
            return false;
        }
        return true;
    }

    private boolean checkFecha() {
        if (et_guiaFecha.getText().toString().trim().isEmpty()) {
            et_guiaFecha.setError("Ingresar Fecha");
            return false;
        }
        return true;
    }


    private boolean checkDestino() {
        if (et_guiaDestino.getText().toString().trim().isEmpty()) {
            et_guiaDestino.setError("Ingresar Destino");
            return false;
        }
        return true;
    }

    private boolean checkVehiculo() {
        if (tv_marcaVehiculo.getText().toString().trim().isEmpty()) {
            tv_marcaVehiculo.setError("Ingresar Vehiculo");
            return false;
        }
        return true;

    }

    private boolean checkChofer() {
        if (tv_breveteChofer.getText().toString().trim().isEmpty()) {
            tv_breveteChofer.setError("Ingresar brevete");
            return false;
        }
        return true;
    }


    //*******************************************


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
            listChoferSpinner.add(" " + listChoferFromSqlite.get(i).getNameChofer() + " " + listChoferFromSqlite.get(i).getLastChofer());
        }

    }


}
