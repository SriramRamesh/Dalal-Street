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

public class Trade extends AppCompatActivity {
    String Stock_Name=new String();
    String Stock_in_Exchange=new String();
    EditText editText;
    String username;
    String password;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        context=getApplicationContext();
        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");
        Stock_in_Exchange=in.getStringExtra("Stock in Exchange");

        TextView textView=(TextView)findViewById(R.id.title_trade);
        textView.setText(Stock_Name);

    }

    public void Call_Trade_api(View v){
        String s=editText.getText().toString();
        if(validate(s)){
            int no_of_stocks=Integer.parseInt(s);
            Update_api(no_of_stocks,username,password);
        }
        else{
            Toast.makeText(context, "Invalid Number of Stocks!!!", Toast.LENGTH_LONG).show();
        }
    }
    private boolean validate(String s){
        try{
            int i=0;
            int stocks=Integer.parseInt(Stock_in_Exchange);
            i=Integer.parseInt(s);
            if(i>stocks){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public void Update_api(int no_of_stocks, final String username_args, final String password_args){
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/stocks/"+Stock_Name+"?num_of_stock="+no_of_stocks;
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
