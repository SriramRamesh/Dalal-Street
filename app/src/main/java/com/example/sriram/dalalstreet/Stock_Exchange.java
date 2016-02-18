package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Stock_Exchange extends Fragment {
         static Context context;
        ArrayList<String> Stocks=new ArrayList<>();
        ArrayList<String> Current_Price=new ArrayList<>();
        int flag;


        public static Stock_Exchange newInstance(Context context_args, ArrayList<String> Stocks_args,
                                                 ArrayList<String> current_Price,int flag_args){
            Stock_Exchange stock_exchange=new Stock_Exchange();
            context=context_args;
            Bundle args=new Bundle();
            args.putStringArrayList("Stocks", Stocks_args);
            args.putStringArrayList("Current", current_Price);
            args.putInt("Flag",flag_args);
            stock_exchange.setArguments(args);
            return stock_exchange;

        }


        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Stocks=getArguments().getStringArrayList("Stocks");
            Current_Price=getArguments().getStringArrayList("Current");
            flag=getArguments().getInt("Flag");

        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_stock_exchange, container, false);
            ListView listView=(ListView)view.findViewById(R.id.stock_exchange_list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(flag==1){
                        Intent in=new Intent(context,Stock_Exchange_activity.class);
                        in.putExtra("Stock Name",Stocks.get(position));
                        startActivity(in);
                    }
                    else if(flag==2){
                        Intent in=new Intent(context,Buy_and_Sell_activity.class);
                        in.putExtra("Stock Name",Stocks.get(position));
                        startActivity(in);
                    }

                }
            });
            Stock_Exchange_list_adapter arrayAdapter=new Stock_Exchange_list_adapter(context,Stocks,Current_Price);
            listView.setAdapter(arrayAdapter);
            return view;
        }




}
