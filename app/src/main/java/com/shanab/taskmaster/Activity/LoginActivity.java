package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.loginToolBar);
        setSupportActionBar(toolbar);
        setTitle("Login");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent callingIntent= getIntent();
        String email = callingIntent.getStringExtra(VerifyAccountActivity.VERIFY_ACCOUNT_EMAIL_TAG);
        EditText usernameEditText = (EditText) findViewById(R.id.loginUserNameEditText);
        usernameEditText.setText(email);

        findViewById(R.id.buttonLogin).setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

            Amplify.Auth.signIn(username,password,success ->{
                Log.i(TAG, "Login succeeded: "+success.toString());
                Intent goToProductListIntent= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goToProductListIntent);
            },fail -> {
                Log.i(TAG, "Login failed: "+fail.toString());
                runOnUiThread(() ->
                {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG);
                });
            });
        });
        findViewById(R.id.signupbutton).setOnClickListener(v -> {
            Intent goToSignUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(goToSignUpIntent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}