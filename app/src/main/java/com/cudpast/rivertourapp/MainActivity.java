package com.cudpast.rivertourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cudpast.rivertourapp.Helper.Common;
import com.cudpast.rivertourapp.Model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Verificar si esta logeado o no
        Log.e("Hola","MainActivity");
        User userLogined = Common.user;
        if (userLogined == null) {
            returnLoginActivity(); // user is not logined , user is new or user close session
        } else {
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

}
