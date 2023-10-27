package com.example.mystore.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.mystore.R;
import com.example.mystore.activities.ShowAllActivity;
import com.example.mystore.adapters.CategoryAdapter;
import com.example.mystore.adapters.NewProductsAdapter;
import com.example.mystore.adapters.PopularAdapter;
import com.example.mystore.models.AllProductModel;
import com.example.mystore.models.CategoryModel;
import com.example.mystore.models.NewProductsModel;
import com.example.mystore.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView newProductSeeAll, popularSeeAll;
    RecyclerView catRecycleView, newProductRecycleView,popularRecycleView;

    //category
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    NewProductsAdapter newProductsAdapter;
    PopularAdapter popularAdapter;
    List<AllProductModel> allProductModelList;
    List<AllProductModel> newProductsList;
    List<AllProductModel> popularList;

    FirebaseFirestore firestore;
    ProgressDialog progressBar;
    LinearLayout linearLayout;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        linearLayout = root.findViewById(R.id.home_layout);
        progressBar = new ProgressDialog(getActivity());
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        catRecycleView = root.findViewById(R.id.rec_category);
        newProductRecycleView = root.findViewById(R.id.new_product_rec);
        popularRecycleView = root.findViewById(R.id.popular_rec);
        newProductSeeAll = root.findViewById(R.id.newProducts_see_all);
        popularSeeAll = root.findViewById(R.id.popular_see_all);


        newProductSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShowAllActivity.class);
                i.putExtra("division", "new");
                startActivity(i);
            }
        });

        popularSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShowAllActivity.class);
                i.putExtra("division", "popular");
                startActivity(i);
            }
        });
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, "Discount on shoes items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount on perfumes", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        linearLayout.setVisibility(View.GONE);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setMessage("Please wait ...");
        progressBar.show();



        firestore = FirebaseFirestore.getInstance();
        //for category recycle view
        catRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecycleView.setAdapter(categoryAdapter);
        firestore.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot ds : task.getResult()){
                        CategoryModel categoryModel = ds.toObject(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        allProductModelList = new ArrayList<>();
        //for new product recycleview

        newProductRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsList);
        newProductRecycleView.setAdapter(newProductsAdapter);

        //for popular products
        popularRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getContext(),popularList);
        popularRecycleView.setAdapter(popularAdapter);



        firestore.collection("All Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot ds: task.getResult()){
                        AllProductModel allProductModel = ds.toObject(AllProductModel.class);
                        allProductModel.setProductId(ds.getId());
                        System.out.println("productId" + allProductModel.getProductId());
                        if(allProductModel.getDivision().equals("new")){
                            newProductsList.add(allProductModel);
                        }else if(allProductModel.getDivision().equals("popular")) {
                            popularList.add(allProductModel);
                        }
                    }
                    popularAdapter.notifyDataSetChanged();
                    newProductsAdapter.notifyDataSetChanged();
                    progressBar.dismiss();
                }else{
                    Toast.makeText(getActivity(), ""+ task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}