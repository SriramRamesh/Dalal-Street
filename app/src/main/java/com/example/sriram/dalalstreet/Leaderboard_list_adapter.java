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

    ArrayList<String> Names;
    ArrayList<Integer> Pid;
    //String[] Total;
    Context context;
    public Leaderboard_list_adapter(Context context2, ArrayList<Integer> Pid_args,ArrayList<String> names_args) {
        super(context2,-1,names_args);
        context=context2;
        Pid=Pid_args;
        Names=names_args;
        //Total=total_args;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.leaderboard_item, parent, false);

        TextView textView_Names = (TextView) rowView.findViewById(R.id.name_leaderboard_item);
        TextView textView_Pid = (TextView) rowView.findViewById(R.id.pid_leaderboard_item);
        //TextView textView_total=(TextView)rowView.findViewById(R.id.total_leaderboard_item);

        textView_Pid.setText(Pid.get(position)+"");
        textView_Names.setText(Names.get(position));
        return rowView;
    }

}
