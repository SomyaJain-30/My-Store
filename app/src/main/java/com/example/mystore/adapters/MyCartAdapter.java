package com.example.mystore.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.activities.CartActivity;
import com.example.mystore.activities.DetailedActivity;
import com.example.mystore.models.MyCartModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context context;
    private static final int  getQuantityInfo = 798;
    private Activity activity;
    private List<MyCartModel> myCartList;
    int total = 0;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    public MyCartAdapter(Context context, Activity activity, List<MyCartModel> myCartList){
        this.context = context;
        this.myCartList = myCartList;
        this.activity = activity;
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_items, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(myCartList.get(position).getImg_url()).into(holder.cart_img);
        holder.cart_name.setText(myCartList.get(position).getProductName());
        holder.cart_price.setText(myCartList.get(position).getProductPrice());
        holder.cart_quantity.setText(myCartList.get(position).getTotalQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("detailed", myCartList.get(position));
                activity.startActivityForResult(i,getQuantityInfo);

            }
        });
        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = myCartList.get(position).getProductId();
                System.out.println(productId);
                firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").whereEqualTo("productId", productId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() != 0){
                            System.out.println(productId);
                            queryDocumentSnapshots.getDocuments().get(0).getReference().delete();
                            myCartList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                            calSubTotal();
                        }
                    }

                });
            }
        });
    }

    public void calSubTotal(){
        int subtotal = 0;
        for(int i = 0; i< myCartList.size(); i++){
            subtotal += Integer.parseInt(myCartList.get(i).getTotalPrice());
        }
        CartActivity.subtotal.setText(String.valueOf(subtotal));
    }
    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cart_img;
        ImageView delete_img;
        TextView cart_name,cart_price,cart_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_img = itemView.findViewById(R.id.my_cart_img);
            cart_name = itemView.findViewById(R.id.my_cart_name);
            cart_price = itemView.findViewById(R.id.my_cart_price);
            cart_quantity = itemView.findViewById(R.id.my_cart_quantity);
            delete_img = itemView.findViewById(R.id.delete);

        }
    }
}
