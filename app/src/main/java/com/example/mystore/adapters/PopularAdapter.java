package com.example.mystore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.activities.DetailedActivity;
import com.example.mystore.models.AllProductModel;
import com.example.mystore.models.PopularModel;

import org.w3c.dom.Text;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

        Context context;
        List<AllProductModel> popularList;

        public PopularAdapter(Context context, List<AllProductModel> popularList){
                this.context = context;
                this.popularList = popularList;
        }
        @NonNull
        @Override
        public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
                Glide.with(context).load(popularList.get(position).getImg_url()).into(holder.popular_img);
                holder.popular_name.setText(popularList.get(position).getName());
                holder.popular_price.setText(String.valueOf(popularList.get(position).getPrice()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent i = new Intent(context, DetailedActivity.class);
                                i.putExtra("detailed", popularList.get(position));
                                context.startActivity(i);
                        }
                });

        }

        @Override
        public int getItemCount() {
                return Math.min(popularList.size(),4);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

                ImageView popular_img;
                TextView popular_name;
                TextView popular_price;
                public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        popular_img = itemView.findViewById(R.id.popular_img);
                        popular_name = itemView.findViewById(R.id.popular_name);
                        popular_price = itemView.findViewById(R.id.popular_price);

                }
        }
}
