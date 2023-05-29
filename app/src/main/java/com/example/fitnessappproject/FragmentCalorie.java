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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class FragmentCalorie extends Fragment implements AdapterView.OnItemSelectedListener{

    public TextView kalori;
    RecyclerView rv;
    String textspinner;
    private HashMap<String,Object> mData;
    Button kayit;
    List<Food> yiyecekListt;
    DatabaseReference databaseReference;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calorie, container, false);

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


        Intent ii=getActivity().getIntent();
        String aa= ii.getStringExtra("email");

        kayit=view.findViewById(R.id.buttonkaydet);
        rv=view.findViewById(R.id.myrecycler);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kalorideneme-default-rtdb.firebaseio.com/");

        kalori=view.findViewById(R.id.kalori);

        yiyecekListt= new ArrayList<>();

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textspinner.equals("Öğün Seç")){
                    Toast.makeText(view.getContext(), "Öğün seçiniz.", Toast.LENGTH_SHORT).show();
                }else
                {

                    String z=currentDate+" - "+textspinner;

                    databaseReference.child("Users").child(aa).child("Öğünler").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            mData=new HashMap<>();
                            mData.put("Tarih",currentDate);
                            mData.put("Kalori",kalori.getText().toString());
                            mData.put("Öğün",textspinner);


                            databaseReference.child("Users").child(aa).child("Öğünler").child(z).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(view.getContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        Query query=databaseReference.child("Foods");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String url=dataSnapshot.child("url").getValue().toString();
                    String ad=dataSnapshot.child("ad").getValue().toString();
                    String kalori=dataSnapshot.child("kalori").getValue().toString();


                    yiyecekListt.add(new Food(ad,kalori,url));

                }

                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

                rv.setAdapter(new MyAdapterFood(view.getContext(), yiyecekListt, new ClickDelegate(){

                    public void onClick(String text){

                        String ilkkalori=kalori.getText().toString();
                        int gelenkalori=Integer.parseInt(text);
                        int ilkkalorii=Integer.parseInt(ilkkalori);
                        int sonhal=gelenkalori+ilkkalorii;

                        String sonhals=String.valueOf(sonhal);
                        kalori.setText(sonhals);
                    }

                    @Override
                    public void onClickDelete(String text) {
                        String ilkkalori=kalori.getText().toString();
                        int gelenkalori=Integer.parseInt(text);
                        int ilkkalorii=Integer.parseInt(ilkkalori);
                        int sonhal=ilkkalorii-gelenkalori;

                        String sonhals=String.valueOf(sonhal);
                        kalori.setText(sonhals);
                    }
                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Spinner spinner=view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);





        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textspinner=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}