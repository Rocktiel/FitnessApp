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

interface ClickDelegate {
    void onClick(String text);
    void onClickDelete(String text);
}

public class MyAdapterFood extends RecyclerView.Adapter<MyAdapterFood.MyViewHolder> {

    Context context;
    List<Food> yiyecekler;

    ClickDelegate delegate;

    public MyAdapterFood(Context context, List<Food> yiyecekler, ClickDelegate delegate) {

        this.context = context;
        this.yiyecekler = yiyecekler;
        this.delegate = delegate;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.foodrowitem,parent,false),delegate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Food ymk=yiyecekler.get(position);

        String name=ymk.getAd();
        String kalori=ymk.getKalori();


        Picasso.get().load(ymk.getImage()).into(holder.imgg);


        holder.text.setText(name);
        holder.textkalori.setText(kalori);


    }

    @Override
    public int getItemCount() {
        return yiyecekler.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text,textkalori,textadet;
        public Button ekle;

        public ImageView imgg;




        public MyViewHolder(@NonNull View itemView, ClickDelegate delegate ) {
            super(itemView);



            textadet=itemView.findViewById(R.id.adet);
            text = itemView.findViewById(R.id.textViewad);
            textkalori = itemView.findViewById(R.id.textViewKalori);
            ekle=itemView.findViewById(R.id.butonekle);
            imgg=itemView.findViewById(R.id.imageViewfood);


            itemView.findViewById(R.id.butoncikar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ilkadet=textadet.getText().toString();
                    int a=Integer.parseInt(ilkadet);
                    if(a==0){
                        Toast.makeText(itemView.getContext(), "0'dan küçük olamaz.", Toast.LENGTH_SHORT).show();
                    }else{
                        a=a-1;
                        String sonadet=String.valueOf(a);
                        textadet.setText(sonadet);
                        String kalori=textkalori.getText().toString();
                        delegate.onClickDelete(kalori);
                    }
                }
            });

            itemView.findViewById(R.id.butonekle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ilkadet=textadet.getText().toString();

                    int a=Integer.parseInt(ilkadet);
                    a=a+1;
                    String sonadet=String.valueOf(a);
                    textadet.setText(sonadet);

                    String kalori=textkalori.getText().toString();

                    delegate.onClick(kalori);
                }
            });





        }
    }

}