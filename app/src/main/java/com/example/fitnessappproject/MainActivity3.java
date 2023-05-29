package com.example.fitnessappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {

    DatabaseReference databaseReference;
    BottomNavigationView bottomNavi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent ii=getIntent();
        String a= ii.getStringExtra("email");


        bottomNavi=findViewById(R.id.bottomnavi);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentler,new FragmentCalorie()).commit();

        bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.calorie:
                    {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new FragmentCalorie()).addToBackStack(null).commit();
                        break;
                    }
                    case R.id.information:
                    {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new FragmentInfo()).addToBackStack(null).commit();
                        break;
                    }
                    case R.id.profile:
                    {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new FragmentProfile()).addToBackStack(null).commit();
                        break;
                    }

                }
                return true;
            }
        });






    }
}