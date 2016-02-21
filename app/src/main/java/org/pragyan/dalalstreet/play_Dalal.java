package org.pragyan.dalalstreet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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


public class play_Dalal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TAG="play_Dalal";
    String username;
    String password;
    Context context;
    LinearLayout linearLayout;
    TextView textView;
    JSONArray leaderboard=null;
    //String[] Total=new String[300];
    // static Boolean progress_flag;

    SharedPreferences.Editor editor;
    static int market_events_No;
    static String Cash_Value;
    static String Net_Value;
    static int Stock_Value;
    TextView textView_username;

    static ArrayList<Integer> Company_Id=new ArrayList<>();
    static ArrayList<String> Company_Names=new ArrayList<>();

    Button market_events;
    Button Cash;
    Button Stocks;
    Button Net_Wealth;

    Spinner companies;
    static ArrayAdapter<String> dataAdapter;

    ListView listView;
    static ArrayList<String> events=new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
   // SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_dalal);
        Log.d("play dalal", "on Create");
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        progressBar=(ProgressBar)findViewById(R.id.progress_play_Dalal);
        progressBar.setVisibility(View.VISIBLE);



        sharedPreferences =getSharedPreferences("User Details", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        username=sharedPreferences.getString("username", null);
        password=sharedPreferences.getString("password", null);
       // editor=sharedPreferences.edit();

        Log.d("play_Dalal","username"+username+"password"+password);


        market_events=(Button)findViewById(R.id.marketEvents);
        Cash=(Button)findViewById(R.id.cash);
        Stocks=(Button)findViewById(R.id.stock);
        Net_Wealth=(Button)findViewById(R.id.netWealth);
        companies=(Spinner)findViewById(R.id.home_spinner);
        listView=(ListView)findViewById(R.id.market_list_content);
        textView = (TextView) findViewById(R.id.title_content_play_dalal);

        textView.setText("Market Events");
        context = getApplicationContext();
        linearLayout = (LinearLayout) findViewById(R.id.Layout_play_dalal);
        //linearLayout.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        api_Dalal_home(username, password);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //TextView Dalal
        navigationView.setNavigationItemSelectedListener(this);
        /*try {
            textView_username = (TextView) findViewById(R.id.nav_user_email);
            textView_username.setText(username);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }

    void dalal_Home(){


        Log.d("api","cash"+Cash_Value+"net"+Net_Value+"id"+Company_Id+"stock"+Stock_Value+"\nNames"+Company_Names);

        market_events.setText(market_events_No+"\nMarket Events");
        Cash.setText(Cash_Value + "\nCash");
        Net_Wealth.setText(Net_Value + "\nNet Wealth");
        Stocks.setText(Stock_Value + "\nStocks");

        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Company_Names);
        companies.setAdapter(dataAdapter);

        companies.setOnItemSelectedListener(listener);
    }
    boolean flag=true;
    AdapterView.OnItemSelectedListener listener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(flag) {
                progressBar.setVisibility(View.VISIBLE);
                Log.d("Market Events", "Stock:" + Company_Names.get(position));
                /*ArrayList<String> temp = */
                api_Dalal_home(Company_Names.get(position), username, password);
                Log.d(TAG, "temp=" + events);

            }
            /*else{
                flag=true;
            }*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the play_Dalal/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.INVISIBLE);
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_stock_exchange_play_Dalal) {
            textView.setText("Stock Exchange");
            fragment=Stock_Exchange.newInstance(context,username,password,1);
        }
        else if (id == R.id.nav_buy_and_sell_play_Dalal) {
            textView.setText("Buy and Sell");
            //api.api_Stocks();
            fragment=Stock_Exchange.newInstance(context,username,password,2);


        } else if (id == R.id.nav_bank_mortage_play_Dalal) {
            textView.setText("Bank");
            fragment=Bank_Mortgage.newInstance(context,username,password);
        } else if (id == R.id.nav_transaction_play_Dalal) {
            textView.setText("Transaction");
            fragment=Transaction.newInstance(context,username,password);
            if(fragment==null) {
                Log.d("Transaction_panel", "fragment is null");
            }
        } else if (id == R.id.nav_bids_play_Dalal) {
            textView.setText("Bids And Asks");
            //api.api_Bids_and_Asks();
            fragment=Bids_and_Asks.newInstance(context,username,password);

        } else if (id == R.id.nav_leaderboard_play_Dalal) {

            textView.setText("Leaderboard");
            fragment = Leaderboard.newInstance(context, username,password);

        } else if (id == R.id.nav_panel_play_Dalal) {
            setContentView(R.layout.activity_play_dalal);
            progressBar.setVisibility(View.VISIBLE);

            market_events=(Button)findViewById(R.id.marketEvents);
            Cash=(Button)findViewById(R.id.cash);
            Stocks=(Button)findViewById(R.id.stock);
            Net_Wealth=(Button)findViewById(R.id.netWealth);
            companies=(Spinner)findViewById(R.id.home_spinner);
            listView=(ListView)findViewById(R.id.market_list_content);
            textView = (TextView) findViewById(R.id.title_content_play_dalal);

            textView.setText("Market Events");
            context = getApplicationContext();
            linearLayout = (LinearLayout) findViewById(R.id.Layout_play_dalal);
            //linearLayout.setVisibility(View.INVISIBLE);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            api_Dalal_home(username, password);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            //TextView Dalal
            navigationView.setNavigationItemSelectedListener(this);
            progressBar.setVisibility(View.GONE);



        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.Layout_play_dalal, fragment).commit();

            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.removeAllViews();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            // error in creating fragment
            Log.e("test", "Error in creating fragment");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void api_Dalal_home(String company_Name_args,final String username,final String password){
        final String company_Name=company_Name_args;
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/home?company="+company_Name_args;
        //ArrayList<String> events=new ArrayList<>();
        Log.d(TAG,"url"+url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    JSONArray market_event_list=response.getJSONArray("market_events_list");
                    //arrayAdapter.clear();
                    //arrayAdapter.notifyDataSetChanged();

                    events=new ArrayList<>();
                    for(int i=0;i<market_event_list.length();i++){
                        try {
                            JSONObject x =market_event_list.getJSONObject(i);
                            if(x != null) {

                                events.add(x.getString("eventname"));
                            }
                        } catch (Exception e) {
                            Log.d("respone to json","Error");
                            e.printStackTrace();
                            break;
                        }
                    }
                    arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, events);
                    listView.setAdapter(arrayAdapter);

                    //arrayAdapter.notifyDataSetChanged();
                    //arrayAdapter.clear();
                    //dalal_Home();
                    progressBar.setVisibility(View.GONE);
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
                headers.put("X-DALAL-API-EMAIL", username);
                headers.put("X-DALAL-API-PASSWORD", password);
                return headers;
            }


        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);


        //return events;
    }

    public void api_Dalal_home(final String username,final String password){
        String api = context.getString(R.string.api);
        String url="http://"+api + "/api/home";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", "api response" + response);
                    boolean alive;
                    String alive_message;
                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(context,alive_message,Toast.LENGTH_LONG);
                        Intent intent=new Intent( context,Home.class);
                        intent.putExtra("alive", false);
                        context.startActivity(intent);

                    }
                    Stock_Value = response.getInt("price_of_tot_stock");
                    Cash_Value = response.getString("user_current_cash");
                    Net_Value = response.getString("user_total_calculator");
                    market_events_No = response.getInt("market_events_total");
                    dalal_Home();
                    JSONArray stock_list = response.getJSONArray("stocks_list");

                    dataAdapter.clear();
                    if (stock_list != null) {
                        for (int i = 0; i < stock_list.length(); i++) {
                            try {
                                JSONObject x = stock_list.getJSONObject(i);
                                if (x != null) {
                                    Company_Names.add(x.getString("stockname"));
                                    Company_Id.add(x.getInt("id"));

                                }
                            } catch (Exception e) {
                                Log.d("respone to json", "Error");
                                e.printStackTrace();
                                break;
                            }
                        }
                        dalal_Home();
                        dataAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        Log.d("inside Api Debug", "Names" + Company_Names);
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
                headers.put("X-DALAL-API-EMAIL", username);
                headers.put("X-DALAL-API-PASSWORD", password);
                return headers;
            }

        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        Volley.newRequestQueue(context).add(jsonObjectRequest);


    }
}

