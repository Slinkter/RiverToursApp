package com.cudpast.rivertourapp.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import static com.cudpast.rivertourapp.SQLite.Utils.CAMPO_PLACA_VEHICULO;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_CHOFER;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_USUARIO;
import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_VEHICULO;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbRiverTour";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public static final String drop_vehiculo = "DROP TABLE IF EXISTS " + Utils.TABLA_VEHICULO;
    public static final String drop_chofer = "DROP TABLE IF EXISTS " + Utils.TABLA_CHOFER;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_VEHICULO);
        db.execSQL(CREATE_TABLA_CHOFER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_vehiculo);
        db.execSQL(drop_chofer);

        onCreate(db);
    }

    public Cursor readFromLocalDatabaseVehiculo(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_NOMBRE_VEHICULO, Utils.CAMPO_MARCA_VEHICULO, Utils.CAMPO_MATRICULA_VEHICULO, CAMPO_PLACA_VEHICULO};
        return (database.query(Utils.TABLA_VEHICULO, projection, null, null, null, null, null));
    }

    public Cursor readFromLocalDatabaseChofer(SQLiteDatabase database) {
        String[] projection = {Utils.CAMPO_NOMBRE_CHOFER, Utils.CAMPO_APELLIDO_CHOFER, Utils.CAMPO_DNI_CHOFER,Utils.CAMPO_BREVETE_CHOFER, Utils.CAMPO_TELEFONO_CHOFER};
        return (database.query(Utils.TABLA_CHOFER, projection, null, null, null, null, null));
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
}
