package com.zephyr.zephyr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.zephyr.zephyrapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private HashMap userRecords;
    private String userToken;
    private String userFullName;
    private String webServerGETUserRecordsURL;
    private ListView ketoneTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent currentIntent = getIntent();
        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            userToken = extras.getString("User Token");
            userFullName = extras.getString("Full Name");
        }
        String userTokenModified = "Token " + userToken;

        //Customize the profile activity to match the user information
        ((TextView) findViewById(R.id.profileFullName)).setText("Welcome " + userFullName);
        fetchKetoneReadings(userTokenModified);
    }

    //Request user ketone records via user token
    public void fetchKetoneReadings(String userTokenModified) {
        webServerGETUserRecordsURL = "https://zephyrapp.herokuapp.com/zephyr/api/user_records/";
        Ion.with(getBaseContext()).load(webServerGETUserRecordsURL).setHeader("Authorization", userTokenModified).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                userRecords = new HashMap<String, String>();
                for (JsonElement dataItem : result) {
                    userRecords.put(dataItem.getAsJsonObject().get("timestamp").toString(), dataItem.getAsJsonObject().get("ketone_reading").toString());
                }
                populateUserRecords(userRecords);
            }
        });
    }

    //Populate the ketone table
    public void populateUserRecords(HashMap<String, String> userRecords) {
        ketoneTable = (ListView) findViewById(R.id.ketoneTable);
        ArrayList tempArray = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempArray);
        ketoneTable.setAdapter(arrayAdapter);

        for (Object userRecordsKey : userRecords.keySet()) {
            final String userRecordsValue = userRecords.get(userRecordsKey).toString();
            String fullData = userRecordsKey.toString() + " " + userRecordsValue;
            arrayAdapter.add(fullData);

            ketoneTable.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    String value = (String)parent.getItemAtPosition(position);

                    String[] parts = value.split(" ");

                    System.out.println("value = " + parts[1]);

                    if (Integer.parseInt(parts[1]) < 3) {
                                Toast.makeText(ProfileActivity.this, "Ketone Level Normal", Toast.LENGTH_SHORT).show();
                            } else {
                                if (Integer.parseInt(parts[1]) > 7) {
                                    Toast.makeText(ProfileActivity.this, "Ketone Level High-Seek Medical Attention Immediately", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (Integer.parseInt(parts[1]) > 3 && Integer.parseInt(parts[1]) < 7) {
                                        Toast.makeText(ProfileActivity.this, "Ketone Level Moderate", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                    }
                });
            }
            System.out.println("tempArray " + tempArray );
        }
    }
