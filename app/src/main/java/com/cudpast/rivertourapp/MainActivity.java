package com.cudpast.rivertourapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cudpast.rivertourapp.Business.QueryChoferActivity;
import com.cudpast.rivertourapp.Business.QueryVehiculoActivity;
import com.cudpast.rivertourapp.Business.QueryManifiestoActivity;
import com.cudpast.rivertourapp.Other.NewChoferActivity;
import com.cudpast.rivertourapp.Business.NewManifiestoActivity;
import com.cudpast.rivertourapp.Other.NewVehiculoActivity;
import com.cudpast.rivertourapp.SQLite.Utils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(Utils.UI_UPDATE_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


    public void newManifiesto(View view) {
        progressDialog.show();
        Intent intent = new Intent(MainActivity.this, NewManifiestoActivity.class);
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }


    public void reviewManifiesto(View view) {
        progressDialog.show();
        Intent intent = new Intent(MainActivity.this, QueryManifiestoActivity.class);
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }

    public void reviewChofer(View view) {
        progressDialog.show();
        Intent intent = new Intent(MainActivity.this, QueryChoferActivity.class);
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }

    public void reviewVehiculo(View view) {
        progressDialog.show();
        Intent intent = new Intent(MainActivity.this, QueryVehiculoActivity.class);
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }


    public void newChofer(View view) {
        Intent intent = new Intent(MainActivity.this, NewChoferActivity.class);
        startActivity(intent);

    }

    public void newVehiculo(View view) {
        Intent intent = new Intent(MainActivity.this, NewVehiculoActivity.class);
        startActivity(intent);

    }

    private void returnLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void loadInfoUser() {
    }


}
