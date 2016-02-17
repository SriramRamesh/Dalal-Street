package com.example.sriram.dalalstreet;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
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
import java.util.Map;

/**
 * Created by sriram on 17/2/16.
 */
public class Task_loader extends AsyncTaskLoader<ArrayList<Users>> {

    Context context=null;

    public Task_loader(Context context_args) {
        super(context_args);
        context=context_args;

    }
    @Override
    public ArrayList<Users> loadInBackground() {
        final ArrayList<Users> userses=new ArrayList<>();
        String api = getContext().getString(R.string.api);
        String url = "http://" + api + "/api/leaderboard";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray leaderboard = response.getJSONArray("leaderboard");
                    Log.d("test", "api response" + leaderboard);
                    if (leaderboard != null) {
                        for (int i = 0; i < leaderboard.length(); i++) {
                            try {
                                JSONObject x = leaderboard.getJSONObject(i);

                                if (x != null) {
                                    String Names2=new String();
                                    String Total2=new String();
                                    Names2=x.getString("username");
                                    //Pid.add(x.getInt("id"));
                                    Total2=x.getString("total");
                                    Users temp=new Users(Names2,Total2);
                                    userses.add(temp);
                                }
                            } catch (Exception e) {
                                Log.d("r-espone to json", "Error");
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
        int socketTimeout = 3000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);



        return userses;
    }


}
