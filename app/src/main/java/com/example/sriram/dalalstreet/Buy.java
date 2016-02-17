package com.example.sriram.dalalstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Buy extends AppCompatActivity {
    String Stock_Name=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");

        TextView textView=(TextView)findViewById(R.id.title_trade);
        textView.setText(Stock_Name);

    }
    public void Call_Buy_api(View v){
    //TODO: integrate buy api
    }
}
