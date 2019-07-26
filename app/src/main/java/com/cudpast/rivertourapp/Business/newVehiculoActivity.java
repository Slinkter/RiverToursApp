package com.cudpast.rivertourapp.Business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cudpast.rivertourapp.R;

public class newVehiculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_vehiculo);
    }
}
