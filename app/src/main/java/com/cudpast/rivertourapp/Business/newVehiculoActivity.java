package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.MainActivity;
import com.cudpast.rivertourapp.Model.Chofer;
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newVehiculoActivity extends AppCompatActivity {

    EditText newNombreVehiculo, newMarcaVehiculo, newMatriculaVehiculo, newPlacaVehiculo;
    Button btnNewVehiculo, btnSalirVehiculo;


    private ApiInterface apiInterface;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_vehiculo);

        newNombreVehiculo = findViewById(R.id.newNombreVehiculo);
        newMarcaVehiculo = findViewById(R.id.newMarcaVehiculo);
        newMatriculaVehiculo = findViewById(R.id.newMatriculaVehiculo);
        newPlacaVehiculo = findViewById(R.id.newPlacaVehiculo);

        btnNewVehiculo = findViewById(R.id.btnNewVehiculo);
        btnSalirVehiculo = findViewById(R.id.btnSalirVehiculo);

        btnSalirVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });

        btnNewVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertVehiculo();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }


    private void insertVehiculo() {
        progressDialog.show();
        String firstname = newNombreVehiculo.getText().toString();
        String lastname = newMarcaVehiculo.getText().toString();
        String dni = newMatriculaVehiculo.getText().toString();
        String brevete = newPlacaVehiculo.getText().toString();


        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<Vehiculo> userInsert = apiInterface.insertVehiculo(firstname, lastname, dni, brevete);
        userInsert.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {

                Log.e(" response", " " + response.message());
                Log.e(" response", " " + response.toString());
                Log.e(" response", " " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        gotoMain();
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : Success");
                        Toast.makeText(newVehiculoActivity.this, "Vehiculo registrado", Toast.LENGTH_SHORT).show();

                        gotoMain();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    } else {
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(newVehiculoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(newVehiculoActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("remoteBD", " onResponse : fail" + t.toString() + "\n " + t.getCause());
                Log.e("remoteBD", " onResponse : fail");
                Log.e("onFailure", " response =  " + t.getMessage());

            }
        });
    }

    private void gotoMain() {
        Intent intent = new Intent(newVehiculoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
