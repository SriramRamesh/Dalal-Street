package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Transaction extends Fragment {
    static Context context;
    static String username=new String();
    static String password=new String();
    static ArrayList<String> Names=new ArrayList<>();
    static ArrayList<String> Current_Price=new ArrayList<>();
    static ArrayList<String> Stocks_worth=new ArrayList<>();
    static ArrayList<Integer> Stocks_bought=new ArrayList<>();
    static ArrayList<Integer> updown=new ArrayList<>();


    public static Transaction newInstance(Context context_args,String username,String password){
        Transaction transaction=new Transaction();
        context=context_args;
        Bundle args=new Bundle();
        args=api_transaction(context_args,username,password);
        args.putString("useremail",username);
        args.putString("password",password);
        transaction.setArguments(args);
        return transaction;
    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Names=new ArrayList<>();
            Current_Price=new ArrayList<>();
            Stocks_worth=new ArrayList<>();
            Stocks_bought=new ArrayList<>();
            updown=new ArrayList<>();
            username = getArguments().getString("useremail");
            password = getArguments().getString("password");
            Names=getArguments().getStringArrayList("Names");
            Current_Price= getArguments().getStringArrayList("Current");
            Stocks_worth=getArguments().getStringArrayList("Stock Worth");
            Stocks_bought=getArguments().getIntegerArrayList("Stocks Bought");
            updown=getArguments().getIntegerArrayList("updown");
        }catch (Exception e){
            e.printStackTrace();

        }




    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.progress_transaction);
        progressBar.setVisibility(View.VISIBLE);
        ListView listView=(ListView)view.findViewById(R.id.transaction_list);
        if(Names==null) {
            Log.d("Trans frag", "Null parms");
        }

        Log.d("Trans frag", "Names"+Names);
        if(Names!=null){
            progressBar.setVisibility(View.GONE);
        }
        Transaction_list_adapter list_adapter=new Transaction_list_adapter(context,Names,Current_Price,Stocks_bought,Stocks_worth,updown);
        try{
            listView.setAdapter(list_adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    public static Bundle api_transaction(Context context_args, final String username_args, final String password_args){

        Bundle args=new Bundle();
        final Context context=context_args;
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/transactions";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray transaction=new JSONArray();
                try {

                    transaction= response.getJSONArray("stocks");
                    Log.d("transaction", "api response" + transaction);
                    if(transaction!=null) {
                        for (int i = 0; i < transaction.length(); i++) {
                            try {

                                JSONObject x =transaction.getJSONObject(i);
                                if(x != null) {
                                    Names.add(i,x.getString("stockname"));
                                    Current_Price.add(i,x.getString("currentprice"));
                                    Stocks_worth.add(i,x.getString("netcash"));
                                    Stocks_bought.add(i,x.getInt("totalstock"));
                                    updown.add(i,x.getInt("updown"));
                                }
                            } catch (Exception e) {
                                Log.d("transaction:","respone to json Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            Context temp=context;
            @Override
            public void onErrorResponse(VolleyError error) {
                String data="Error";
                if(error instanceof NoConnectionError) {
                    data= "No internet Access, Check your internet connection.";
                }
                error.printStackTrace();
                Log.d("volley error", "" + error);
                Toast.makeText(temp, data, Toast.LENGTH_SHORT).show();
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
        args.putStringArrayList("Names", Names);
        args.putStringArrayList("Current",Current_Price);
        args.putStringArrayList("Stock Worth",Stocks_worth);
        args.putIntegerArrayList("Stocks Bought", Stocks_bought);
        args.putIntegerArrayList("updown",updown);
        return args;
    }




}
