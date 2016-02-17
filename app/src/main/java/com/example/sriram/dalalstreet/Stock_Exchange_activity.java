package com.example.sriram.dalalstreet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class Stock_Exchange_activity extends AppCompatActivity {
    String Stock_name=new String();
    Context context;
    JSONObject stock_info=new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_exchange);

        context=getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Api api=new Api(context);
        api.api_Stocks();
        stock_info=api.get_Stock_Info(Stock_name);


        TextView textView_stock_name=(TextView)findViewById(R.id.stock_name_stock_exchange);
        TextView textView_current_price=(TextView)findViewById(R.id.current_price_stock_exchange);
        TextView textView_day_high=(TextView)findViewById(R.id.day_high_stock_exchange);
        TextView textView_day_low=(TextView)findViewById(R.id.day_low_stock_exchange);
        TextView textView_all_time_high=(TextView)findViewById(R.id.alltime_high_stock_exchange);
        TextView textView_all_time_low=(TextView)findViewById(R.id.alltime_low_stock_exchange);
        TextView textView_stocks_in_exchange=(TextView)findViewById(R.id.stock_in_exchange_stock_exchange);
        TextView textView_stocks_in_market=(TextView)findViewById(R.id.stocks_in_market_stock_exchange);

        try{
            textView_stock_name.setText(stock_info.getString("stockname"));
            textView_current_price.setText(stock_info.getString("currentprice"));
            textView_day_high.setText(stock_info.getString("dayhigh"));
            textView_day_low.setText(stock_info.getString("daylow"));
            textView_all_time_high.setText(stock_info.getString("alltimehigh"));
            textView_all_time_low.setText(stock_info.getString("alltimelow"));
            textView_stocks_in_exchange.setText(stock_info.getString("stocksinexchange"));
            textView_stocks_in_market.setText(stock_info.getString("stocksinmarket"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void Trade_On_Click(View v){
        Intent intent=new Intent(Stock_Exchange_activity.this,Trade.class);
        intent.putExtra("Stock Name",Stock_name);
        startActivity(intent);
    }


}
