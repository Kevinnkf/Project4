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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private TextView lgnTextView;
    private Button Btn, btnLogin;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//inititate firebase
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        btnLogin = findViewById(R.id.btntologin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


//        lgnTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(register.this)
//            }
//        });




//klik buttonnya mau kemana
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }
    //function register
    private void registerNewUser(){
        progressbar.setVisibility(View.VISIBLE);

        String email,password;
        email = emailTextView.getText().toString().trim();
        password = passwordTextView.getText().toString().trim();

        //validasi kalo formnya kosong
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //function kalo kalo validasinya udah passed semua
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //registration success
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(), "idk please try again, theres something wrong tho", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


     


}