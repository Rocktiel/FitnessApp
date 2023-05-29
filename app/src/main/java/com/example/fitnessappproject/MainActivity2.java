package com.example.fitnessappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    EditText email,ad,soyad,sifre,age,weight,height;
    Button kayit;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private HashMap<String,Object> mData;
    private FirebaseAuth mAuth;



    String semail,sad,ssyoad,ssifre,sage,sweight,sheight;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        email=findViewById(R.id.emailkayit);
        ad=findViewById(R.id.name);
        soyad=findViewById(R.id.surname);
        sifre=findViewById(R.id.password);
        age=findViewById(R.id.age);
        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kalorideneme-default-rtdb.firebaseio.com/");


        mAuth= FirebaseAuth.getInstance();

        kayit=findViewById(R.id.signup);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semail=email.getText().toString();
                sad=ad.getText().toString();
                ssyoad=soyad.getText().toString();
                ssifre=sifre.getText().toString();
                sage=age.getText().toString();
                sweight=weight.getText().toString();
                sheight=height.getText().toString();

                if(!TextUtils.isEmpty(semail) && !TextUtils.isEmpty(sad) && !TextUtils.isEmpty(ssyoad) && !TextUtils.isEmpty(ssifre) && !TextUtils.isEmpty(sage) && !TextUtils.isEmpty(sweight) && !TextUtils.isEmpty(sheight) )
                {

                    mAuth.createUserWithEmailAndPassword(semail,ssifre).addOnCompleteListener(MainActivity2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(semail)){
                                        Toast.makeText(MainActivity2.this, "Bu kullanıcı adı daha önceden alınmış.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity2.this, "Kayıt başarılı.", Toast.LENGTH_SHORT).show();

                                        Intent intent;
                                        intent=new Intent(MainActivity2.this,MainActivity.class);


                                        firebaseUser=mAuth.getCurrentUser();

                                        mData=new HashMap<>();
                                        mData.put("Username",semail);
                                        mData.put("Şifre",ssifre);
                                        mData.put("Ad",sad);
                                        mData.put("Soyad",ssyoad);
                                        mData.put("Yaş",sage);
                                        mData.put("Ağırlık",sweight);
                                        mData.put("Boy",sheight);

                                        databaseReference.child("Users").child(semail).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

                }

            }
        });



    }
}