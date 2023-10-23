package com.example.mystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.mystore.models.NewProductsModel;
import com.example.mystore.models.PopularModel;
import com.example.mystore.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg, addItems,removeItems;
    TextView productName,rating,description,price;
    Button addToCart, buyNow;
    LinearLayout detailedDotsLayout;
    TextView []dots;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    //New product
    NewProductsModel newProductsModel = null;
    //popular products
    PopularModel popularModel = null;

    //show all product
    ShowAllModel showAllModel = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof PopularModel){
            popularModel = (PopularModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }
        detailedImg = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.detailed_plus);
        removeItems = findViewById(R.id.detailed_minus);
        productName = findViewById(R.id.detailed_product_name);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_description);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart_button_detailed);
        buyNow = findViewById(R.id.buy_now_button_detailed);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        detailedDotsLayout  = findViewById(R.id.detailed_dots);
        addDots(0);

        //New Products
        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            productName.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

        }
        //popular products;
        if(popularModel != null){
            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailedImg);
            productName.setText(popularModel.getName());
            rating.setText(popularModel.getRating());
            description.setText(popularModel.getDescription());
            price.setText(String.valueOf(popularModel.getPrice()));
        }

        // show all products;
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            productName.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart();

            }
        });
    }

    public void addToCart(){
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName", productName.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);

        firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Successfully Added to Cart!", Toast.LENGTH_SHORT).show();
                        finish();
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
            detailedDotsLayout.addView(dots[i]);
        }
    }
}