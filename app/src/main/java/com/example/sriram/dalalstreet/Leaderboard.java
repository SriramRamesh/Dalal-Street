package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Leaderboard extends Fragment{

    static Context context2;
    ArrayList<String> Names;
    ArrayList<Integer> Pid;
    //String[] Total;

    public static Leaderboard newInstance(Context context, ArrayList<String> Names_args,ArrayList<Integer> Pid_args){
        Leaderboard leaderboard=new Leaderboard();
        context2=context;
        Bundle args=new Bundle();
        args.putStringArrayList("Names", Names_args);
        args.putIntegerArrayList("Pid", Pid_args);
        //args.putStringArray("Total Assets",Total_args);
        leaderboard.setArguments(args);
        return leaderboard;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Names= getArguments().getStringArrayList("Names");
        //Total=getArguments().getStringArray("Total Assests");
        Pid = getArguments().getIntegerArrayList("Pid");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        ListView listView=(ListView)view.findViewById(R.id.leaderboard_list);
        Leaderboard_list_adapter list_adapter=new Leaderboard_list_adapter(context2,Pid,Names);

        listView.setAdapter(list_adapter);

        return view;
    }




}