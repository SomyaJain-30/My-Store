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
import com.example.mystore.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<AllProductModel> showAllList;

    public ShowAllAdapter(Context context, List<AllProductModel> showAllList){
        this.context = context;
        this.showAllList = showAllList;
    }
    @NonNull
    @Override
    public ShowAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(showAllList.get(position).getImg_url()).into(holder.item_img);
        holder.item_name.setText(showAllList.get(position).getName());
        holder.item_price.setText("₹" + showAllList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("detailed", showAllList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showAllList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item_price;
        TextView item_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_price = itemView.findViewById(R.id.item_cost);
            item_name = itemView.findViewById(R.id.item_name);
        }
    }
}
