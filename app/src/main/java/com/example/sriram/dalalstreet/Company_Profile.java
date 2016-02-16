package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by sriram on 16/2/16.
 */
public class Company_Profile extends Fragment {
    static Context context2;
    String[] Company_Names;
    String[] Current_Price;
    int[] updown;
    int[] Stocks_in_market;

    public static Company_Profile newInstance(Context context, String[] Names_args,String[] currentPrice_args,
                                              int[] updown_args,int[] Stocks_in_market_args ){
        Company_Profile company_profile=new Company_Profile();
        context2=context;
        Bundle args=new Bundle();
        args.putStringArray("Companies", Names_args);
        args.putStringArray("Current Price",currentPrice_args);
        args.putIntArray("Updown", updown_args);
        args.putIntArray("Stocks in market",Stocks_in_market_args);
        company_profile.setArguments(args);
        return company_profile;

    }




    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Company_Names= getArguments().getStringArray("Companies");
        Current_Price=getArguments().getStringArray("Current Price");
        updown=getArguments().getIntArray("Updown");
        Stocks_in_market=getArguments().getIntArray("Stocks in market");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        ListView listView=(ListView)view.findViewById(R.id.company_profile_list);
        Company_list_adapter list_adapter=new Company_list_adapter(context2,Company_Names,Current_Price,
                updown,Stocks_in_market);

        listView.setAdapter(list_adapter);

        return view;
    }




}
