package com.example.sriram.dalalstreet;

import android.content.Context;
import android.util.Log;
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
import java.util.Hashtable;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by sriram on 17/2/16.
 */
public class Api {
    int market_events = 0;
    String Cash = new String();
    String Net = new String();
    int Stock = 0;
    ArrayList<Integer> Id = new ArrayList<>();
    JSONArray market_event_list;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> api_Market_events_Company = new ArrayList<>();
    ArrayList<String> Bids = new ArrayList<>();
    ArrayList<String> Asks = new ArrayList<>();

    ArrayList<String> Stock_Names = new ArrayList<>();
    ArrayList<String> Current_Price = new ArrayList<>();
    JSONArray stocks_array;
    Context context;

    public Api(Context context_args) {
        context = context_args;
    }

    public void api_Stocks() {
        String api = context.getString(R.string.api);
        String url = "http://" + api + "/api/stocks";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    stocks_array = response.getJSONArray("stocks_list");

                    for (int i = 0; i < stocks_array.length(); i++) {
                        JSONObject temp = stocks_array.getJSONObject(i);
                        Stock_Names.add(temp.getString("stockname"));
                        Current_Price.add(temp.getString("currentprice"));
                    }

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
                headers.put("X-DALAL-API-EMAIL", "lol@pol.com");
                headers.put("X-DALAL-API-PASSWORD", "password");
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public void api_Bids_and_Asks() {
        String api = context.getString(R.string.api);
        String url = "http://" + api + "/api/bids_and_asks";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    JSONArray bids = response.getJSONArray("bids");
                    JSONArray asks = response.getJSONArray("asks");
                    for (int i = 0; i < bids.length(); i++) {
                        JSONObject temp = bids.getJSONObject(i);
                        Bids.add(temp.getString("message"));
                    }
                    for (int i = 0; i < asks.length(); i++) {
                        JSONObject temp = asks.getJSONObject(i);
                        Asks.add(temp.getString("message"));
                    }
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
                headers.put("X-DALAL-API-EMAIL", "lol@pol.com");
                headers.put("X-DALAL-API-PASSWORD", "password");
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public ArrayList<String> api_getMarketevent(String Stock_Name_args) {

        final String Stock_Name=Stock_Name_args;
        if(Stock_Name==null){
            return null;
        }
        String api = context.getString(R.string.api);
        String url = "http://" + api + "/api/home"+"?company="+Stock_Name;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String temp2=Stock_Name;
                try {

                    market_event_list = response.getJSONArray("market_events_list");

                    for (int i = 0; i < market_event_list.length(); i++) {
                        JSONObject temp = market_event_list.getJSONObject(i);
                        if (temp != null) {
                            api_Market_events_Company.add(temp.getString("eventname"));
                        }
                    }
                    Log.d("api_getMarket","Stock:"+temp2+"\n"+api_Market_events_Company);
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
                headers.put("X-DALAL-API-EMAIL", "lol@pol.com");
                headers.put("X-DALAL-API-PASSWORD", "password");
                return headers;
            }

            /*@Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("company", Stock_Name);
                return params;
            }*/
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        return api_Market_events_Company;
    }




    public void api_Bank_Mortgage() {
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/mortgage";
        JsonObjectRequest jsonObjectRequest=                    new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    Stock=response.getInt("price_of_tot_stock");
                    Cash=response.getString("user_current_cash");
                    Net=response.getString("user_total_calculator");
                    market_events=response.getInt("market_events_total");
                    Log.d("inside api","cash"+Cash+"net"+Net+"market"+market_events+"stock"+Stock);

                    JSONArray stock_list = response.getJSONArray("stocks_list");
                    market_event_list=response.getJSONArray("market_events_list");
                    if(stock_list!=null) {
                        for (int i = 0; i < stock_list.length(); i++) {
                            try {
                                JSONObject x =stock_list.getJSONObject(i);
                                if(x != null) {
                                    Names.add(x.getString("stockname"));
                                    Id.add(x.getInt("id"));
                                }
                            } catch (Exception e) {
                                Log.d("respone to json","Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        Log.d("inside Api Debug","Names"+Names);
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
    }

    public void api_Dalal_home(){
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/home";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    Stock=response.getInt("price_of_tot_stock");
                    Cash=response.getString("user_current_cash");
                    Net=response.getString("user_total_calculator");
                    market_events=response.getInt("market_events_total");
                    Log.d("inside api","cash"+Cash+"net"+Net+"market"+market_events+"stock"+Stock);

                    JSONArray stock_list = response.getJSONArray("stocks_list");
                    market_event_list=response.getJSONArray("market_events_list");
                    if(stock_list!=null) {
                        for (int i = 0; i < stock_list.length(); i++) {
                            try {
                                JSONObject x =stock_list.getJSONObject(i);
                                if(x != null) {
                                    Names.add(x.getString("stockname"));
                                    Id.add(x.getInt("id"));
                                }
                            } catch (Exception e) {
                                Log.d("respone to json","Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        Log.d("inside Api Debug","Names"+Names);
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

    }
    public int getMarket_events(){
        return market_events;
    }
    public int getStock(){
        return Stock;
    }
    public String getCash(){
        return Cash;
    }
    public String getNet(){
        return Net;
    }
    public ArrayList<String> getNames(){
        return Names;
    }
    /* public ArrayList<String> getMarket_event_list(int id){
         ArrayList<String> ans=new ArrayList<>();//id specific market event
         for(int i=0;i<market_event_list.length();i++)
         {
             try {
                 JSONObject temp = market_event_list.getJSONObject(i);
                 if (temp != null) {
                     if(temp.getInt("stock_id")==id) {
                         ans.add(temp.getString("eventname"));
                     }
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
         return ans;
     }*/
    public ArrayList<Integer> getId(){
        return Id;
    }
    public ArrayList<String> getBids(){return Bids;}
    public ArrayList<String> getAsks(){return Asks;}
    public ArrayList<String> getStock_Names(){return Stock_Names;}
    public ArrayList<String> getCurrent_Price(){return Current_Price;}
    public JSONObject get_Stock_Info(String Stock_Name){
        JSONObject temp=new JSONObject();
        for(int i=0;i<stocks_array.length();i++){
            try{
                temp=stocks_array.getJSONObject(i);
                if(temp.getString("stockname").equals(Stock_Name)){
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return temp;
    }
}
