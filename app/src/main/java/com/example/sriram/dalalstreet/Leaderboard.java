package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Leaderboard extends Fragment{

    static Context context;
    String TAG="Leaderboard";
    static ArrayList<String> Names=new ArrayList<>();
    //ArrayList<Integer> Pid;
    static ArrayList<String> Total=new ArrayList<>();
    static Leaderboard_list_adapter list_adapter;

  /*  public Leaderboard(ArrayList<String> Names_args,ArrayList<Integer> Pid_args){
        Names=Names_args;
        Pid=Pid_args;

    }*/
    public static Leaderboard newInstance(Context context_args,String username,String password) {
        Leaderboard leaderboard=new Leaderboard();
        context=context_args;
        Bundle args=new Bundle();
        args=leaderboard.getLeaderboard(context_args,username,password);
        leaderboard.setArguments(args);
        return leaderboard;

    }

    // Store instance variables based on a-rguments passed
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Names=new ArrayList<>();
        Total=new ArrayList<>();
        Names= getArguments().getStringArrayList("Names");
        Total=getArguments().getStringArrayList("Total Assets");
        //Pid = getArguments().getIntegerArrayList("Pid");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        progressBar=(ProgressBar)view.findViewById(R.id.progress_leaderboard);
        progressBar.setVisibility(View.VISIBLE);

        ListView listView=(ListView)view.findViewById(R.id.leaderboard_list);
        list_adapter=new Leaderboard_list_adapter(context,Names,Total);

        listView.setAdapter(list_adapter);
        //progressBar.setVisibility(View.GONE);
        return view;
    }

    Bundle getLeaderboard(final Context context,final String username,final String password) {

        Bundle args=new Bundle();
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/leaderboard";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean alive;
                    String alive_message;
                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(context,alive_message,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent( context,Home.class);
                        intent.putExtra("alive", false);
                        context.startActivity(intent);

                    }
                    JSONArray leaderboard = response.getJSONArray("leaderboard");
                    Log.d(TAG, "api response" + leaderboard);
                    if(leaderboard!=null) {
                        for (int i = 0; i < leaderboard.length(); i++) {
                            try {
                                JSONObject x =leaderboard.getJSONObject(i);
                                if(x != null) {
                                    Names.add(i, x.getString("username"));
                                    //Pid.add(x.getInt("id"));
                                    Log.d(TAG,"x.getStr(tot)="+x.getString("total"));
                                    Total.add(i, x.getString("total"));
                                }
                            } catch (Exception e) {
                                Log.d(TAG,"respone to json Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        //list_adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String data="Error";
                if(error instanceof NoConnectionError) {
                    data= "No internet Access, Check your internet connection.";
                }
                error.printStackTrace();
                Log.d(TAG, "volley error" + error);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // the GET headers:
                headers.put("X-DALAL-API-EMAIL", username);
                headers.put("X-DALAL-API-PASSWORD", password);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        args.putStringArrayList("Names",Names);
        args.putStringArrayList("Total Assets", Total);
        return args;

    }


}