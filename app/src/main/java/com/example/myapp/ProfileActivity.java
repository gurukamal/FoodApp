package com.example.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    TextView userName,userEmail,userPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;
    String uId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        DocumentReference documentReference=firebaseFirestore.collection("users").document(uId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName.setText(value.getString("fullName"));
                userEmail.setText(value.getString("email"));
                userPhone.setText(value.getString("phone"));

            }
        });
    }

    private void init() {
        userName=findViewById(R.id.tv_profile_name);
        userEmail=findViewById(R.id.tv_profile_email);
        userPhone=findViewById(R.id.tv_profile_phone);
        fAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        uId=fAuth.getCurrentUser().getUid();
    }
}