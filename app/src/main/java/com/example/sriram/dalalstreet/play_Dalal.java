package com.example.sriram.dalalstreet;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class play_Dalal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Context context;
    Api api;
    LinearLayout linearLayout;
    TextView textView;
    JSONArray leaderboard=null;
    ArrayList<String> Names=new ArrayList<String>();
    ArrayList<Integer> Pid=new ArrayList<Integer>();
    //String[] Total=new String[300];
   // static Boolean progress_flag;

    Button market_events;
    Button Cash;
    Button Stocks;
    Button Net_Wealth;
    Spinner companies;
    ListView listView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_dalal);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        progressBar=(ProgressBar)findViewById(R.id.progress_play_Dalal);
        progressBar.setVisibility(View.VISIBLE);

        api=new Api(getApplicationContext());
        api.api_Dalal_home();

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
        linearLayout.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dalal_Home();

        getLeaderboard();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //TextView Dalal
        navigationView.setNavigationItemSelectedListener(this);
    }

    void dalal_Home(){

        int market_events_No=api.getMarket_events();
        String Cash_Value=api.getCash();
        String Net_Value=api.getNet();
        final ArrayList<Integer> Id=api.getId();
        int Stock_Value=api.getStock();
        final ArrayList<String> Names_Value=api.getNames();

        Log.d("api","cash"+Cash_Value+"net"+Net_Value+"id"+Id+"stock"+Stock_Value+"\nNames"+Names_Value);
        if(Cash_Value!=null&&Net_Value!=null&&Id!=null&&Stock_Value!=0&&Names_Value!=null){
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            Log.d("dalal_home","All have values");

        }
        if(Names_Value==null&&Cash_Value!=null){
            Log.d("dalal_home","accessing server again");
            api.api_Dalal_home();
            dalal_Home();
        }
       market_events.setText(market_events_No+"\nMarket Events");
        Cash.setText(Cash_Value + "\nCash");
        Net_Wealth.setText(Net_Value + "\nNet Wealth");
        Stocks.setText(Stock_Value + "\nStocks");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Names_Value);
        companies.setAdapter(dataAdapter);

        companies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                Log.d("Market Events","Stock:"+Names_Value.get(position));
                ArrayList<String> events=api.api_getMarketevent(Names_Value.get(position));
                ArrayAdapter arrayAdapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1,events);
                listView.setAdapter(arrayAdapter);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.INVISIBLE);
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_stock_exchange_play_Dalal) {
            textView.setText("Stock Exchange");
            api.api_Stocks();
            fragment=Stock_Exchange.newInstance(context,api.getStock_Names(),api.getCurrent_Price(),1);
            // Handle the camera action
        }
        else if (id == R.id.nav_buy_and_sell_play_Dalal) {
            textView.setText("Buy and Sell");
            api.api_Stocks();
            fragment=Stock_Exchange.newInstance(context,api.getStock_Names(),api.getCurrent_Price(),2);


        } else if (id == R.id.nav_bank_mortage_play_Dalal) {
            textView.setText("Bank");
            //TODO:api.Bank_Morgage()
            //TODO fragment=Bank_Mortgage.newInstance(context,api.getStock_Names(),api.getCurrent_Price(),api.getStock_Bought());

        } else if (id == R.id.nav_transaction_play_Dalal) {
            textView.setText("Transaction");
            fragment=Transaction.newInstance(context,"lol@pol.com","password");
            if(fragment==null) {
                Log.d("Transaction_panel", "fragment is null");
            }
        } else if (id == R.id.nav_bids_play_Dalal) {
            textView.setText("Bids And Asks");
            api.api_Bids_and_Asks();
            fragment=Bids_and_Asks.newInstance(context,api.getBids(),api.getAsks());

        } else if (id == R.id.nav_leaderboard_play_Dalal) {

            getLeaderboard();
            Log.d("test", "Pressed Leaderboard");
            try {

                JSONObject temp = leaderboard.getJSONObject(1);
                Log.d("leaderboard JSON", "" + temp);
            }
            catch(Exception e){
                e.printStackTrace();
                Log.d("leaderboard JSON","Error "+e);
            }

            textView.setText("Leaderboard");
            if(Names!=null&&Pid!=null) {
                fragment = Leaderboard.newInstance(context, Names, Pid);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Null Response",Toast.LENGTH_LONG).show();
                Log.d("test","Json is null");
            }
        } else if (id == R.id.nav_panel_play_Dalal) {
            textView.setText("Market Events");
            dalal_Home();


        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.Layout_play_dalal, fragment).commit();

            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.removeAllViews();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // error in creating fragment
            Log.e("test", "Error in creating fragment");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void getLeaderboard() {

        String api = getString(R.string.api);
        String url="http://"+api + "/api/leaderboard";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    leaderboard = response.getJSONArray("leaderboard");
                    Log.d("test", "api response" + leaderboard);
                    if(leaderboard!=null) {
                        for (int i = 0; i < leaderboard.length(); i++) {
                            try {
                                JSONObject x =leaderboard.getJSONObject(i);
                                if(x != null) {
                                    Names.add(x.getString("username"));
                                    Pid.add(x.getInt("id"));
                                    //Total[i] = x.getString("total");
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
                Toast.makeText(play_Dalal.this, data, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
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

        Volley.newRequestQueue(this).add(jsonObjectRequest);


    }
}

