package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cudpast.rivertourapp.Adapter.ManifiestoAdapter;
import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.MainActivity;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.MySqliteDB;
import com.cudpast.rivertourapp.SQLite.Utils;

import java.util.ArrayList;

public class QueryManifiestoActivity extends AppCompatActivity {

    public static final String TAG = QueryManifiestoActivity.class.getSimpleName();
    //
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ManifiestoAdapter mAdapter;
    private ApiInterface apiInterface;
    ArrayList<Manifiesto> mListOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_consult_mani);
        init();
    }

    private void init() {
        pDialog = new ProgressDialog(QueryManifiestoActivity.this);
        pDialog.setMessage("Actualizando....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        loadOfflineListManifiesto();
    }

    private void loadOfflineListManifiesto() {
        Log.e(TAG, "======= loadOfflineListManifiesto() =======");
        mListOff = new ArrayList<>();
        mListOff.clear();
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        SQLiteDatabase db = mySqliteDB.getReadableDatabase();
        Cursor cursor = mySqliteDB.getListManifiesto(db);

        while (cursor.moveToNext()) {
            //
            String idguia = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_ID_GUIA));
            String fechaMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_FECHA_MANIFIESTO));
            String destinoMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_DESTINO_MANIFIESTO));
            String vehiculoMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_VEHICULO_MANIFIESTO));
            String choferMani = cursor.getString(cursor.getColumnIndex(Utils.CAMPO_CHOFER_MANIFIESTO));
            int syncMani = cursor.getInt(cursor.getColumnIndex(Utils.CAMPO_SYNC_STATUS_MANIFIESTO));
            //
            Manifiesto manifiesto = new Manifiesto();
            manifiesto.setIdGuiaMani(idguia);
            manifiesto.setFechaMani(fechaMani);
            manifiesto.setDestinoMani(destinoMani);
            manifiesto.setVehiculoMani(vehiculoMani);
            manifiesto.setChoferMani(choferMani);
            manifiesto.setSync_status(syncMani);
            //
            String cadena ="================================" + "\n"+
                            "idguia : " + idguia +"\n"+
                            "fechaMani : " + fechaMani +"\n"+
                            "destinoMani : " + destinoMani +"\n"+
                            "vehiculoMani : " + vehiculoMani +"\n"+
                            "choferMani : " + choferMani +"\n"+
                            "syncMani : " + syncMani;
            Log.e(TAG, cadena);
            //
            mListOff.add(manifiesto);
        }

        //
        mAdapter = new ManifiestoAdapter(mListOff);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new ManifiestoAdapter.OnItemClickListener() {
            @Override
            public void onShowSync(int position) {
                showsync(position);
            }

            @Override
            public void onShowListManfiesto(int position) {
                showlist(position);
            }

            @Override
            public void onShowPdfManfiesto(int position) {
                showpdft(position);
            }
        });
        //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view_manifiesto);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        pDialog.dismiss();
        cursor.close();
        mySqliteDB.close();
    }

    private void showsync(int position) {
        Toast.makeText(this, "es 1 " + position, Toast.LENGTH_SHORT).show();
    }

    private void showlist(int position) {
        Toast.makeText(this, " lista de pasajero " + position, Toast.LENGTH_SHORT).show();
    }

    private void showpdft(int position) {
        Toast.makeText(this, " generar pdf " + position, Toast.LENGTH_SHORT).show();
    }

    public void backToMain(View view) {
        Intent i = new Intent(QueryManifiestoActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void updateListManifiesto(String idguia, String fechaMani, String destinoMani, String vehiculoMani, String choferMani) {
        try {
            MySqliteDB conn = new MySqliteDB(this);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " +
                    Utils.TABLA_MANIFIESTO + "( " +
                    Utils.CAMPO_ID_GUIA + "," +
                    Utils.CAMPO_FECHA_MANIFIESTO + "," +
                    Utils.CAMPO_DESTINO_MANIFIESTO + "," +
                    Utils.CAMPO_VEHICULO_MANIFIESTO + "," +
                    Utils.CAMPO_CHOFER_MANIFIESTO + ")" +
                    " VALUES ('" +
                    idguia + "','" +
                    fechaMani + "','" +
                    destinoMani + "','" +
                    vehiculoMani + "','" +
                    choferMani + "')";
            db.execSQL(insert);
            db.close();
            Log.e(TAG, " INSERT : updateListManifiesto \n " + insert);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, " ERROR :  updateListManifiesto");
        }
    }

    private void destroyDBManifiesto() {
        Log.e(TAG, " destroyDBManifiesto :  eliminar  drop table manifiesto ");
        MySqliteDB mySqliteDB = new MySqliteDB(this);
        mySqliteDB.deleteTableManifiesto();
    }
}
