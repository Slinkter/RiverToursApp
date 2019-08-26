package com.cudpast.rivertourapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cudpast.rivertourapp.Business.AddPasajeroActivity;
import com.cudpast.rivertourapp.Business.NewManifActivity;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.Model.Pasajero;

import okhttp3.internal.Util;

import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_PLACA_VEHICULO;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_CHOFER;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_MANIFIESTO;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_PASAJERO;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_VEHICULO;

public class MySqliteDB extends SQLiteOpenHelper {

    public static final String TAG = MySqliteDB.class.getSimpleName();
    private static final String DATABASE_NAME = "dbRiverTour";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    //
    public static final String drop_vehiculo = "DROP TABLE IF EXISTS " + Utils.TABLA_VEHICULO;
    public static final String drop_chofer = "DROP TABLE IF EXISTS " + Utils.TABLA_CHOFER;
    public static final String drop_pasajero = "DROP TABLE IF EXISTS " + Utils.TABLA_PASAJERO;
    public static final String drop_manifiesto = "DROP TABLE IF EXISTS " + Utils.TABLA_MANIFIESTO;

    //
    public MySqliteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_VEHICULO);
        db.execSQL(CREATE_TABLA_CHOFER);
        db.execSQL(CREATE_TABLA_PASAJERO);
        db.execSQL(CREATE_TABLA_MANIFIESTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_vehiculo);
        db.execSQL(drop_chofer);
        db.execSQL(drop_pasajero);
        db.execSQL(drop_manifiesto);
        onCreate(db);
    }

    //*************************************
    //          Metodos de Insert
    //***********************************
    // Insert Manifiesto
    public void mySaveToLocalDBManifiesto(Manifiesto manifiesto, int sync, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        //Log test de data;
        Log.e(TAG, "  Id Guia Mani " + manifiesto.getIdGuiaMani());
        //Set data
        contentValues.put(Utils.CAMPO_ID_GUIA, manifiesto.getIdGuiaMani());
        contentValues.put(Utils.CAMPO_FECHA_MANIFIESTO, manifiesto.getFechaMani());
        contentValues.put(Utils.CAMPO_DESTINO_MANIFIESTO, manifiesto.getDestinoMani());
        contentValues.put(Utils.CAMPO_VEHICULO_MANIFIESTO, manifiesto.getVehiculoMani());
        contentValues.put(Utils.CAMPO_CHOFER_MANIFIESTO, manifiesto.getChoferMani());
        contentValues.put(Utils.CAMPO_SYNC_STATUS_MANIFIESTO, sync);
        //Insert
        long hola = db.insert(Utils.TABLA_MANIFIESTO, null, contentValues);
        Log.e(TAG, "INSERT TABLA_MANIFIESTO " + hola);
    }

    // Insert Pasajero
    public void mySaveToLocalDBPasajero(Pasajero pasajero, SQLiteDatabase database) {

        Log.e("mySaveToLocalDBPasajero", "pasajero : " + pasajero.getNombrePasajero());
        Log.e("mySaveToLocalDBPasajero", "pasajero : " + pasajero.getEdadPasajero());
        Log.e("mySaveToLocalDBPasajero", "pasajero : " + pasajero.getOcupacionPasajero());
        Log.e("mySaveToLocalDBPasajero", "pasajero : " + pasajero.getNacionalidadPasajero());
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.CAMPO_NOMBRE_PASAJERO, pasajero.getNombrePasajero());
        contentValues.put(Utils.CAMPO_EDAD_PASAJERO, pasajero.getEdadPasajero());
        contentValues.put(Utils.CAMPO_OCUPACION_PASAJERO, pasajero.getOcupacionPasajero());
        contentValues.put(Utils.CAMPO_NACIONALIDAD_PASAJERO, pasajero.getNacionalidadPasajero());
        contentValues.put(Utils.CAMPO_NUMBOLETA_PASAJERO, pasajero.getNumBoleta());
        contentValues.put(Utils.CAMPO_DNI_PASAJERO, pasajero.getDniPasajero());
        contentValues.put(Utils.CAMPO_DESTINO_PASAJERO, pasajero.getDestinoPasajero());
        // falta
        long hola = database.insert(Utils.TABLA_PASAJERO, null, contentValues);

        if (hola > 0) {
            Log.e("mySaveToLocalDBPasajero", "hola  " + hola);
        } else {
            Log.e("mySaveToLocalDBPasajero", "hola " + hola);
        }

    }

    public void mySaveToLocalDBPasajero2(Pasajero pasajero, SQLiteDatabase database) {


    }

    public Cursor getListVehiculo(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_NOMBRE_VEHICULO, Utils.CAMPO_MARCA_VEHICULO, Utils.CAMPO_MATRICULA_VEHICULO, CAMPO_PLACA_VEHICULO};
        return (database.query(Utils.TABLA_VEHICULO, projection, null, null, null, null, null));
    }

    public Cursor getListChofer(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_NOMBRE_CHOFER, Utils.CAMPO_APELLIDO_CHOFER, Utils.CAMPO_DNI_CHOFER, Utils.CAMPO_BREVETE_CHOFER, Utils.CAMPO_TELEFONO_CHOFER};
        return (database.query(Utils.TABLA_CHOFER, projection, null, null, null, null, null));
    }

    public Cursor getListPasajero(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_NOMBRE_PASAJERO, Utils.CAMPO_EDAD_PASAJERO, Utils.CAMPO_OCUPACION_PASAJERO, Utils.CAMPO_NACIONALIDAD_PASAJERO, Utils.CAMPO_NUMBOLETA_PASAJERO, Utils.CAMPO_DNI_PASAJERO, Utils.CAMPO_DESTINO_PASAJERO, Utils.CAMPO_GUIAID_PASAJERO};
        return (database.query(Utils.TABLA_PASAJERO, projection, null, null, null, null, null));
    }

    public Cursor getListManifiesto(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_ID_GUIA, Utils.CAMPO_FECHA_MANIFIESTO, Utils.CAMPO_DESTINO_MANIFIESTO, Utils.CAMPO_VEHICULO_MANIFIESTO, Utils.CAMPO_CHOFER_MANIFIESTO, Utils.CAMPO_SYNC_STATUS_MANIFIESTO};
        return (database.query(Utils.TABLA_MANIFIESTO, projection, null, null, null, null, null));
    }


    public void myUpdateManifiesto(String idGuia, int sync_status, SQLiteDatabase db) {

        ContentValues fiels = new ContentValues();
        fiels.put(Utils.CAMPO_SYNC_STATUS_MANIFIESTO, sync_status); // campo y nuevo valor
        String sql = Utils.CAMPO_ID_GUIA + " LIKE ?"; // idGuia
        String[] newItem = {idGuia}; // variable
        db.update(Utils.TABLA_MANIFIESTO, fiels, sql, newItem);
    }


    public void deleteTable() {
        if (db == null || !db.isOpen())
            db = getWritableDatabase();
        db.execSQL(drop_vehiculo);
        db.execSQL(CREATE_TABLA_VEHICULO);
    }

    public void deleteTableChofer() {
        if (db == null || !db.isOpen())
            db = getWritableDatabase();
        db.execSQL(drop_chofer);
        db.execSQL(CREATE_TABLA_CHOFER);
    }

    public void deleteTablePasajero() {
        if (db == null || !db.isOpen())
            db = getWritableDatabase();
        db.execSQL(drop_pasajero);
        db.execSQL(CREATE_TABLA_PASAJERO);
    }
}
