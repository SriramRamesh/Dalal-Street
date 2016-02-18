package com.example.sriram.dalalstreet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;
import java.util.Map;

public class Return_Bank_Stock_activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String username=new String();
    String password=new String();
    Context context;
    String Stock_Name=new String();
    ListView listView;
    String status=new String();
    static ArrayList<Integer> Stock=new ArrayList<>();
    static ArrayList<Integer> Price=new ArrayList<>();
    static ArrayList<Integer> ID=new ArrayList<>();//return stock id
    //int Stocks_bought=-1;
    Return_List_adapter return_list_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_bank_stock);

        listView=(ListView)findViewById(R.id.return_stock_list);
        //listView.setAdapter(list_adapter);
        sharedPreferences=getSharedPreferences("User Details", MODE_PRIVATE);
        /*TODO: username
        username=sharedPreferences.getString("username",null);
        password=sharedPreferences.getString("password",null);*/
        username="lol@pol.com";
        password="password";
        context=getApplicationContext();
        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");

        api_getReturn_Stocks(username,password);
        return_list_adapter=new Return_List_adapter(context,Stock,Price);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to return the stock?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Update_return_Stock(username,password, ID.get(position));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.create();
                builder.show();
            }
        });


    }
    void api_getReturn_Stocks(final String username_args,final String password_args){

        String api = context.getString(R.string.api);

        String url="http://"+api + "/api/mortgage"+Stock_Name;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    return_list_adapter.clear();
                    Log.d("test", "api response" + response);
                    status=response.getString("success");
                    JSONArray mortgage=response.getJSONArray("mortgage");
                    JSONObject temp=new JSONObject();
                    switch(status){
                        case "true":{

                            for(int i=0;i<mortgage.length();i++) {
                                temp=mortgage.getJSONObject(i);
                                if(temp!=null){
                                    Stock.add(i,temp.getInt("numofstock"));
                                    Price.add(i,temp.getInt("pricerendered"));
                                    ID.add(i,temp.getInt("id"));
                                }
                            }

                            break;
                        }
                        case "false":{
                            Toast.makeText(context,"Invalid Company",Toast.LENGTH_LONG).show();
                            break;
                        }

                    }
                    return_list_adapter.notifyDataSetChanged();


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
                headers.put("X-DALAL-API-EMAIL", username_args);
                headers.put("X-DALAL-API-PASSWORD", password_args);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }


    void Update_return_Stock(final String username_args,final String password_args,int id) {
        String api = context.getString(R.string.api);

        String url="http://"+api + "/api/mortgage/return_from_bank/"+id;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    String status=response.getString("success");
                    String message=response.getString("message");
                    switch(status){
                        case "true":{
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            break;
                        }
                        case "false":{
                            Toast.makeText(context,"Invalid Request",Toast.LENGTH_LONG).show();
                            break;
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
                headers.put("X-DALAL-API-EMAIL", username_args);
                headers.put("X-DALAL-API-PASSWORD", password_args);
                return headers;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

}