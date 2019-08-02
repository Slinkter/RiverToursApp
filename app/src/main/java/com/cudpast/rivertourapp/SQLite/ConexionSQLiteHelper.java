package com.cudpast.rivertourapp.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import static com.cudpast.rivertourapp.SQLite.Utils.CREATE_TABLA_USUARIO;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    String drop_usuario = "DROP TABLE IF EXISTS " + Utils.TABLA_USUARIO;


    public ConexionSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_usuario);
        onCreate(db);

    }
}
