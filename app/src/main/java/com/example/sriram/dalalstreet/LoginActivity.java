package com.example.sriram.dalalstreet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    static boolean auth_error;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    static String username;
    static String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;
    boolean logged_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        sharedPreferences=getSharedPreferences("User Details",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        logged_in=sharedPreferences.getBoolean("logged in",false);
        if(logged_in){
            Intent intent=new Intent(LoginActivity.this,Home.class);
            startActivity(intent);
        }
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.login_progress);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

    private boolean validate(String username,String password){
        if(username.contains("@")&&(password.length()>4)){
            return true;
        }
        return false;
    }

    private void attemptLogin(){
        username=mEmailView.getText().toString();
        password=mPasswordView.getText().toString();
        if (username==null){
            Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_LONG).show();
            return;
        }
        else if(password==null){
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            if(validate(username,password))
            {
                authenticate(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
            else{
                Toast.makeText(getApplicationContext(),"email or password incorrect",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void authenticate(final String username_args,final String password_args){

        progressBar.setVisibility(View.VISIBLE);
        String api = getApplicationContext().getString(R.string.api);
        String url="http://"+api + "/api/home";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    /*boolean alive;
                    String alive_message;
                    alive=response.getBoolean("alive");
                    alive_message=response.getString("alive_message");
                    if(!alive){
                        Toast.makeText(getApplicationContext(),alive_message,Toast.LENGTH_LONG);
                        return;

                    }
*/
                    auth_error= response.getBoolean("auth_error");
                    Log.d("stock", "api response" + response);
                    if(auth_error){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Invalid credential",Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        auth_error=false;
                        logged_in=true;
                        editor.putBoolean("logged in",true);
                        editor.putString("username",username_args) ;
                        editor.putString("password",password_args);
                        editor.apply();
                        Log.d("Log in","usename"+username_args+"password"+password_args);
                        Intent intent=new Intent(LoginActivity.this,Home.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }
}

