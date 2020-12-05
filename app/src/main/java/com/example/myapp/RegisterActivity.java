package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText rName,rEmail,rPassword,rPasswordAgain,rPhone;
    Button rRegister;
    TextView rLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

            finish();
        }
        rRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String reg_email=rEmail.getText().toString().trim();
               String reg_password=rPassword.getText().toString().trim();
                String reg_phone=rPhone.getText().toString().trim();
                String reg_name=rName.getText().toString().trim();


                if (TextUtils.isEmpty(reg_email)){
                    rEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(reg_password)){
                    rPassword.setError("Password is required");
                    return;
                }
                if(reg_password.length()<8){
                    rPassword.setError("Password must be greater than 8 characters");
                }
                progressBar.setVisibility(View.VISIBLE);

                //register the user with firebase
                fAuth.createUserWithEmailAndPassword(reg_email,reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User created",Toast.LENGTH_LONG).show();
                            uId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=firebaseFirestore.collection("users").document(uId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fullName",reg_name);
                            user.put("email",reg_email);
                            user.put("phone",reg_phone);
                            documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("TAG", "onComplete: user-profile is created " +uId);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error" +task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });

        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });
    }

    private void init() {

        rName=findViewById(R.id.et_rfname);
        rEmail=findViewById(R.id.register_email);
        rPhone=findViewById(R.id.et_rphone);
        rPassword=findViewById(R.id.register_password);
        rPasswordAgain=findViewById(R.id.et_ragain_password);
        rRegister=findViewById(R.id.register_btn);
        rLogin=findViewById(R.id.tv_rsignin);
        progressBar=findViewById(R.id.rprogressbar);

        fAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }


}