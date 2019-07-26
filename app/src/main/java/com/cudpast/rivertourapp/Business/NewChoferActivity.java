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
import com.cudpast.rivertourapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewChoferActivity extends AppCompatActivity {

    EditText newNameChofer, newLastChofer, newDNIChofer, newBreveChofer, newPhoneChofer;
    Button btnNewChofer, btnSalirDoctor;

    private ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_chofer);

        newNameChofer = findViewById(R.id.newNameChofer);
        newLastChofer = findViewById(R.id.newLastChofer);
        newDNIChofer = findViewById(R.id.newDNIChofer);
        newBreveChofer = findViewById(R.id.newBreveChofer);
        newPhoneChofer = findViewById(R.id.newPhoneChofer);

        btnNewChofer = findViewById(R.id.btnNewChofer);
        btnSalirDoctor = findViewById(R.id.btnSalirDoctor);


        btnNewChofer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertServerChofer();

            }
        });
        btnSalirDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoMain();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

    }


    private void insertServerChofer() {
        progressDialog.show();
        String firstname = newNameChofer.getText().toString();
        String lastname = newLastChofer.getText().toString();
        String dni = newDNIChofer.getText().toString();
        String brevete = newBreveChofer.getText().toString();
        String numphone = newPhoneChofer.getText().toString();

        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<Chofer> userInsert = apiInterface.insertChofer(firstname, lastname, dni, brevete, numphone);
        userInsert.enqueue(new Callback<Chofer>() {
            @Override
            public void onResponse(Call<Chofer> call, Response<Chofer> response) {

                Log.e(" response", " " + response.message());
                Log.e(" response", " " + response.toString());
                Log.e(" response", " " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        gotoMain();
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : Success");
                        Toast.makeText(NewChoferActivity.this, "Chofer registrado", Toast.LENGTH_SHORT).show();

                        Log.e("TAG", " response =  " + response.body().getMessage());
                    } else {
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(NewChoferActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Chofer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewChoferActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("remoteBD", " onResponse : fail" + t.toString() + "\n " + t.getCause());
                Log.e("remoteBD", " onResponse : fail");
                Log.e("onFailure", " response =  " + t.getMessage());

            }
        });
    }

    private void gotoMain(){
        Intent intent = new Intent(NewChoferActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
