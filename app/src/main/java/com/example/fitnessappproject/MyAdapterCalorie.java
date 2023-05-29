package com.example.fitnessappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterCalorie extends RecyclerView.Adapter<MyAdapterCalorie.MyViewHolder>{
    Context context;
    List<Calorie> kaloriler;

    public MyAdapterCalorie(Context context, List<Calorie> kaloriler) {
        this.context = context;
        this.kaloriler = kaloriler;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.kaloriinfo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Calorie cal=kaloriler.get(position);

        String tarih=cal.getZaman();
        String kalori=cal.getKalori();
        String vakit=cal.getVakit();

        System.out.println("kalo "+kalori);


        holder.texttarih.setText(tarih);
        holder.textkalori.setText(cal.getKalori());
        holder.textvakit.setText(vakit);



    }

    @Override
    public int getItemCount() {
        return kaloriler.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView texttarih,textkalori,textvakit;

        public MyViewHolder(@NonNull View itemView ) {
            super(itemView);

            texttarih=itemView.findViewById(R.id.textViewtarih);
            textkalori = itemView.findViewById(R.id.textViewcalori);
            textvakit = itemView.findViewById(R.id.textViewvakit);

        }
    }


}
