package com.cudpast.rivertourapp.Business.Support;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cudpast.rivertourapp.MainActivity;
import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;
import com.cudpast.rivertourapp.SplashActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ShowPDFActivity extends AppCompatActivity {

    public static final String TAG = ShowPDFActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private Button btn_aux_pdf;

    //==============================================================================================
    private String[] header = {"Nombre", "Edad", "Ocupacion", "Nacionalidad", "Numero", "DNI", "Destino"};
    String shortText = "Lista de pasajero";
    String longText = "Lorem to dkiifafas ";
    TemplatePDF templatePDF;
    String idguiaManifiesto;
    //=========================================================================== ===================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);


        if (getIntent() != null) {
            idguiaManifiesto = getIntent().getStringExtra("idguiaManifiesto");
        }
        //==============================================================================================
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                Log.e(TAG, " La version API es >= de 23 ");
                if (checkPermissionWrite()) {
                    Log.e(TAG, " Checkear permisos ");
                    init();
                } else {
                    Log.e(TAG, " Requeriendo permisos ");
                    requestPErmissionWrite();
                }
            } else {

                btn_aux_pdf = findViewById(R.id.btn_aux_pdf);
                btn_aux_pdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            templatePDF.viewPDF();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "error : " + e.getMessage());
                            Log.e(TAG, "error : " + e.getCause());
                            Toast.makeText(ShowPDFActivity.this, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.e(TAG, " La version API es < de 23 ");


            }
        } catch (Exception e) {
            Toast.makeText(this, "Versión de API no compatible", Toast.LENGTH_SHORT).show();
        }
        //==============================================================================================
    }

    private ArrayList<String[]> loadListPasajero(String idguia) {
        ArrayList<String[]> rows = new ArrayList<>();
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
                    rows.add(new String[]{nombre, edad, ocupacion, nacionalidad, numBoleta, dni, destino});
                }
                Log.e(TAG, "id : " + id + " / " + "idguiaManifiesto : " + idguia);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        mySqliteDB.close();
        return rows;
    }

    private void init() {
        templatePDF = new TemplatePDF(ShowPDFActivity.this);
        templatePDF.openDocument();
        templatePDF.addMetada("Clientes", "Ventas", "Liam");
        templatePDF.addTitles("Lista de pasajero", "RiverTour", "2019");
        templatePDF.addParagraph(shortText);
        templatePDF.addParagraph(longText);
        templatePDF.addCreateTable(header, loadListPasajero(idguiaManifiesto));
        templatePDF.closeDocument();
    }

    public void btn_pdfView(View view) {
        try {
            templatePDF.viewPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error : " + e.getMessage());
            Log.e(TAG, "error : " + e.getCause());
            Toast.makeText(this, "Generar al carga el pdf", Toast.LENGTH_SHORT).show();
        }
    }


    //==============================================================================================

    private boolean checkPermissionWrite() {
        int result = ContextCompat.checkSelfPermission(ShowPDFActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, " CheckPermissionWrite()  = 0 ");
            return true;
        } else {
            Log.e(TAG, " CheckPermissionWrite() =  1");
            return false;
        }
    }

    // Pregunta al usuario si Permite acceder
    private void requestPErmissionWrite() {
        ActivityCompat.requestPermissions(ShowPDFActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        Log.e(TAG, "requestPErmissionWrite: 1");
    }

    // DESPUES  DE REQUERIR EL PERMISO APARECE UN CUADRO DE PERMISOS
    // SI  ACEPTA  : Permission Granted
    // SINO SE ACEPTA : Permission Denied
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");

                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
