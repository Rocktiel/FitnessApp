package com.example.fitnessappproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentInfo extends Fragment {

    View view;

    RecyclerView rv;


    DatabaseReference databaseReference;

    List<Calorie> calorieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_info, container, false);

        calorieList= new ArrayList<>();

        rv=view.findViewById(R.id.myrecyclerinfo);

        Intent ii=getActivity().getIntent();
        String aa= ii.getStringExtra("email");



        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kalorideneme-default-rtdb.firebaseio.com/");

        Query query=databaseReference.child("Users").child(aa).child("Öğünler");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String tarih=dataSnapshot.child("Tarih").getValue().toString();
                    String kalori=dataSnapshot.child("Kalori").getValue().toString();
                    String vakit=dataSnapshot.child("Öğün").getValue().toString();


                    calorieList.add(new Calorie(tarih,kalori,vakit));

                }

                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

                rv.setAdapter(new MyAdapterCalorie(view.getContext(), calorieList));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
}