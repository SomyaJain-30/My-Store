package com.example.mystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.models.AllProductModel;
import com.example.mystore.models.MyCartModel;
import com.example.mystore.models.NewProductsModel;
import com.example.mystore.models.PopularModel;
import com.example.mystore.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg, addItems,removeItems;
    TextView productName,rating,description,price, quantity;
    Button addToCart, buyNow;
    LinearLayout detailedDotsLayout;
    TextView []dots;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    AllProductModel allProductModel = null;
    MyCartModel myCartModel = null;
    //New product
    NewProductsModel newProductsModel = null;
    //popular products
    PopularModel popularModel = null;

    //show all product
    ShowAllModel showAllModel = null;
    ProgressDialog progressBar;
    int totalQuantity = 1;
    int totalPrice = 0;
    String imgUrl = null;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        final Object obj = getIntent().getSerializableExtra("detailed");
        if(obj instanceof  AllProductModel){
            allProductModel = (AllProductModel) obj;
        }else if(obj instanceof MyCartModel){
            myCartModel = (MyCartModel) obj;
        }

//        if(obj instanceof NewProductsModel){
//            newProductsModel = (NewProductsModel) obj;
//        }else if(obj instanceof PopularModel){
//            popularModel = (PopularModel) obj;
//        }else if(obj instanceof ShowAllModel){
//            showAllModel = (ShowAllModel) obj;
//        }
        progressBar = new ProgressDialog(this);
        toolbar = findViewById(R.id.detailed_toolbar);
        detailedImg = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.detailed_plus);
        removeItems = findViewById(R.id.detailed_minus);
        productName = findViewById(R.id.detailed_product_name);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_description);
        price = findViewById(R.id.detailed_price);
        quantity = findViewById(R.id.detailed_quantity);
        addToCart = findViewById(R.id.add_to_cart_button_detailed);
        buyNow = findViewById(R.id.buy_now_button_detailed);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        detailedDotsLayout  = findViewById(R.id.detailed_dots);
        if(myCartModel != null){
            totalQuantity = Integer.parseInt(myCartModel.getTotalQuantity());
        }

        addDots(0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(myCartModel != null){
            String productId = myCartModel.getProductId();

            firestore.collection("All Products").document(productId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Glide.with(getApplicationContext()).load(documentSnapshot.get("img_url").toString()).into(detailedImg);
                    productName.setText(documentSnapshot.get("name").toString());
                    rating.setText(documentSnapshot.get("rating").toString());
                    description.setText(documentSnapshot.get("description").toString());
                    price.setText(String.valueOf(documentSnapshot.get("price")));
                    quantity.setText(myCartModel.getTotalQuantity());
                    totalPrice = Integer.parseInt(String.valueOf(documentSnapshot.get("price"))) * totalQuantity;
                }
            });

        }else if(allProductModel != null){
            Glide.with(getApplicationContext()).load(allProductModel.getImg_url()).into(detailedImg);
            productName.setText(allProductModel.getName());
            rating.setText(allProductModel.getRating());
            description.setText(allProductModel.getDescription());
            price.setText(String.valueOf(allProductModel.getPrice()));
            imgUrl = allProductModel.getImg_url();
            totalPrice = allProductModel.getPrice() * totalQuantity;
            System.out.println(allProductModel.getProductId());
        }

        if(myCartModel != null){
            addToCart.setText("Make Changes");
        }
        //New Products
//        if(newProductsModel != null){
//            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
//            productName.setText(newProductsModel.getName());
//            rating.setText(newProductsModel.getRating());
//            description.setText(newProductsModel.getDescription());
//            price.setText(String.valueOf(newProductsModel.getPrice()));
//
//            imgUrl = newProductsModel.getImg_url();
//            totalPrice = newProductsModel.getPrice() * totalQuantity;
//
//        }
//        //popular products;
//        if(popularModel != null){
//            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailedImg);
//            productName.setText(popularModel.getName());
//            rating.setText(popularModel.getRating());
//            description.setText(popularModel.getDescription());
//            price.setText(String.valueOf(popularModel.getPrice()));
//
//            imgUrl = popularModel.getImg_url();
//            totalPrice = popularModel.getPrice() * totalQuantity;
//        }
//
//        // show all products;
//        if(showAllModel != null){
//            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
//            productName.setText(showAllModel.getName());
//            rating.setText(showAllModel.getRating());
//            description.setText(showAllModel.getDescription());
//            price.setText(String.valueOf(showAllModel.getPrice()));
//
//            imgUrl = showAllModel.getImg_url();
//            totalPrice = showAllModel.getPrice() * totalQuantity;
//        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart();

            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity < 10){
                    totalQuantity++;
                }else{
                    Toast.makeText(DetailedActivity.this, "You have reached your limit", Toast.LENGTH_SHORT).show();
                }
                quantity.setText(String.valueOf(totalQuantity));
//                if(newProductsModel != null){
//                    totalPrice = newProductsModel.getPrice() * totalQuantity;
//                }
//                if(popularModel != null){
//                    totalPrice = popularModel.getPrice() * totalQuantity;
//                }
//                if(showAllModel != null){
//                    totalPrice = showAllModel.getPrice() * totalQuantity;
//                }
                if(allProductModel != null) totalPrice = allProductModel.getPrice()*totalQuantity;
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                }
                quantity.setText(String.valueOf(totalQuantity));
            }
        });
    }

    public void addToCart() {
//        String saveCurrentDate, saveCurrentTime;
//        Calendar calForDate = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
//        saveCurrentDate = currentDate.format(calForDate.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calForDate.getTime());
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setMessage("Please wait ...");
        progressBar.show();

        final HashMap<String, Object> cartMap = new HashMap<>();
//        cartMap.put("img_url", imgUrl);
//        cartMap.put("productName", productName.getText().toString());
//        cartMap.put("productPrice", price.getText().toString());
////        cartMap.put("currentTime", saveCurrentTime);
////        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", String.valueOf(totalQuantity));
        cartMap.put("totalPrice", String.valueOf(totalPrice));
        String productId = null;
        if(allProductModel != null){
            cartMap.put("productId", allProductModel.getProductId());
            productId = allProductModel.getProductId();
        }else if(myCartModel != null){
            cartMap.put("productId", myCartModel.getProductId());
            productId = myCartModel.getProductId();
        }


        firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").whereEqualTo("productId",productId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size() != 0){
                    String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                    String previousQuantity = queryDocumentSnapshots.getDocuments().get(0).get("totalQuantity").toString();
                    String previousPrice = queryDocumentSnapshots.getDocuments().get(0).get("totalPrice").toString();
                    Map<String, Object> mp = new HashMap<>();
                    if(allProductModel != null){
                        mp.put("totalQuantity", String.valueOf(Integer.parseInt(previousQuantity) + totalQuantity));
                        mp.put("totalPrice" , String.valueOf(Integer.parseInt(previousPrice) + totalQuantity*Integer.parseInt(price.getText().toString())));
                    }else if(myCartModel != null){
                        mp.put("totalQuantity", String.valueOf(totalQuantity));
                        mp.put("totalPrice" , String.valueOf(totalQuantity*Integer.parseInt(price.getText().toString())));
                    }

                    firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").document(id).update(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(DetailedActivity.this, "Successfully Added to Cart!", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                            if(myCartModel != null){
                                Intent i = new Intent();
                                i.putExtra("productId", myCartModel.getProductId());
                                i.putExtra("qty", quantity.getText().toString());
                                setResult(Activity.RESULT_OK,i);
                            }
                            finish();
                        }
                    });
                }else{
                    firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").document().set(cartMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DetailedActivity.this, "Successfully Added to Cart!", Toast.LENGTH_SHORT).show();
                                    progressBar.dismiss();
                                    finish();
                                }
                            });
                }
            }
        });


    }

    private void addDots(int position) {
        dots = new TextView[7];
        detailedDotsLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(com.denzcoskun.imageslider.R.color.grey_font));
            detailedDotsLayout.addView(dots[i]);
        }
    }
}