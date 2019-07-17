package com.cudpast.rivertourapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText newName, newLast, newPhone, newEmail, newPassword;
    Button btnNewUser, btnNewReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        newName = findViewById(R.id.newName);
        newLast = findViewById(R.id.newLast);
        newPhone = findViewById(R.id.newPhone);
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);

        btnNewUser = findViewById(R.id.btnNewUser);
        btnNewReturn = findViewById(R.id.btnNewReturn);
    }
}
