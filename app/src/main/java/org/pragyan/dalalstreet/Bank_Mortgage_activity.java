package org.pragyan.dalalstreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Bank_Mortgage_activity extends AppCompatActivity {
    String Stock_Name=new String();
    int Stocks_bought;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_mortgage);


        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");
        Stocks_bought=in.getIntExtra("Stocks Bought", -1);

        TextView textView=(TextView)findViewById(R.id.title_bank_mortgage);
        textView.setText(Stock_Name);
    }
    public void Call_Mortgage_Activity(View v){
        Intent intent=new Intent(Bank_Mortgage_activity.this,Mortgage_activity.class);
        intent.putExtra("Stock Name",Stock_Name);
        intent.putExtra("Stocks Bought",Stocks_bought);
        startActivity(intent);
    }
    public void Call_Return_Activity(View v){
        Intent intent=new Intent(Bank_Mortgage_activity.this,Return_Bank_Stock_activity.class);
        intent.putExtra("Stock Name",Stock_Name);
        //intent.putExtra("Stocks Bought", Stocks_bought);
        startActivity(intent);
    }

}
