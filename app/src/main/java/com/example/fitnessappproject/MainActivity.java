package com.example.fitnessappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText email,sifre;


    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Button giris,kayit;

    String smail,ssifree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.emailgiris);
        sifre=findViewById(R.id.sifregiris);

        giris=findViewById(R.id.buttongiris);
        kayit=findViewById(R.id.buttonkayit);



        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://kalorideneme-default-rtdb.firebaseio.com/");



        mAuth=FirebaseAuth.getInstance();

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smail=email.getText().toString();
                ssifree=sifre.getText().toString();

                if(!TextUtils.isEmpty(smail) && !TextUtils.isEmpty(ssifree))
                {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(smail)){

                                String ssoyad=String.valueOf(snapshot.child(smail).child("Şifre").getValue());

                                if(ssoyad.equals(ssifree)){
                                    Intent iff=new Intent(MainActivity.this,MainActivity3.class);
                                    iff.putExtra("email",smail);
                                    startActivity(iff);
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "yanlışş", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Boş bırakma", Toast.LENGTH_SHORT).show();
                }




            }
        });




    }
}