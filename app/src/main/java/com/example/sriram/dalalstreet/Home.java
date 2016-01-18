package com.example.sriram.dalalstreet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import javax.xml.transform.Transformer;

public class Home extends Activity {
    private SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sliderShow = (SliderLayout) findViewById(R.id.slider);

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

    }

    public void Play_Dalal(View v){
        Intent intent=new Intent(Home.this,play_Dalal.class);
        startActivity(intent);
        return;
    }
    public void Show_Contact(View v){
        Toast.makeText(getApplicationContext(), "contact :", Toast.LENGTH_SHORT);
        return;
    }
    public void Show_Manual(View v){
        Toast.makeText(getApplicationContext(), "Manual:", Toast.LENGTH_SHORT);
        return;
    }
    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }
}