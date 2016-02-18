package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

/**
 * Created by sriram on 17/2/16.
 */
public class Stock_Exchange extends Fragment {
    static Context context;
    static ArrayList<String> Stocks=new ArrayList<>();
    static ArrayList<String> Current_Price=new ArrayList<>();
    static int flag=0;
    static Stock_Exchange_list_adapter arrayAdapter;
    static ProgressBar progressBar;
    static JSONArray stocks_array=new JSONArray();

    public static Stock_Exchange newInstance(Context context_args,String username,String password,int flag_args){
        Stock_Exchange stock_exchange=new Stock_Exchange();
        context=context_args;
        Bundle args=new Bundle();
        args=api_Stock_Exchange(context_args, username, password);

        flag=flag_args;
        stock_exchange.setArguments(args);
        return stock_exchange;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stocks=getArguments().getStringArrayList("Stocks");
        Current_Price=getArguments().getStringArrayList("Current");
        //flag=getArguments().getInt("Flag");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock_exchange, container, false);
        ListView listView=(ListView)view.findViewById(R.id.stock_exchange_list);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_exchange);
        progressBar.setVisibility(View.VISIBLE);
        arrayAdapter=new Stock_Exchange_list_adapter(context,Stocks,Current_Price);
        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (flag == 1) {

                            Intent in = new Intent(getActivity(), Stock_Exchange_activity.class);
                            //in.putExtra("Stock Name", Stocks.get(position));
                            in.putExtra("Stock JSON",stocks_array.get(position).toString());
                            getActivity().startActivity(in);

                        } else if (flag == 2) {

                            Intent in = new Intent(getActivity(), Buy_and_Sell_activity.class);
                            //in.putExtra("Stock Name", Stocks.get(position));
                            in.putExtra("Stock JSON",stocks_array.get(position).toString());
                            getActivity().startActivity(in);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setAdapter(arrayAdapter);
        return view;
    }

    private static Bundle api_Stock_Exchange(Context context_args, final String username_args, final String password_args){
        Bundle args=new Bundle();
        String api = context_args.getString(R.string.api);
        String url = "http://" + api + "/api/stocks";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.d("test", "api response" + response);

                    stocks_array = response.getJSONArray("stocks_list");
                    if(stocks_array!=null) {
                        arrayAdapter.clear() ;
                        for (int i = 0; i < stocks_array.length(); i++) {
                            JSONObject temp = stocks_array.getJSONObject(i);
                            if (temp != null) {
                                Stocks.add(temp.getString("stockname"));
                                Current_Price.add(temp.getString("currentprice"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(arrayAdapter!=null){
                    progressBar.setVisibility(View.GONE);
                    arrayAdapter.notifyDataSetChanged();
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
        args.putStringArrayList("Stocks", Stocks);
        args.putStringArrayList("Current", Current_Price);
        return args;
    }


}
