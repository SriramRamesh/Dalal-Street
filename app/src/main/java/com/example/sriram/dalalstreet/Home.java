package com.example.sriram.dalalstreet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends Activity {

    private SliderLayout sliderShow;
    private Context context;
    String username;
    String password;
    TextViewWithImages textViewWithImages;
    SharedPreferences sharedPreferences;
    HorizontalScrollView s;

    static String ans="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences =getSharedPreferences("User Details", MODE_PRIVATE);
        username=sharedPreferences.getString("username", null);
        password=sharedPreferences.getString("password", null);
        context=getApplicationContext();
        textViewWithImages=(TextViewWithImages)findViewById(R.id.home_stock_new);
        textViewWithImages.setSelected(true);
        api_Stocks(context, username, password);
        s=(HorizontalScrollView)findViewById(R.id.horizontaltextview);

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
        ViewTreeObserver vto 		=	s.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                s.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScrollMaxAmount();
                startAutoScrolling();
            }
        });

    }
    public void getScrollMaxAmount(){
        int actualWidth = (s.getMeasuredWidth()-512);
        scrollMax   = actualWidth;
    }



    private static SpannableStringBuilder builder;

    public void api_Stocks(Context context_args,final String username_args,final String password_args){
        final Context context=context_args;
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/stocks";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray stocks_info=new JSONArray();
                try {

                    stocks_info= response.getJSONArray("stocks_list");
                    Log.d("stock", "api response" + stocks_info);
                    if(stocks_info!=null) {

                        builder= new SpannableStringBuilder();

                        for (int i = 0; i < stocks_info.length(); i++) {

                            try {

                                JSONObject x =stocks_info.getJSONObject(i);
                                if(x != null) {
                                    if(Build.VERSION.SDK_INT<21) {
                                        builder.append(x.getString("stockname")).append(" ").append(x.getString("currentprice"));
                                        int updown = x.getInt("updown");
                                        if (updown == 1) {
                                            builder.setSpan(new ImageSpan(context, R.drawable.green), builder.length() - 1,
                                                    builder.length(), 0);
                                        } else if (updown == 0) {
                                            builder.setSpan(new ImageSpan(context, R.drawable.red), builder.length() - 1,
                                                    builder.length(), 0);

                                        }
                                    }
                                    else{
                                        builder.append(x.getString("stockname")).append(" ").append(x.getString("currentprice"));
                                        int updown = x.getInt("updown");
                                        if (updown == 1) {
                                            builder.append(" ",new ImageSpan(context, R.drawable.green),0);
                                        } else if (updown == 0) {
                                            builder.append(" ",new ImageSpan(context, R.drawable.red), 0);

                                        }

                                    }




                                    /*ans+=x.getString("stockname")+" "+x.getString("currentprice");
                                    int updown= x.getInt("updown");
                                    if(updown==1){
                                        ans+="[img src=green/]";
                                    }
                                    else if(updown==0){
                                        ans+="[img src=red/]";
                                    }*/

                                }

                            } catch (Exception e) {
                                Log.d("stock:","respone to json Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        Log.d("text view",""+builder);
                        textViewWithImages.setText(builder);
                        s.fling(10);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String data="Error";
                if(error instanceof NoConnectionError) {
                    data= "No internet Access, Check your internet connection.";
                }
                error.printStackTrace();
                Log.d("volley error", "" + error);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // the GET headers:
                headers.put("X-DALAL-API-EMAIL", username_args);
                headers.put("X-DALAL-API-PASSWORD", password_args);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);

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
        //popupView.setAlpha((float) 0.9);
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

    private Timer scrollTimer		=	null;
    private TimerTask scrollerSchedule;
    public void startAutoScrolling(){
        if (scrollTimer == null) {
            scrollTimer					=	new Timer();
            final Runnable Timer_Tick 	= 	new Runnable() {
                public void run() {
                    moveScrollView();
                }
            };

            if(scrollerSchedule != null){
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask(){
                @Override
                public void run(){
                    runOnUiThread(Timer_Tick);
                }
            };

            scrollTimer.schedule(scrollerSchedule, 30, 30);
        }
    }
    private int scrollMax;
    private int scrollPos =	0;


    public void moveScrollView(){
        scrollPos							= 	(int) (s.getScrollX() + 1.0);
        if(scrollPos >= scrollMax){
            scrollPos						=	0;
        }
        s.scrollTo(scrollPos, 0);

    }


}