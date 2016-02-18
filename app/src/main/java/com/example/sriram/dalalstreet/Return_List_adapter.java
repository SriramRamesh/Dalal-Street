package com.example.sriram.dalalstreet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sriram on 19/2/16.
 */
public class Return_List_adapter extends ArrayAdapter<Integer> {

    static Context context;
    ArrayList<Integer> Mortgaged_Price=new ArrayList<>();
    ArrayList<Integer> Stocks_mortgaged=new ArrayList<>();

    public Return_List_adapter(Context context_args, ArrayList<Integer> mortgaged_Price,
                                      ArrayList<Integer> stocks_mortgaged){
        super(context_args,-1,mortgaged_Price);
        context=context_args;
        Stocks_mortgaged=stocks_mortgaged;
        Mortgaged_Price=mortgaged_Price;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.return_stock_item, parent, false);

        TextView textView_Price = (TextView)rowView.findViewById(R.id.mortgaged_return_price);
        TextView textView_Stocks=(TextView)rowView.findViewById(R.id.stocks_mortgaged);

        textView_Stocks.setText(Stocks_mortgaged.get(position).toString());
        textView_Price.setText(Mortgaged_Price.get(position).toString());
        return rowView;
    }

}
