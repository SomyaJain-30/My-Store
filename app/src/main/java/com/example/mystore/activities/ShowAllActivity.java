package com.example.mystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mystore.R;
import com.example.mystore.adapters.ShowAllAdapter;
import com.example.mystore.models.AllProductModel;
import com.example.mystore.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView showAllRecyclerView;
    ShowAllAdapter showAllAdapter;
    List<AllProductModel> showAllModelList;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();

        String division = getIntent().getStringExtra("division");
        String type = getIntent().getStringExtra("type");
        showAllRecyclerView = findViewById(R.id.show_all_rec);
        showAllRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this,showAllModelList);
        showAllRecyclerView.setAdapter(showAllAdapter);

        if(division == null || division.isEmpty()){
            firestore.collection("All Products").whereEqualTo("type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductModel allProductModel = doc.toObject(AllProductModel.class);
                            showAllModelList.add(allProductModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }else {
            firestore.collection("All Products").whereEqualTo("division", division).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc: task.getResult().getDocuments()){
                            AllProductModel allProductModel = doc.toObject(AllProductModel.class);
                            showAllModelList.add(allProductModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }
}