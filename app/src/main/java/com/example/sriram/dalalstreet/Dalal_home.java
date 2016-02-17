package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sriram on 17/2/16.
 */
public class Dalal_home extends Fragment {
    static Context context2;
    ArrayList<String> Names;
    ArrayList<Integer> Pid;
    //String[] Total;

    /*  public Leaderboard(ArrayList<String> Names_args,ArrayList<Integer> Pid_args){
          Names=Names_args;
          Pid=Pid_args;

      }*/
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
   /* public class getLeaderboard extends AsyncTask<Void,Void,Leaderboard>{
        ProgressDialog progressDialog=new ProgressDialog(getActivity());


        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Updating");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Leaderboard doInBackground(Void... params) {

            return null;
        }

        @Override
        protected Leaderboard onPostExecute(Void v){
            Leaderboard leaderboard=new Leaderboard();


        }
    }
*/

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
