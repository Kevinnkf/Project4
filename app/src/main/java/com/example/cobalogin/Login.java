package com.example.cobalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn, btnreg;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.login);
        progressbar = findViewById(R.id.progressbar);
        btnreg = findViewById(R.id.btntoreg);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });



        Btn.setOnClickListener(view -> loginUser());
    }
    private void loginUser(){
        progressbar.setVisibility(View.VISIBLE);
        String email, password;

        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please fill in the email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please fill in the password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(Login.this, "login failed! Check username and password", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}