package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Transaction extends Fragment {
    static Context context;
    ArrayList<String> Names=new ArrayList<>();
    ArrayList<String> Current_Price=new ArrayList<>();
    ArrayList<String> Stocks_worth=new ArrayList<>();
    ArrayList<Integer> Stocks_bought=new ArrayList<>();


    public static Transaction newInstance(Context context_args, ArrayList<String> Names_args,
                                          ArrayList<String> current_Price,ArrayList<String> stocks_worth,
                                          ArrayList<Integer> stocks_bought){
        Transaction transaction=new Transaction();
        context=context_args;
        Bundle args=new Bundle();
        args.putStringArrayList("Names", Names_args);
        args.putStringArrayList("Current", current_Price);
        args.putStringArrayList("Stocks Worth",stocks_worth);
        args.putIntegerArrayList("Stocks bought",stocks_bought);
        transaction.setArguments(args);
        return transaction;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Names= getArguments().getStringArrayList("Names");
        Current_Price=getArguments().getStringArrayList("Current");
        Stocks_worth=getArguments().getStringArrayList("Stocks worth");
        Stocks_bought=getArguments().getIntegerArrayList("Stocks bought");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ListView listView=(ListView)view.findViewById(R.id.transaction_list);
        Transaction_list_adapter list_adapter=new Transaction_list_adapter(context,Names,Current_Price,Stocks_bought,Stocks_worth);

        listView.setAdapter(list_adapter);

        return view;
    }




}
