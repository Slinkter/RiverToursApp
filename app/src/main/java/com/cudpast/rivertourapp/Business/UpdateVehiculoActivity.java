package com.cudpast.rivertourapp.Business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.DbHelper;
import com.cudpast.rivertourapp.SQLite.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cudpast.rivertourapp.SQLite.Utils.db_version;

public class UpdateVehiculoActivity extends AppCompatActivity {

    EditText newNombreVehiculo, newMarcaVehiculo, newMatriculaVehiculo, newPlacaVehiculo;
    Button btnNewVehiculo, btnSalirVehiculo;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    public static final String TAG = "UpdateVehiculoActivity";

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
                //   insertVehiculo();
                if (insertVehiculoSQLite()) {
                    Log.e(TAG, "INSERTO EL Vehiculo");
                } else {
                    Log.e(TAG, "No INSERTO EL vehiculo");
                }


            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    private Boolean insertVehiculoSQLite() {
        progressDialog.show();
        try {
            String nomV = newNombreVehiculo.getText().toString();
            String marV = newMarcaVehiculo.getText().toString();
            String matV = newMatriculaVehiculo.getText().toString();
            String plaV = newPlacaVehiculo.getText().toString();

            //1.Conexion
            DbHelper conn = new DbHelper(this);
            SQLiteDatabase db = conn.getWritableDatabase();
            String insert = "INSERT INTO " + Utils.TABLA_VEHICULO + "(" +
                    Utils.CAMPO_NOMBRE_VEHICULO + "," +
                    Utils.CAMPO_MARCA_VEHICULO + "," +
                    Utils.CAMPO_MATRICULA_VEHICULO + "," +
                    Utils.CAMPO_PLACA_VEHICULO + ")" +
                    " VALUES ('" + nomV + "','" + marV + "','" + matV + "','" + plaV + "')";

            Log.e(TAG, "insert Usuario :" + insert);
            db.execSQL(insert);
            db.close();
            Toast.makeText(this, "Si se inserto ", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            gotoMain();
            return true;
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, " error : No se inserto ", Toast.LENGTH_SHORT).show();
            return false;
        }


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
                        Toast.makeText(UpdateVehiculoActivity.this, "Vehiculo registrado", Toast.LENGTH_SHORT).show();

                        gotoMain();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    } else {
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(UpdateVehiculoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdateVehiculoActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("remoteBD", " onResponse : fail" + t.toString() + "\n " + t.getCause());
                Log.e("remoteBD", " onResponse : fail");
                Log.e("onFailure", " response =  " + t.getMessage());

            }
        });
    }

    private void gotoMain() {
        Intent intent = new Intent(UpdateVehiculoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
