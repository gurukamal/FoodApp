package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView restRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btnLogout,btnProfile;
    private ArrayList<RestaurantData> arrayList;
    private RecyclerAdapterRest recyclerAdapterRest;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
//new
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        restRecycler.setLayoutManager(layoutManager);
        restRecycler.setHasFixedSize(true);

        //firebase
        databaseReference=FirebaseDatabase.getInstance().getReference();
        //ArrayList
        arrayList=new ArrayList<RestaurantData>();

        //clear arraylist
        ClearAll();
        //get data method

        GetDataFromFirebase();*/


        //profile
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });



    }


    private void GetDataFromFirebase() {

        Query query=databaseReference.child("data");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ClearAll();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RestaurantData restaurantData=new RestaurantData();

                  //  restaurantData.setImg(snapshot.child("img").getValue().toString());
                    restaurantData.setTitle(snapshot.child("title").getValue().toString());

                    arrayList.add(restaurantData);
                }
                recyclerAdapterRest=new RecyclerAdapterRest(getApplicationContext(),arrayList);
                restRecycler.setAdapter(recyclerAdapterRest);
                recyclerAdapterRest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void ClearAll(){
        if(arrayList != null){
            arrayList.clear();

            if (recyclerAdapterRest !=null){
                recyclerAdapterRest.notifyDataSetChanged();
            }
        }
        arrayList=new ArrayList<RestaurantData>();
    }

    private void init() {
        btnProfile=findViewById(R.id.btn_profile);
        btnLogout=findViewById(R.id.btn_logout);
/*
        restRecycler = findViewById(R.id.rest_recycler_view);
*/

    }

    public void getRestaurant(View view) {
        startActivity(new Intent(getApplicationContext(),RestaurantView.class));

    }

    public void getStreet(View view) {
        startActivity(new Intent(getApplicationContext(),StreetView.class));

    }
}