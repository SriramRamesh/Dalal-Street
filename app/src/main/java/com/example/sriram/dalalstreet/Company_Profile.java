package com.example.sriram.dalalstreet;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
 * Created by sriram on 16/2/16.
 */
public class Company_Profile extends Fragment {
    static Context context2;
    ArrayList<String> Company_Names=new ArrayList<String>();
    ArrayList<String> Current_Price=new ArrayList<String>();
    ArrayList<Integer> updown=new ArrayList<Integer>();
    ArrayList<Integer> Stocks_in_market=new ArrayList<Integer>();
    Context context;




    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Update_Companies up=new Update_Companies();
        up.execute();
        context=getActivity();
       /* Company_Names= getArguments().getStringArray("Companies");
        Current_Price=getArguments().getStringArray("Current Price");
        updown=getArguments().getIntArray("Updown");
        Stocks_in_market=getArguments().getIntArray("Stocks in market");*/
    }

    public class Update_Companies extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String api = getString(R.string.api);
            String url="http://"+api + "/api/stocks";
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray stocks = response.getJSONArray("stocks");
                        Log.d("test", "api response" + stocks);
                        if(stocks!=null) {
                            for (int i = 0; i < stocks.length(); i++) {
                                try {
                                    JSONObject x =stocks.getJSONObject(i);
                                    if(x != null) {
                                        Company_Names.add(x.getString("stockname"));
                                        Current_Price.add(x.getString("currentprice"));
                                        updown.add(x.getInt("updown"));
                                        Stocks_in_market.add(x.getInt("stocksinmarket"));

                                    }
                                } catch (Exception e) {
                                    Log.d("r-espone to json","Error");
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
                @Override
                public void onErrorResponse(VolleyError error) {
                    String data="Error";
                    if(error instanceof NoConnectionError) {
                        data= "No internet Access, Check your internet connection.";
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
                    headers.put("X-DALAL-API-EMAIL", "lol@pol.com");
                    headers.put("X-DALAL-API-PASSWORD", "password");
                    return headers;
                }
            };
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);

            Volley.newRequestQueue(context).add(jsonObjectRequest);


            return null;
        }

        /*@Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }*/

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        ListView listView=(ListView)view.findViewById(R.id.company_profile_list);
        Company_list_adapter list_adapter=new Company_list_adapter(context2,Company_Names,Current_Price,
                updown,Stocks_in_market);

        listView.setAdapter(list_adapter);

        return view;
    }




}
