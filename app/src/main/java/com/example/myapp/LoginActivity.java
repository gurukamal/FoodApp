package com.example.myapp;

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

public class LoginActivity extends AppCompatActivity {

    EditText lEmail,lPassword;
    Button btnLogin;
    TextView loginReg;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=lEmail.getText().toString().trim();
                String password=lPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    lEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    lPassword.setError("Password is required");
                    return;
                }
                if(password.length()<8){
                    lPassword.setError("Password must be greater than 8 characters");
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(LoginActivity.this,"Login Successful ",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                        }

                });
            }
        });

        loginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

            }
        });
    }
    private void init() {


        lEmail=findViewById(R.id.loginEmail);
        lPassword=findViewById(R.id.loginPassword);
        btnLogin=findViewById(R.id.loginButton);
        loginReg=findViewById(R.id.login_register);
        progressBar=findViewById(R.id.lprogressbar);
        fAuth=FirebaseAuth.getInstance();

    }
}