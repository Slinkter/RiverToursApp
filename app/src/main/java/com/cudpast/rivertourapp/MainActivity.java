package com.cudpast.rivertourapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cudpast.rivertourapp.Business.ConsulChoferActivity;
import com.cudpast.rivertourapp.Business.ConsulVehiculoActivity;
import com.cudpast.rivertourapp.Business.ConsultManiActivity;
import com.cudpast.rivertourapp.Business.NewChoferActivity;
import com.cudpast.rivertourapp.Business.AddManifActivity;
import com.cudpast.rivertourapp.Business.NewVehiculoActivity;
import com.cudpast.rivertourapp.SQLite.Utils;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;

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




    private void returnLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void loadInfoUser() {
    }


    public void newManifiesto(View view) {
        Intent intent = new Intent(MainActivity.this, AddManifActivity.class);
        startActivity(intent);
    }

    public void newChofer(View view) {
        Intent intent = new Intent(MainActivity.this, NewChoferActivity.class);
        startActivity(intent);

    }

    public void newVehiculo(View view) {
        Intent intent = new Intent(MainActivity.this, NewVehiculoActivity.class);
        startActivity(intent);

    }

    public void reviewManifiesto(View view) {
        Intent intent = new Intent(MainActivity.this, ConsultManiActivity.class);
        startActivity(intent);
    }

    public void reviewChofer(View view) {
        Intent intent = new Intent(MainActivity.this, ConsulChoferActivity.class);
        startActivity(intent);
    }

    public void reviewVehiculo(View view) {
        Intent intent = new Intent(MainActivity.this, ConsulVehiculoActivity.class);
        startActivity(intent);
    }
}
