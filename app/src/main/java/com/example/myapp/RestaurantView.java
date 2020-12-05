package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantView extends AppCompatActivity {

    Button btnViewRestaurant;
    ImageView imgRestaurant;
    TextView tvTitleRestaurant;
    private RecyclerView mFirestoreList;
    FirebaseFirestore firebaseFirestore;
    DocumentReference reference=firebaseFirestore.collection("restaurant").document();

    String UserId;
    FirebaseAuth firebaseAuth;
    private List<String> restList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        init();


       reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){
                    Object id=documentSnapshot.get("Id");
                    String title=documentSnapshot.getString("title");

                    tvTitleRestaurant.setText(title);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RestaurantView.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        firebaseFirestore.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
     /*   DocumentReference documentReference=firebaseFirestore.collection("restaurant").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvTitleRestaurant.setText(value.getString("title"));
*/

/*
        firebaseFirestore.collection("restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                restList.clear();
                for (DocumentSnapshot snapshot:value){
                    restList.add(snapshot.getString("title"));
                    restList.add(snapshot.getString("image"));


                }
                ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_restaurant_view,restList);
                adapter.notifyDataSetChanged();
                restList.setAdapter(adapter);
            }
        });*/
    }

    private void init() {
       /* btnViewRestaurant=findViewById(R.id.btn_view_restaurant);
        tvTitleRestaurant=findViewById(R.id.rest_title);
        imgRestaurant=findViewById(R.id.rest_img1);*/
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        UserId=firebaseAuth.getCurrentUser().getUid();

        //mFirestoreList=findViewById(R.id.recyclerList);
    }
}