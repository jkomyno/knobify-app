/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.jk0myn0.audio.knobify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jk0myn0.audio.InitialSplash;
import com.jk0myn0.audio.R;
import com.jk0myn0.audio.controllers.VolleyController;
import com.jk0myn0.audio.helper.MyPreferenceManager;
import com.jk0myn0.audio.helper.SQLiteHandler;
import com.jk0myn0.audio.helper.Validate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputName;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private MyPreferenceManager session;
    private SQLiteHandler db;
    private String URL_LOGIN = "http://192.168.1.86:3000/api/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Button listeners
        btnLogin.setOnClickListener(this);
        btnLinkToRegister.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new MyPreferenceManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            startNextActivity();
        }
    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String name, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");
                        String name = jObj.getString("name");
                        String email = jObj.getString("email");
                        String created_at = jObj.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        showNotification(getApplicationContext());
                        // Launch main activity
                        startNextActivity();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnLinkToRegisterScreen:
                startSignUpActivity();
                break;
        }
    }

    private void login(){
        String name = inputName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        Validate validate = new Validate(getApplicationContext());
        // Check for empty data in the form
        if (Validate.isValid(inputName, inputPassword)) {
            // login user
            checkLogin(name, password);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void startSignUpActivity(){
        Intent signUp = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(signUp);
        finish();
    }

    private void startNextActivity(){
        Intent next = new Intent(getApplicationContext(), LoggedSplash.class);
        startActivity(next);
        finish();
    }

    private void showNotification(Context ctx){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        String text = "Successfully logged in as: ";
        builder
            .setSmallIcon(R.drawable.ic_menu_share)
            .setContentTitle("Knobify")
            .setContentText(text);

        builder.setStyle(new NotificationCompat.BigTextStyle());

        Intent logoutIntent = new Intent(this, InitialSplash.class);
        PendingIntent logoutPendingIntent = PendingIntent.getActivity(
                this,
                0,
                logoutIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        int myNotificationId = 001;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(myNotificationId, builder.build());
    }
}
