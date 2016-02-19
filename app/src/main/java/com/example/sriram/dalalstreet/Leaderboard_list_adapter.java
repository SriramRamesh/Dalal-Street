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

    static Context context;
    static ArrayList<String> Names=new ArrayList<>();
    //ArrayList<Integer> Pid;
    static ArrayList<String> Total=new ArrayList<>();
    static Leaderboard_list_adapter list_adapter;

    public Leaderboard_list_adapter(Context context_args, ArrayList<String> names_args,ArrayList<String> total_args) {
        super(context_args,-1,names_args);
        context=context_args;
        //Pid=Pid_args;
        Names=names_args;
        Total=total_args;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.leaderboard_item, parent, false);

        TextView textView_Names = (TextView) rowView.findViewById(R.id.name_leaderboard_item);
        //TextView textView_Pid = (TextView) rowView.findViewById(R.id.pid_leaderboard_item);
        TextView textView_total=(TextView)rowView.findViewById(R.id.total_leaderboard_item);

        //textView_Pid.setText(Pid.get(position)+"");
        if (Names!=null & Total != null) {
            textView_Names.setText(Names.get(position));
            textView_total.setText(Total.get(position));

        }
        return rowView;
    }

}
