package com.cudpast.rivertourapp.Business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.DbHelper;

import java.util.ArrayList;

public class NewManifActivity extends AppCompatActivity {


    private TextView guiaNombreVehiculo, guiaMatricula, guiaMarca, guiaGuia, guiaFecha, guiaChofer1, guiaChofer2, guiaBrevete1, guiaBrevete2, guiaDestino;
    private TextView pasajeroNombre, pasajeroEdad, pasajeroOcupacion, pasajeroNacionalidad, pasajeroN, pasajeroDNI, pasajeroDestino;
    private Button btnGuia;
    private Button btnPasajero;

    private Spinner guiaPlacaVehiculo, chofer1, chofer2;
    ArrayList<String> listaPersonas;
    ArrayList<Chofer> choferList;
    ArrayList<Vehiculo> vehiculoList;

    DbHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_manif);
        //
        conn = new DbHelper(this);
        //
        ArrayAdapter<CharSequence> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaPersonas);
        guiaPlacaVehiculo.setAdapter(adapter);
        guiaPlacaVehiculo
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position != 0) {
                            guiaNombreVehiculo.setText(vehiculoList.get(position - 1).getNombrevehiculo());
                            guiaMatricula.setText(vehiculoList.get(position - 1).getMatriculaVehiculo());
                            guiaMarca.setText(vehiculoList.get(position - 1).getMarcaVehiculo());

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


        //Bloque 1
        guiaPlacaVehiculo = findViewById(R.id.guiaPlacaVehiculo);
        guiaNombreVehiculo = findViewById(R.id.guiaNombreVehiculo);
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

        btnGuia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewManifActivity.this, "Guia button", Toast.LENGTH_SHORT).show();
            }
        });

        btnPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewManifActivity.this, "Pasajero button ", Toast.LENGTH_SHORT).show();
            }
        });
/*


        ArrayAdapter<CharSequence> adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaPersonas);
*/
    }

/*
    private void consultarListaChofer(){

        //obtenerLista();

    }

    private void obtenerLista(){
        listaPersonas = new ArrayList<String>();
        listaPersonas.add("Seleccione");
        for (int i = 0; i < personasList.size(); i++) {
            listaPersonas.add(personasList.get(i).getId() + "-" + personasList.get(i).getBrevete());
        }
    }
*/

}
