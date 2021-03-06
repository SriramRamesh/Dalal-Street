package org.pragyan.dalalstreet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Buy extends AppCompatActivity {
    String Stock_Name=new String();
    Context context;
    EditText editText_bid,editText_stocks;
    String username,password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        context=getApplicationContext();
        //TODO:sharedpref and username
        sharedPreferences =getSharedPreferences("User Details",MODE_PRIVATE);
        username=sharedPreferences.getString("username", null);
        password=sharedPreferences.getString("password", null);


        Intent in=getIntent();
        Stock_Name=in.getStringExtra("Stock Name");

        TextView textView=(TextView)findViewById(R.id.title_buy);
        editText_bid=(EditText)findViewById(R.id.bid_price_buy);
        editText_stocks=(EditText)findViewById(R.id.stocks_to_bid_buy);
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

    public void Call_Buy_api(View v) {
        String stocks=editText_stocks.getText().toString();
        String price=editText_bid.getText().toString();
        if(validate(stocks)&&validate(price)){
            int no_of_stocks=Integer.parseInt(stocks);
            int bid=Integer.parseInt(price);
            Update_api(no_of_stocks,bid,username,password);
        }
        else{
            Toast.makeText(context, "Invalid Parameters", Toast.LENGTH_LONG).show();
        }

    }
    static boolean alive;
    static String alive_message;

    public void Update_api(final int no_of_stocks, final int bid_price,final String username_args, final String password_args){

        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/stocks/bid/"+Stock_Name+"?num_of_stock="+no_of_stocks+"&price="+bid_price;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status;
                    String message;
                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(context,alive_message,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent( context,Home.class);
                        intent.putExtra("alive", false);
                        context.startActivity(intent);

                    }

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
           /* @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("num_of_stock",no_of_stocks+"");
                params.put("price",bid_price+"");
                return params;
            }*/
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);



    }
}
