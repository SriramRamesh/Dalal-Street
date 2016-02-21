package Pragyan.Delta.Dalal.Street;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class Buy_and_Sell_activity extends AppCompatActivity {

    String Stock_name=new String();
    Context context;
    JSONObject stock_info=new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);

        context=getApplicationContext();


        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

        try{
            stock_info=new JSONObject(getIntent().getStringExtra("Stock JSON"));


        TextView textView_stock_name=(TextView)findViewById(R.id.stock_name_buy_and_sell);
        TextView textView_current_price=(TextView)findViewById(R.id.current_price_buy_and_sell);
        TextView textView_day_high=(TextView)findViewById(R.id.day_high_buy_and_sell);
        TextView textView_day_low=(TextView)findViewById(R.id.day_low_buy_and_sell);
        TextView textView_all_time_high=(TextView)findViewById(R.id.alltime_high_buy_and_sell);
        TextView textView_all_time_low=(TextView)findViewById(R.id.alltime_low_buy_and_sell);
        TextView textView_stocks_in_exchange=(TextView)findViewById(R.id.stock_in_exchange_buy_and_sell);
        TextView textView_stocks_in_market=(TextView)findViewById(R.id.stocks_in_market_buy_and_sell);

            Stock_name=stock_info.getString("stockname");

            textView_stock_name.setText("Stock Name: "+stock_info.getString("stockname"));
            if(stock_info.getInt("updown")==1) {
                textView_current_price.setText("Current Price: " + stock_info.getString("currentprice")+"[img src=green/]");
            }
            else if(stock_info.getInt("updown")==0) {
                textView_current_price.setText("Current Price: " + stock_info.getString("currentprice")+"[img src=red/]");
            }
            textView_day_high.setText("Day High: "+stock_info.getString("dayhigh"));
            textView_day_low.setText("Day Low"+stock_info.getString("daylow"));
            textView_all_time_high.setText("All time high: "+stock_info.getString("alltimehigh"));
            textView_all_time_low.setText("All time low: "+stock_info.getString("alltimelow"));
            textView_stocks_in_exchange.setText("Stocks in Exchange: "+stock_info.getString("stocksinexchange"));
            textView_stocks_in_market.setText("Stocks in Market : "+stock_info.getString("stocksinmarket"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void Buy_On_Click(View v){
        Intent intent=new Intent(Buy_and_Sell_activity.this,Buy.class);
        intent.putExtra("Stock Name",Stock_name);
        startActivity(intent);
    }
    public void Sell_On_Click(View v){
        Intent intent=new Intent(Buy_and_Sell_activity.this,Sell.class);
        intent.putExtra("Stock Name",Stock_name);
        startActivity(intent);
    }

}
