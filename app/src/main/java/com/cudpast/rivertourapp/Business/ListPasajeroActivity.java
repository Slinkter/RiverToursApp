package com.cudpast.rivertourapp.Business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cudpast.rivertourapp.R;

public class ListPasajeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("   ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list_pasajero);
    }
}
