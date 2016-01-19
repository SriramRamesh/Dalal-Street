package com.example.sriram.dalalstreet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class Home extends Activity {

    private SliderLayout sliderShow;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TODO: get stock news as a String and put it in home_stock_news


        context=getApplicationContext();
        sliderShow = (SliderLayout) findViewById(R.id.slider);

        //TODO: get images from the website and put it in slider
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("LG reports quaterly loss in revenue")
                .image(R.drawable.lg2);

        sliderShow.addSlider(textSliderView);

        textSliderView = new TextSliderView(this);
        textSliderView
                .description("Sony acquires assets in China")
                .image(R.drawable.sony4);

        sliderShow.addSlider(textSliderView);
        textSliderView = new TextSliderView(this);
        textSliderView
                .description("Infy set to invest 120 million on latest tech")
                .image(R.drawable.infy1);

        sliderShow.addSlider(textSliderView);
        textSliderView = new TextSliderView(this);
        textSliderView
                .description("Github employees calls for a strike")
                .image(R.drawable.github2);

        sliderShow.addSlider(textSliderView);
        textSliderView = new TextSliderView(this);
        textSliderView
                .description("Yahoo reports quaterly loss in revenue")
                .image(R.drawable.yahoo2);

        sliderShow.addSlider(textSliderView);
        textSliderView = new TextSliderView(this);
        textSliderView
                .description("Apple set to invest 40 million on latest tech")
                .image(R.drawable.apple1);

        sliderShow.addSlider(textSliderView);
        textSliderView = new TextSliderView(this);
        textSliderView
                .description("HDFC set to invest 75 million on latest tech")
                .image(R.drawable.hdfc1);

        sliderShow.addSlider(textSliderView);
        PagerIndicator pagerIndicator=(PagerIndicator)findViewById(R.id.custom_indicator);
        sliderShow.setCustomIndicator(pagerIndicator);

    }

    public void Play_Dalal(View v){
        Intent intent=new Intent(Home.this,play_Dalal.class);
        startActivity(intent);
        return;
    }
    public void Show_Contact(View v){

        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.contact_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.pop_Up_ok);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupView.setAlpha((float) 0.8);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        return;
    }
    public void Show_Manual(View v){
        Intent intent=new Intent(Home.this,Manual.class);
        startActivity(intent);
        return;
    }
    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }


}