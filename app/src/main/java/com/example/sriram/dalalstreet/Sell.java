package com.example.sriram.dalalstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Sell extends AppCompatActivity {

    String Stock_Name=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");

        TextView textView=(TextView)findViewById(R.id.title_trade);
        textView.setText(Stock_Name);
    }

    public void Call_Sell_api(View v){
        //TODO: integrate sell api
    }
}
