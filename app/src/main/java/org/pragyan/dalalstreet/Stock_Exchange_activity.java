package org.pragyan.dalalstreet;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class Stock_Exchange_activity extends AppCompatActivity {
    String Stock_name=new String();
    String Stocks_in_Exchange=new String();
    Context context;
    JSONObject stock_info=new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_exchange);

        context=getApplicationContext();
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
    /*    Api api=new Api(context);
        api.api_Stocks();*/
        try{
            stock_info=new JSONObject(getIntent().getStringExtra("Stock JSON"));
            Stocks_in_Exchange=stock_info.getString("stocksinexchange");
            Stock_name=stock_info.getString("stockname");

            TextView textView_stock_name=(TextView)findViewById(R.id.stock_name_stock_exchange);
            TextView textView_current_price=(TextView)findViewById(R.id.current_price_stock_exchange);
            TextView textView_day_high=(TextView)findViewById(R.id.day_high_stock_exchange);
            TextView textView_day_low=(TextView)findViewById(R.id.day_low_stock_exchange);
            TextView textView_all_time_high=(TextView)findViewById(R.id.alltime_high_stock_exchange);
            TextView textView_all_time_low=(TextView)findViewById(R.id.alltime_low_stock_exchange);
            TextView textView_stocks_in_exchange=(TextView)findViewById(R.id.stock_in_exchange_stock_exchange);
            TextView textView_stocks_in_market=(TextView)findViewById(R.id.stocks_in_market_stock_exchange);


            textView_stock_name.setText("Stock Name: "+stock_info.getString("stockname"));
            SpannableStringBuilder builder=new SpannableStringBuilder();
            if(stock_info.getInt("updown")==1) {
                if(Build.VERSION.SDK_INT<21) {
                    builder.append("Current Price").append(" :").append(stock_info.getString("currentprice"));
                    builder.setSpan(new ImageSpan(context, R.drawable.green, ImageSpan.ALIGN_BOTTOM + 1), builder.length() - 1,
                            builder.length(), 0);
                }
                else {
                    builder.append("Current Price").append(" :").append(stock_info.getString("currentprice"));
                    builder.append(" ", new ImageSpan(context, R.drawable.green, ImageSpan.ALIGN_BOTTOM + 1), 0);

                }

            }
            else if(stock_info.getInt("updown")==0) {
                if(Build.VERSION.SDK_INT<21) {
                    builder.append("Current Price").append(" :").append(stock_info.getString("currentprice"));
                    builder.setSpan(new ImageSpan(context, R.drawable.red, ImageSpan.ALIGN_BOTTOM + 1), builder.length() - 1,
                            builder.length(), 0);
                }
                else {
                    builder.append("Current Price").append(" :").append(stock_info.getString("currentprice"));
                    builder.append(" ", new ImageSpan(context, R.drawable.red, ImageSpan.ALIGN_BOTTOM + 1), 0);

                }
            }
            textView_current_price.setText(builder);
            textView_day_high.setText("Day High: "+stock_info.getString("dayhigh"));
            textView_day_low.setText("Day Low"+stock_info.getString("daylow"));
            textView_all_time_high.setText("All time high: "+stock_info.getString("alltimehigh"));
            textView_all_time_low.setText("All time low: "+stock_info.getString("alltimelow"));
            textView_stocks_in_exchange.setText("Stocks in Exchange: "+stock_info.getString("stocksinexchange"));
            textView_stocks_in_market.setText("Stocks in Market: "+stock_info.getString("stocksinmarket"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void Trade_On_Click(View v){
        Intent intent=new Intent(Stock_Exchange_activity.this,Trade.class);
        intent.putExtra("Stock Name",Stock_name);
        intent.putExtra("Stock in Exchange",Stocks_in_Exchange);
        startActivity(intent);
    }


}
