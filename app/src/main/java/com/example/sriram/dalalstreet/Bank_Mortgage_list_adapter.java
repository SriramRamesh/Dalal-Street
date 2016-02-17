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
public class Bank_Mortgage_list_adapter extends ArrayAdapter<String> {

    static Context context;
    ArrayList<String> Stocks=new ArrayList<>();
    ArrayList<String> Current_Price=new ArrayList<>();
    ArrayList<Integer> Stocks_bought=new ArrayList<>();

    public Bank_Mortgage_list_adapter(Context context_args, ArrayList<String> Stocks_args,
                                      ArrayList<String> current_Price,ArrayList<Integer> stocks_bought){
        super(context_args,-1,Stocks_args);
        context=context_args;
        Stocks=Stocks_args;
        Current_Price=current_Price;
        Stocks_bought=stocks_bought;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.bank_mortgage_item, parent, false);

        TextView textView_Names = (TextView) rowView.findViewById(R.id.stock_name_Mortgage);
        TextView textView_Current = (TextView) rowView.findViewById(R.id.current_price_mortgage);
        TextView textView_Stocks_bought=(TextView)rowView.findViewById(R.id.stocks_bought_mortgage);

        textView_Names.setText(Stocks.get(position));
        textView_Current.setText(Current_Price.get(position));
        textView_Stocks_bought.setText(Stocks_bought.get(position));
        return rowView;
    }

}
