package com.example.mystore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.example.mystore.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private Context context;
    List<AllProductModel> productList;

    public NewProductsAdapter(Context context, List<AllProductModel> productList){
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public NewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(productList.get(position).getImg_url()).into(holder.product_img);
        holder.product_name.setText(productList.get(position).getName());
        holder.product_price.setText(String.valueOf(productList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("detailed", productList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(4, productList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_img;
        TextView product_name;
        TextView product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.new_product_img);
            product_name = itemView.findViewById(R.id.new_product_name);
            product_price = itemView.findViewById(R.id.new_product_price);
        }
    }
}
