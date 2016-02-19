package com.example.sriram.dalalstreet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sell extends AppCompatActivity {

    String Stock_Name=new String();
    Context context;
    EditText editText_ask,editText_stocks;
    String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        context=getApplicationContext();
        //TODO:sharedpref and username
        username="lol@pol.com";
        password="password";

        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");

        TextView textView=(TextView)findViewById(R.id.title_sell);
        editText_ask=(EditText)findViewById(R.id.ask_price_sell);
        editText_stocks=(EditText)findViewById(R.id.stocks_to_ask_sell);
        textView.setText(Stock_Name);
    }



    private boolean validate(String s){
        try{
            int i=0;
            i=Integer.parseInt(s);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void Call_Sell_api(View v) {
        String stocks=editText_stocks.getText().toString();
        String price=editText_ask.getText().toString();
        if(validate(stocks)&&validate(price)){
            int no_of_stocks=Integer.parseInt(stocks);
            int ask=Integer.parseInt(price);
            Update_api(no_of_stocks,ask,username,password);
        }
        else{
            Toast.makeText(context, "Invalid Parmaeters", Toast.LENGTH_LONG).show();
        }

    }
    public void Update_api(final int no_of_stocks, final int ask_price,final String username_args, final String password_args){

        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/stocks/ask/"+Stock_Name;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status;
                    String message;

                    Log.d("test", "api response" + response);
                    status=response.getString("success");
                    message=response.getString("message");
                    switch(status){
                        case "true":{
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            break;
                        }
                        case "false":{
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
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
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("num_of_stock",no_of_stocks+"");
                params.put("price",ask_price+"");
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);



    }


}
