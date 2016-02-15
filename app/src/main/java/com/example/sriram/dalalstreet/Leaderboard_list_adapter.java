package com.example.sriram.dalalstreet;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by sriram on 16/2/16.
 */
public class Leaderboard_list_adapter extends ArrayAdapter<String> {

    String[] Names;
    Context context;
    public Leaderboard_list_adapter(Context context2, String[] names_args) {
        super(context2,-1,names_args);
        context=context2;
        Names=names_args;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.leaderboard_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.leaderboard_item_name);
        TextView textViewrank = (TextView) rowView.findViewById(R.id.leaderboard_item_rank);
        textView.setText(Names[position]);
        int temp=position+1;
        textViewrank.setText(""+temp);
        return rowView;
    }
}
