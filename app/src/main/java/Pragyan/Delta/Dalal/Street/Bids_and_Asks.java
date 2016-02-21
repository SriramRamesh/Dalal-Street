package Pragyan.Delta.Dalal.Street;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import Pragyan.android.volley.AuthFailureError;
import Pragyan.android.volley.DefaultRetryPolicy;
import Pragyan.android.volley.NoConnectionError;
import Pragyan.android.volley.Request;
import Pragyan.android.volley.Response;
import Pragyan.android.volley.RetryPolicy;
import Pragyan.android.volley.VolleyError;
import Pragyan.android.volley.toolbox.JsonObjectRequest;
import Pragyan.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sriram on 17/2/16.
 */
public class Bids_and_Asks extends Fragment {
    static Context context;
    static ArrayList<String> Bids=new ArrayList<>();
    static ArrayList<String> Asks=new ArrayList<>();
    static  ArrayAdapter arrayAdapter;
    static  ArrayAdapter arrayAdapter2;
    static ProgressBar progressBar;


    public static Bids_and_Asks newInstance(Context context_args,String username,String password){
        Bids_and_Asks bids_and_asks=new Bids_and_Asks();
        context=context_args;
        Bundle args=new Bundle();
        args=bids_and_asks.api_Bids_and_Asks(context_args,username,password);
        bids_and_asks.setArguments(args);
        return bids_and_asks;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bids=new ArrayList<>();
        Asks=new ArrayList<>();
        Bids=getArguments().getStringArrayList("Bids");
        Asks=getArguments().getStringArrayList("Asks");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bids_and_asks, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bids_and_asks);
        progressBar.setVisibility(View.VISIBLE);
        ListView listView1=(ListView)view.findViewById(R.id.Bids_list);
        arrayAdapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1,Bids);
        listView1.setAdapter(arrayAdapter);
        ListView listView2=(ListView)view.findViewById(R.id.Asks_list);
        arrayAdapter2=new ArrayAdapter(context,android.R.layout.simple_list_item_1,Asks);
        listView2.setAdapter(arrayAdapter2);
        return view;
    }

    static boolean alive;
    static String alive_message;

    public Bundle api_Bids_and_Asks(Context context_args, final String username_args, final String password_args){
        Bundle args=new Bundle();
        final Context context=context_args;
        String api = context.getString(R.string.api);
        String url = "http://" + api + "/api/bids_and_asks";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(context,alive_message,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent( context,Home.class);
                        intent.putExtra("alive", false);
                        context.startActivity(intent);

                    }
                    Log.d("test", "api response" + response);
                    if(response!=null){
                        arrayAdapter.clear();
                        arrayAdapter2.clear();
                    }
                    JSONArray bids = response.getJSONArray("bids");
                    if(response.getJSONArray("bids").length()==0){
                        Toast.makeText(context,"No bids",Toast.LENGTH_LONG).show();
                    }
                    JSONArray asks = response.getJSONArray("asks");
                    if(response.getJSONArray("asks").length()==0){
                        Toast.makeText(context,"No asks",Toast.LENGTH_LONG).show();
                    }
                    else {
                        for (int i = 0; i < bids.length(); i++) {
                            JSONObject temp = bids.getJSONObject(i);
                            Bids.add(temp.getString("message"));
                        }
                        for (int i = 0; i < asks.length(); i++) {
                            JSONObject temp = asks.getJSONObject(i);
                            Asks.add(temp.getString("message"));
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapter2.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String data = "Error";
                if (error instanceof NoConnectionError) {
                    data = "No internet Access, Check your internet connection.";
                }
                error.printStackTrace();
                Log.d("volley error", "" + error);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // the GET headers:
                headers.put("X-DALAL-API-EMAIL", username_args);
                headers.put("X-DALAL-API-PASSWORD", password_args);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        args.putStringArrayList("Bids", Bids);
        args.putStringArrayList("Asks", Asks);

        return args;
    }


}
