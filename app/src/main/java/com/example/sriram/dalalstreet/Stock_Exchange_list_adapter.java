package com.example.sriram.dalalstreet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Stock_Exchange_list_adapter extends ArrayAdapter<String> {


    static Context context;
    ArrayList<String> Stocks=new ArrayList<>();
    ArrayList<String> Current_Price=new ArrayList<>();

    public Stock_Exchange_list_adapter(Context context2, ArrayList<String> stocks,ArrayList<String> current_Price) {
        super(context2,-1,stocks);
        context=context2;
        Stocks=stocks;
        Current_Price=current_Price;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stock_exchange_item, parent, false);

        TextView textView_Names = (TextView) rowView.findViewById(R.id.stock_name_Exchange);
        TextView textView_Current = (TextView) rowView.findViewById(R.id.current_price_exchange);

        textView_Current.setText(Current_Price.get(position));
        textView_Names.setText(Stocks.get(position));
        return rowView;
    }

}

