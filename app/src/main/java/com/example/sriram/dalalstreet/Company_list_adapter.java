package com.example.sriram.dalalstreet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sriram on 16/2/16.
 */
public class Company_list_adapter extends ArrayAdapter<String>{

    ArrayList<String> Company_Names=new ArrayList<String>();
    ArrayList<String> Current_Price=new ArrayList<String>();
    ArrayList<Integer> updown=new ArrayList<Integer>();
    ArrayList<Integer> Stocks_in_market=new ArrayList<Integer>();
        Context context;
        public Company_list_adapter(Context context2,
                                    ArrayList<String> company_Names_args,
                                    ArrayList<String> current_Price_args,
                                    ArrayList<Integer> updown_args,
                                    ArrayList<Integer> stocks_in_market_args) {
            super(context2,-1,company_Names_args);
            context=context2;
            Company_Names=company_Names_args;
            Current_Price=current_Price_args;
            updown=updown_args;
            Stocks_in_market=stocks_in_market_args;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.company_profile_item, parent, false);

            TextView textView_Names = (TextView) rowView.findViewById(R.id.stockname_company_item);
            TextViewWithImages textView_Price = (TextViewWithImages) rowView.findViewById(R.id.stock_current_price_company_item);
            TextView textView_stocks_in_market=(TextView)rowView.findViewById(R.id.stocks_in_market_company_item);

            textView_Names.setText(Company_Names.get(position));
            if(updown.get(position)==1){
                textView_Price.setText(Current_Price.get(position)+"[img src=green/]");
            }
            else {
                textView_Price.setText(Current_Price.get(position)+"[img src=red/]");
            }

            textView_stocks_in_market.setText(Stocks_in_market.get(position));

            return rowView;
        }


}
