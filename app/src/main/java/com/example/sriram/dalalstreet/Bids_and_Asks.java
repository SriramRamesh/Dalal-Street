package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Bids_and_Asks extends Fragment {
    static Context context;
    ArrayList<String> Bids=new ArrayList<>();
    ArrayList<String> Asks=new ArrayList<>();


    public static Bids_and_Asks newInstance(Context context_args, ArrayList<String> Bids_args,
                                          ArrayList<String> Asks_args){
        Bids_and_Asks bids_and_asks=new Bids_and_Asks();
        context=context_args;
        Bundle args=new Bundle();
        args.putStringArrayList("Bids", Bids_args);
        args.putStringArrayList("Asks", Asks_args);
        bids_and_asks.setArguments(args);
        return bids_and_asks;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bids=getArguments().getStringArrayList("Bids");
        Asks=getArguments().getStringArrayList("Asks");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bids_and_asks, container, false);
        ListView listView1=(ListView)view.findViewById(R.id.Bids_list);
        ArrayAdapter arrayAdapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1,Bids);
        listView1.setAdapter(arrayAdapter);
        ListView listView2=(ListView)view.findViewById(R.id.Asks_list);
        ArrayAdapter arrayAdapter2=new ArrayAdapter(context,android.R.layout.simple_list_item_1,Asks);
        listView2.setAdapter(arrayAdapter2);
        return view;
    }


}
