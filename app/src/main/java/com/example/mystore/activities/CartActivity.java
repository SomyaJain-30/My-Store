package com.example.mystore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.example.mystore.R;
import com.example.mystore.adapters.MyCartAdapter;
import com.example.mystore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRecycleView;
    List<MyCartModel> myCartModelList;
    MyCartAdapter myCartAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    public static TextView subtotal;
    int total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subtotal = findViewById(R.id.subtotal);
        cartRecycleView = findViewById(R.id.cart_rec);
        cartRecycleView.setLayoutManager(new LinearLayoutManager(this));
        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(this, CartActivity.this,myCartModelList);
        cartRecycleView.setAdapter(myCartAdapter);

        firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                            total += Integer.parseInt(myCartModel.getTotalPrice());
                            firestore.collection("All Products").document(myCartModel.getProductId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    myCartModel.setImg_url(documentSnapshot.get("img_url").toString());
                                    myCartModel.setProductName(documentSnapshot.get("name").toString());
                                    myCartModel.setProductPrice(documentSnapshot.get("price").toString());
                                    myCartModelList.add(myCartModel);
                                    myCartAdapter.notifyDataSetChanged();
                                }
                            });

                        }

                        subtotal.setText(String.valueOf(total));
                    }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            if(requestCode == 798){
                String quantity = data.getStringExtra("qty");
                String productId = data.getStringExtra("productId");
                total = 0;
                for(int i = 0;i< myCartModelList.size(); i++){
                    if(myCartModelList.get(i).getProductId().equals(productId)){
                        myCartModelList.get(i).setTotalQuantity(quantity);
                        myCartModelList.get(i).computeTotalPrice();
                    }
                    total += Integer.parseInt(myCartModelList.get(i).getTotalPrice());
                }
                myCartAdapter.notifyDataSetChanged();
                subtotal.setText(String.valueOf(total));

            }
        }
    }

}