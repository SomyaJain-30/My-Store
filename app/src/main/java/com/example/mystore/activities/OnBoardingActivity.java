package com.example.mystore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mystore.R;
import com.example.mystore.adapters.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dotslayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button getStartbtn;
    Button nxtbtn;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        viewPager = findViewById(R.id.slider);
        dotslayout = findViewById(R.id.dots);
        getStartbtn = findViewById(R.id.lets_get_started);
        nxtbtn = findViewById(R.id.next);
        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);
        //call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        getStartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnBoardingActivity.this, RegisterActivity.class);
                startActivity(i);
//                finish();
            }
        });

    }

    public void addDots(int position){
        dots = new TextView[3];
        dotslayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotslayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if(position == 0 || position == 1){
                getStartbtn.setVisibility(View.INVISIBLE);
                nxtbtn.setVisibility(View.VISIBLE);
            }else{
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                getStartbtn.setAnimation(animation);
                getStartbtn.setVisibility(View.VISIBLE);
                nxtbtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}