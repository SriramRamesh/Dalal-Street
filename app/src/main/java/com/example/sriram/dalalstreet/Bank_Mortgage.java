package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Bank_Mortgage extends Fragment {

    static Context context;
    ArrayList<String> Stocks=new ArrayList<>();
    ArrayList<String> Current_Price=new ArrayList<>();
    ArrayList<Integer> Stocks_bought=new ArrayList<>();

    public static Bank_Mortgage newInstance(Context context_args, ArrayList<String> Stocks_args,
                                             ArrayList<String> current_Price,ArrayList<Integer> stocks_bought){
        Bank_Mortgage stock_exchange=new Bank_Mortgage();
        context=context_args;
        Bundle args=new Bundle();
        args.putStringArrayList("Stocks", Stocks_args);
        args.putStringArrayList("Current", current_Price);
        args.putIntegerArrayList("Stocks bought", stocks_bought);
        stock_exchange.setArguments(args);
        return stock_exchange;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stocks=getArguments().getStringArrayList("Stocks");
        Current_Price=getArguments().getStringArrayList("Current");
        Stocks_bought=getArguments().getIntegerArrayList("Stocks bought");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_mortgage, container, false);
        ListView listView=(ListView)view.findViewById(R.id.bank_mortgage_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent in = new Intent(context, Bank_Mortgage_activity.class);
                    in.putExtra("Stock Name", Stocks.get(position));
                    startActivity(in);
            }
        });
        Bank_Mortgage_list_adapter arrayAdapter=new Bank_Mortgage_list_adapter(context,Stocks,Current_Price,Stocks_bought);
        listView.setAdapter(arrayAdapter);
        return view;
    }

}
