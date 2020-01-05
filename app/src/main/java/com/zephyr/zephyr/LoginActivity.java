package com.zephyr.zephyr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.zephyr.zephyrapp.R;


/**
 * Android login screen Activity
 */
//public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {
public class LoginActivity extends Activity {

    private JsonObject userLogin;
    private String userFullName;
    private String email;
    private String password;
    private Button loginAccount;
    private String webServerPOSTLoginURL;
    private String webServerGETUserProfileURL;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAccount = (Button) findViewById(R.id.signInButton);
        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Logging into account.");

                //Extract userLogin-entered information
                EditText emailET = (EditText) findViewById(R.id.email);
                EditText passwordET = (EditText) findViewById(R.id.password);

                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                if (email.equals("") || password.equals("")) {
                    //Prompt userLogin to fill out all fields
                    Toast.makeText(getBaseContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show();
                } else {
                    //Create JSON Login Object with extracted login data
                    userLogin = new JsonObject();
                    userLogin.addProperty("username", email);
                    userLogin.addProperty("password", password);
                    fetchUserTokenOnLogin(userLogin);
                }
            }
        });
    }

    //Request user token for authentication on successful login
    public void fetchUserTokenOnLogin(JsonObject userLogin) {
        webServerPOSTLoginURL = "https://zephyrapp.herokuapp.com/zephyr/api/token-auth/";
        Ion.with(getBaseContext()).load(webServerPOSTLoginURL).setJsonObjectBody(userLogin).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                userToken = result.get("token").getAsString();
                String userTokenModified = "Token " + userToken;
                fetchUserProfileViaToken(userTokenModified);
            }
        });
    }

    //Request user profile information via user token
    public void fetchUserProfileViaToken(String userTokenModified) {
        webServerGETUserProfileURL = "https://zephyrapp.herokuapp.com/zephyr/api/user_profile/";
        Ion.with(getBaseContext()).load(webServerGETUserProfileURL).setHeader("Authorization", userTokenModified).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (result.get("is_active").getAsString().equals("true")) {
                    userFullName = result.get("first_name").getAsString() + " " + result.get("last_name").getAsString();

                    //Render the profile activity after sign up
                    Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("User Token", userToken);
                    profileIntent.putExtra("Full Name", userFullName);
                    startActivity(profileIntent);
                } else {
                    Toast.makeText(getBaseContext(), "This account is no longer active.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
