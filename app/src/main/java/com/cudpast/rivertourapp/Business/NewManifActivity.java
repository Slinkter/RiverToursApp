package com.cudpast.rivertourapp.Business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cudpast.rivertourapp.R;

public class NewManifActivity extends AppCompatActivity {

    private TextView guiaVehiculo, guiaN, guiaMatricula, guiaMarca, guiaGuia, guiaFecha, guiaChofer1, guiaChofer2, guiaBrevete1, guiaBrevete2, guiaDestino;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroN, pasajeroDNI, pasajeroDestino;
    private Button btnGuia;
    private Button btnPasajero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);

        //Bloque 1
        guiaVehiculo = findViewById(R.id.guiaVehiculo);
        guiaN = findViewById(R.id.guiaN);
        guiaMatricula = findViewById(R.id.guiaMatricula);
        guiaMarca = findViewById(R.id.guiaMarca);
        guiaGuia = findViewById(R.id.guiaGuia);
        guiaFecha = findViewById(R.id.guiaFecha);
        guiaChofer1 = findViewById(R.id.guiaChofer1);
        guiaChofer2 = findViewById(R.id.guiaChofer2);
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


    }
}
