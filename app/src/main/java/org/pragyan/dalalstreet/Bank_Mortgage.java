package org.pragyan.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.android.volley.AuthFailureError;
import org.android.volley.DefaultRetryPolicy;
import org.android.volley.NoConnectionError;
import org.android.volley.Request;
import org.android.volley.Response;
import org.android.volley.RetryPolicy;
import org.android.volley.VolleyError;
import org.android.volley.toolbox.JsonObjectRequest;
import org.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sriram on 17/2/16.
 */
public class Bank_Mortgage extends Fragment {


    static Context context;
    static ArrayList<String> Names=new ArrayList<>();
    static ArrayList<String> Current_Price=new ArrayList<>();
    static ArrayList<String> Stocks_worth=new ArrayList<>();
    static ArrayList<Integer> Stocks_bought=new ArrayList<>();
    static ArrayList<Integer> updown=new ArrayList<>();
    static Bank_Mortgage_list_adapter list_adapter;
    static ProgressBar progressBar;
    static JSONArray mortgage=new JSONArray();


    public static Bank_Mortgage newInstance(Context context_args,String username,String password){
        Bank_Mortgage bank_mortgage=new Bank_Mortgage();
        context=context_args;
        Bundle args=new Bundle();
        args=api_mortgage(context_args, username, password);
        bank_mortgage.setArguments(args);
        return bank_mortgage;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Names=new ArrayList<>();
        Current_Price=new ArrayList<>();
        Stocks_worth=new ArrayList<>();
        Stocks_bought=new ArrayList<>();
        updown=new ArrayList<>();
        Names=getArguments().getStringArrayList("Names");
        Current_Price=getArguments().getStringArrayList("Current");
        Stocks_bought=getArguments().getIntegerArrayList("Stocks Bought");
        updown=getArguments().getIntegerArrayList("updown");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_mortgage, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_mortgage);
        progressBar.setVisibility(View.VISIBLE);
        ListView listView=(ListView)view.findViewById(R.id.bank_mortgage_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent in = new Intent(context, Bank_Mortgage_activity.class);
                    in.putExtra("Stock Name", Names.get(position));
                    in.putExtra("Stocks Bought",Stocks_bought.get(position));
                    startActivity(in);
            }
        });
        list_adapter=new Bank_Mortgage_list_adapter(context,Names,Current_Price,Stocks_bought,updown);
        listView.setAdapter(list_adapter);
        return view;
    }
    static boolean alive;
    static String alive_message;

    public static Bundle api_mortgage(Context context_args, final String username_args, final String password_args){

        Bundle args=new Bundle();
        final Context context=context_args;
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/transactions";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bank mortgage",""+response.length());

                try {

                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(context,alive_message,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent( context,Home.class);
                        intent.putExtra("alive",false);
                        context.startActivity(intent);

                    }
                    mortgage= response.getJSONArray("stocks");
                    if(response.getJSONArray("stocks").length()==0){
                        Toast.makeText(context,"No stocks in bank",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    Log.d("mortgage", "api response" + mortgage);
                    if(mortgage!=null) {
                        list_adapter.clear();
                        for (int i = 0; i < mortgage.length(); i++) {

                            try {

                                JSONObject x =mortgage.getJSONObject(i);
                                if(x != null) {
                                    Names.add(i,x.getString("stockname"));
                                    Current_Price.add(i,x.getString("currentprice"));
                                    Stocks_worth.add(i,x.getString("netcash"));
                                    Stocks_bought.add(i,x.getInt("totalstock"));
                                    updown.add(i, x.getInt("updown"));

                                }
                                else{
                                    Toast.makeText(context,"No stocks in bank",Toast.LENGTH_LONG).show();
                                }


                            } catch (Exception e) {
                                Log.d("mortgage:","respone to json Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        if(list_adapter!=null) {
                            progressBar.setVisibility(View.GONE);

                            list_adapter.notifyDataSetChanged();
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
        args.putStringArrayList("Stock Worth", Stocks_worth);
        args.putIntegerArrayList("Stocks Bought", Stocks_bought);
        args.putIntegerArrayList("updown", updown);
        return args;
    }

}
