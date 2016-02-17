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
public class Transaction_list_adapter extends ArrayAdapter<String>{

    static Context context;
    ArrayList<String> Names=new ArrayList<>();
    ArrayList<String> Current_Price=new ArrayList<>();
    ArrayList<String> Stocks_worth=new ArrayList<>();
    ArrayList<Integer> Stocks_bought=new ArrayList<>();

    public Transaction_list_adapter(Context context2,
                                    ArrayList<String> names_args,ArrayList<String> current,
                                    ArrayList<Integer> stocks_bought,
                                    ArrayList<String> stocks_worth) {
        super(context2,-1,names_args);
        context=context2;
        Names=names_args;
        Current_Price=current;
        Stocks_bought=stocks_bought;
        Stocks_worth=stocks_worth;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transaction_item, parent, false);

        TextView textView_Names = (TextView) rowView.findViewById(R.id.stock_Name_transaction);
        TextView textView_Current = (TextView) rowView.findViewById(R.id.current_transaction);
        TextView textView_Stocks_bought=(TextView) rowView.findViewById(R.id.stock_bought_transaction);
        TextView textView_Stocks_worth=(TextView) rowView.findViewById(R.id.stock_worth_transaction);

        textView_Names.setText(Names.get(position));
        textView_Current.setText(Current_Price.get(position));
        textView_Stocks_bought.setText(Stocks_bought.get(position)+"");
        textView_Stocks_worth.setText(Stocks_worth.get(position));
        return rowView;
    }




}
