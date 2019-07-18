package com.cudpast.rivertourapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cudpast.rivertourapp.Helper.ApiInterface;
import com.cudpast.rivertourapp.Helper.ApiRetrofit;
import com.cudpast.rivertourapp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText newName, newLast, newDNI, newPhone, newEmail, newPassword;
    Button btnNewUser, btnNewReturn;

    private Vibrator vibrator;
    private Animation animation;

    private ApiInterface apiInterface;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        //Animación de error al ingresar dato en el formulario
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //
        newName = findViewById(R.id.newName);
        newLast = findViewById(R.id.newLast);
        newDNI = findViewById(R.id.newDNI);
        newPhone = findViewById(R.id.newPhone);
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        btnNewUser = findViewById(R.id.btnNewUser);
        btnNewReturn = findViewById(R.id.btnNewReturn);

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (newform()) {
                    Log.e("newForn()", "validacion ok ");
                    createNewUser();
                }

            }
        });
        btnNewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    private void createNewUser() {
        String email = newEmail.getText().toString();
        String pwd = newPassword.getText().toString();
        mAuth
                .createUserWithEmailAndPassword(email, pwd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.e("mAuth", " registro ok ");
                        String UID = authResult.getUser().getUid();
                        insertServerRegister(UID);
                        Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e("mAuth", " registro error ");
                        Toast.makeText(RegisterActivity.this, "Error crear usuario", Toast.LENGTH_SHORT).show();
                    }
                })
        ;

    }

    private void insertServerRegister(String uid) {

        String firstname = newName.getText().toString();
        String lastname = newLast.getText().toString();
        String dni = newDNI.getText().toString();
        String correo = newEmail.getText().toString();
        String numphone = newPhone.getText().toString();

        apiInterface = ApiRetrofit.getApiRetrofitConexion().create(ApiInterface.class);
        Call<User> userInsert = apiInterface.insertUser(firstname, lastname, dni, correo, numphone, uid);
        userInsert.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.e(" response", " " + response.message());
                Log.e(" response", " " + response.toString());
                Log.e(" response", " " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        progressDialog.dismiss();
                        goToLogin();
                        Log.e("remoteBD", " onResponse : Success");
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    } else {
                        progressDialog.dismiss();
                        Log.e("remoteBD", " onResponse : fail");
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", " response =  " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("remoteBD", " onResponse : fail" + t.toString() + "\n " + t.getCause());
                Log.e("remoteBD", " onResponse : fail");
                Log.e("onFailure", " response =  " + t.getMessage());

            }
        });
    }

    private boolean newform() {

        if (!vNewName()) {
            newName.setAnimation(animation);
            newName.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }

        if (!vNewLast()) {
            newLast.setAnimation(animation);
            newLast.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }
        if (!vNewPhone()) {
            newPhone.setAnimation(animation);
            newPhone.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }
        if (!vNewEmail()) {
            newEmail.setAnimation(animation);
            newEmail.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }
        if (!vNewPwd()) {
            newPassword.setAnimation(animation);
            newPassword.startAnimation(animation);
            vibrator.vibrate(120);
            return false;
        }

        return true;
    }

    //validacion de campos
    private boolean vNewName() {
        if (newName.getText().toString().isEmpty() && newName.getText().toString().length() <= 3) {
            newName.setError("error");
            return false;
        }
        return true;
    }

    private boolean vNewLast() {
        if (newLast.getText().toString().isEmpty() && newLast.getText().toString().length() <= 3) {
            newName.setError("error");
            return false;
        }
        return true;
    }

    private boolean vNewPhone() {
        if (newPhone.getText().toString().isEmpty() && newPhone.getText().toString().length() <= 3) {
            newName.setError("error");
            return false;
        }
        return true;
    }

    private boolean vNewEmail() {
        if (newEmail.getText().toString().isEmpty() && newEmail.getText().toString().length() <= 3) {
            newName.setError("error");
            return false;
        }
        return true;
    }

    private boolean vNewPwd() {
        if (newName.getText().toString().isEmpty() && newName.getText().toString().length() <= 3) {
            newName.setError("error");
            return false;
        }
        return true;
    }


    private void goToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
