package com.cudpast.rivertourapp.Business;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.R;

import java.util.ArrayList;

public class NewManifActivity extends AppCompatActivity {

    private TextView guiaVehiculo, guiaN, guiaMatricula, guiaMarca, guiaGuia, guiaFecha, guiaChofer1, guiaChofer2, guiaBrevete1, guiaBrevete2, guiaDestino;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroN, pasajeroDNI, pasajeroDestino;
    private Button btnGuia;
    private Button btnPasajero;

    private Spinner chofer1, chofer2;
    ArrayList<String> listaPersonas;
    ArrayList<Chofer> personasList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);
        //


        //Bloque 1
        guiaVehiculo = findViewById(R.id.guiaPlacaVehiculo);
        guiaN = findViewById(R.id.guiaNombreVehiculo);
        guiaMatricula = findViewById(R.id.guiaMatricula);
        guiaMarca = findViewById(R.id.guiaMarca);
        guiaGuia = findViewById(R.id.guiaGuia);
        guiaFecha = findViewById(R.id.guiaFecha);
        chofer1 = findViewById(R.id.guiaChofer1);
        chofer2 = findViewById(R.id.guiaChofer2);
        guiaBrevete1 = findViewById(R.id.guiaBrevete1);
        guiaBrevete2 = findViewById(R.id.guiaBrevete2);
        guiaDestino = findViewById(R.id.guiaDestino);

        btnGuia = findViewById(R.id.btnGuia);
        //Bloque 2
        pasajeroNombre = findViewById(R.id.pasajeroNombre);
        pasajeroEdad = findViewById(R.id.pasajeroEdad);
        pasajeroOcupacion = findViewById(R.id.pasajeroOcupacion);
        pasajeroNacionalidad = findViewById(R.id.pasajeroNacionalidad);
        pasajeroN = findViewById(R.id.pasajeroN);
        pasajeroDNI = findViewById(R.id.pasajeroDNI);
        guiaDestino = findViewById(R.id.guiaDestino);

        btnPasajero = findViewById(R.id.btnPasajero);



        ArrayAdapter<CharSequence> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaPersonas);

    }


    private void consultarListaChofer(){

        obtenerLista();

    }

    private void obtenerLista(){
        listaPersonas = new ArrayList<String>();
        listaPersonas.add("Seleccione");
        for (int i = 0; i < personasList.size(); i++) {
            listaPersonas.add(personasList.get(i).getId() + "-" + personasList.get(i).getBrevete());
        }
    }


}
