package com.cudpast.rivertourapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiService;
import com.cudpast.rivertourapp.Helper.Common;
import com.cudpast.rivertourapp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText userMail, userPassword;

    FirebaseAuth mAuth;

    private ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        //
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        userMail = findViewById(R.id.userMail);
        userPassword = findViewById(R.id.userPassword);
        //
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                goToMain();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

    }

    private void goToMain() {

        String email = "luis.j.cueva@gmail.com";
        String pwd = "MUNDOarsi20";
        //   String email = userMail.getText().toString().trim();
        //   String pwd = userPassword.getText().toString().trim();
        mAuth
                .signInWithEmailAndPassword(email, pwd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.e("1", "1");
                        String uid = authResult.getUser().getUid();
                        obtenerUsuario(uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("2", "2");
                        Toast.makeText(LoginActivity.this, "Usuario no existe \n o \n  password incorrecto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }

    private void obtenerUsuario(final String uid) {
        Log.e("3", " uid = " + uid);
        apiInterface = ApiService.getApiRetrofitConexion().create(ApiInterface.class);
        Call<User> getUser = apiInterface.getUser(uid);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("4", "1");
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(" response", " " + response.message());
                    Log.e(" response", " " + response.toString());
                    Log.e(" response", " " + response.code());
                    Boolean success = response.body().getSuccess();
                    Log.e("5", "1");
                    if (success) {
                        progressDialog.dismiss();
                        Log.e("1", "1");
                        Log.e("obtenerUsuario", " response : Success");
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getMessage());
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getFirstname());
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getLastname());
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getDni());
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getCorreo());
                        Log.e("obtenerUsuario", " response.body().getMessage() = " + response.body().getNumphone());
                        User user = new User();
                        user.setFirstname(response.body().getFirstname());
                        user.setLastname(response.body().getLastname());
                        user.setDni(response.body().getDni());
                        user.setCorreo(response.body().getCorreo());
                        user.setNumphone(response.body().getNumphone());
                        user.setUid(uid);
                        Common.user = user;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("6", "1");
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("7", "1");
                Log.e("8", "8   " + t.getMessage());
                progressDialog.dismiss();
            }
        });


    }

    private void goToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
