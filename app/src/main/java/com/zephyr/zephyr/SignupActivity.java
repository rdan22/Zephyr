package com.zephyr.zephyr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zephyr.zephyrapp.R;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    public int minUserTotal;
    public int maxUserTotal;
    private int userID;
    private String fullName;
    private String email;
    private String password;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        minUserTotal = 2;
        maxUserTotal = 1000000;

        createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SignUpActivity", "Account being created.");

                //Temporary Hash Function
                userID = minUserTotal + (int) (Math.random() * maxUserTotal); //Math.random() returns a number between 0 and 1

                //Extract user-entered information
                EditText fullNameET = (EditText) findViewById(R.id.name);
                EditText emailET = (EditText) findViewById(R.id.email);
                EditText passwordET = (EditText) findViewById(R.id.password);

                fullName = fullNameET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                String[] fullNameSplit = fullName.split("\\s+");
                if (fullNameSplit.length != 2) {
                    //Prompt user to enter full name (first, last)
                    Toast.makeText(getBaseContext(), "Please enter your full name.", Toast.LENGTH_LONG).show();
                } else if (fullName.equals("") || email.equals("") || password.equals("")) {
                    //Prompt user to fill out all fields
                    Toast.makeText(getBaseContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show();
                } else {
                    //All conditions met
                    String firstName = fullNameSplit[0];
                    String lastName = fullNameSplit[1];

                    //Create JSON Object with retrieved data
                    JSONObject user = new JSONObject();
                    try {
                        user.put("UserID", userID);
                        user.put("First Name", firstName);
                        user.put("Last Name", lastName);
                        user.put("Full Name", fullName);
                        user.put("Email", email);
                        user.put("Password", password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Send JSON Object to web server (WRITE CODE HERE)

                    //Render the profile activity after sign up
                    Intent profileIntent = new Intent(SignupActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("UserID", userID);
                    profileIntent.putExtra("Full Name", fullName);
                    startActivity(profileIntent);
                }
            }
        });
    }
}
