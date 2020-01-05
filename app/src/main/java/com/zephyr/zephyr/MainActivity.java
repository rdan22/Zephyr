package com.zephyr.zephyr;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.zephyr.zephyrapp.R;

public class MainActivity extends AppCompatActivity {

    private View loginFormView;
    private View progressView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
//        passwordTextView = (EditText) findViewById(R.id.password);
//        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_NULL) {
//                    return true;
//                }
//                return false;
//            }
//        });

        //Login Button logic
        loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Sign Up Activity activated.");
                MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

        //Sign Up Button logic
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setPaintFlags(signUpButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SignUpActivity", "Sign Up Activity activated.");
                MainActivity.this.startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
    }
}
