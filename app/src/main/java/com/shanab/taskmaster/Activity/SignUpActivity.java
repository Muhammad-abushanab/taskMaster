package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";

    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.signUpToolBar);
        setSupportActionBar(toolbar);
        setTitle("Sign up");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.buttonRegister).setOnClickListener(v -> {
            String username = ((EditText) findViewById(R.id.loginUserNameEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();


            Amplify.Auth.signUp(username, password, AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), email).build(),
                    result ->
                    {
                        Log.i(TAG, "Result:" + result.toString());
                        Intent goToVerifyIntent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
                        goToVerifyIntent.putExtra(SIGNUP_EMAIL_TAG, username);
                        startActivity(goToVerifyIntent);
                    },
                    error ->
                    {
                        Log.i(TAG, "Sign up failed", error);
                        runOnUiThread(() ->
                        {
                            Toast.makeText(SignUpActivity.this, "Signup failed", Toast.LENGTH_LONG);
                        });
                    });
        });
    }
}