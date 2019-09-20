package com.cudpast.rivertourapp.Business.Support;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cudpast.rivertourapp.Adapter.PasajeroAdapterGuia;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

public class ListPasajeroActivity extends AppCompatActivity {

    public static final String TAG = ListPasajeroActivity.class.getSimpleName();
    //
    ArrayList<Pasajero> mListPasajero;
    String idguiaManifiesto;
    //
    public RecyclerView rv_Pasajero;
    public RecyclerView.LayoutManager rv_layoutManager;
    public PasajeroAdapterGuia pAdapter;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("   ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list_pasajero);

        if  (getIntent() != null){
            buildCreateRecyclerPasajero();
            idguiaManifiesto = getIntent().getStringExtra("idguiaManifiesto");
            loadListPasajero(idguiaManifiesto);
        }

    }

    private void buildCreateRecyclerPasajero() {

        // rv_Pasajero
        mListPasajero = new ArrayList<>();
        pAdapter = new PasajeroAdapterGuia(mListPasajero);
        rv_layoutManager = new LinearLayoutManager(this);
        rv_Pasajero = findViewById(R.id.recyclerViewPasajero_Guia);
        rv_Pasajero.setHasFixedSize(true);
        rv_Pasajero.setLayoutManager(rv_layoutManager);
        rv_Pasajero.setAdapter(pAdapter);
        //

    }

    private void loadListPasajero(String idguia) {
        mListPasajero.clear();
        //
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase database = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListPasajero(database);
        //
        while (cursor.moveToNext()) {
            try {
                String id = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_GUIAID_PASAJERO));
                if (id.equalsIgnoreCase(idguia)) {
                    String nombre = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NOMBRE_PASAJERO));
                    String edad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_EDAD_PASAJERO));
                    String ocupacion = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_OCUPACION_PASAJERO));
                    String nacionalidad = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NACIONALIDAD_PASAJERO));
                    String numBoleta = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_NUMBOLETA_PASAJERO));
                    String dni = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DNI_PASAJERO));
                    String destino = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_PASAJERO));
                    mListPasajero.add(new Pasajero(nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino));
                }
                Log.e(TAG, "id : " + id + " / " + "idguiaManifiesto : " + idguia);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        pAdapter.notifyDataSetChanged();
        cursor.close();
        mySqliteDB.close();

    }


}
