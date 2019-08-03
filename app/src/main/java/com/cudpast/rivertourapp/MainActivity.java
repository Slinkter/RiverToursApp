package com.cudpast.rivertourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cudpast.rivertourapp.Business.ConsulChoferActivity;
import com.cudpast.rivertourapp.Business.ConsulVehiculoActivity;
import com.cudpast.rivertourapp.Business.ConsultManiActivity;
import com.cudpast.rivertourapp.Business.UpdateChoferActivity;
import com.cudpast.rivertourapp.Business.NewManifActivity;
import com.cudpast.rivertourapp.Business.UpdateVehiculoActivity;
import com.cudpast.rivertourapp.Helper.Common;
import com.cudpast.rivertourapp.Model.User;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Verificar si esta logeado o no

        User userLogined = Common.user;
        if (userLogined == null) {
            Log.e(TAG, "onStart  :  user = null ");
            returnLoginActivity(); // user is not logined , user is new or user close session
        } else {
            Log.e(TAG, "onStart  :  loadInfoUser()");
            Log.e(TAG, "onStart  :  user = " + userLogined.getFirstname() + " " + userLogined.getLastname());
            loadInfoUser();// user is logined
        }

    }

    private void returnLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void loadInfoUser() {
    }


    public void newManifiesto(View view) {
        Intent intent = new Intent(MainActivity.this, NewManifActivity.class);
        startActivity(intent);
    }

    public void newChofer(View view) {
        Intent intent = new Intent(MainActivity.this, UpdateChoferActivity.class);
        startActivity(intent);

    }

    public void newVehiculo(View view) {
        Intent intent = new Intent(MainActivity.this, UpdateVehiculoActivity.class);
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
