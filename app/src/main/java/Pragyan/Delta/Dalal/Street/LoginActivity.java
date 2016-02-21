package Pragyan.Delta.Dalal.Street;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import Pragyan.android.volley.AuthFailureError;
import Pragyan.android.volley.DefaultRetryPolicy;
import Pragyan.android.volley.NoConnectionError;
import Pragyan.android.volley.Request;
import Pragyan.android.volley.Response;
import Pragyan.android.volley.RetryPolicy;
import Pragyan.android.volley.VolleyError;
import Pragyan.android.volley.toolbox.JsonObjectRequest;
import Pragyan.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    static Context context;
    final LoginActivity loginActivity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context=getApplicationContext();
        // Set up the login form.
        sharedPreferences=getSharedPreferences("User Details",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        logged_in=sharedPreferences.getBoolean("logged in",false);
        if(logged_in){
            Intent intent=new Intent(LoginActivity.this,Home.class);
            startActivity(intent);
            this.finish();
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
                        editor.putBoolean("logged in", true);
                        editor.putString("username",username_args) ;
                        editor.putString("password", password_args);
                        editor.apply();
                        Log.d("Log in", "usename" + username_args + "password" + password_args);
                        Intent intent=new Intent(LoginActivity.this,Home.class);
                        startActivity(intent);
                        loginActivity.finish();

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

