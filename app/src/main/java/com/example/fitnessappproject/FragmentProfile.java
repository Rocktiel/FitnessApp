package com.example.fitnessappproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentProfile extends Fragment {

    DatabaseReference databaseReference;
    TextView a,b,c,d,e,f;
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        Intent ii=getActivity().getIntent();
        String aa= ii.getStringExtra("email");

        a=view.findViewById(R.id.profileemail);
        b=view.findViewById(R.id.profileisim);
        c=view.findViewById(R.id.profilesoyisim);
        d=view.findViewById(R.id.profileyas);
        e=view.findViewById(R.id.profileagırlık);
        f=view.findViewById(R.id.profileboy);

        readData(aa);


        return view;
    }

    private void readData(String aa) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(aa).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot=task.getResult();

                        String smail=String.valueOf(dataSnapshot.child("Username").getValue());
                        String sad=String.valueOf(dataSnapshot.child("Ad").getValue());
                        String ssoyad=String.valueOf(dataSnapshot.child("Soyad").getValue());
                        String syas=String.valueOf(dataSnapshot.child("Yaş").getValue());
                        String sagirlik=String.valueOf(dataSnapshot.child("Ağırlık").getValue());
                        String sboy=String.valueOf(dataSnapshot.child("Boy").getValue());

                        a.setText(smail);
                        b.setText(sad);
                        c.setText(ssoyad);
                        d.setText(syas);
                        e.setText(sagirlik);
                        f.setText(sboy);

                    }else{
                        Toast.makeText(view.getContext(), "Doesntexist", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(view.getContext(), "Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}