package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class VerifyAccountActivity extends AppCompatActivity {
    public static final String TAG = "VerifyAccountActivity";

    public static final String VERIFY_ACCOUNT_EMAIL_TAG = "Verify_Account_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        Toolbar toolbar = findViewById(R.id.verificationToolBar);
        setSupportActionBar(toolbar);
        setTitle("Verification");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent callingIntent= getIntent();
        String email = callingIntent.getStringExtra(SignUpActivity.SIGNUP_EMAIL_TAG);
        TextView usernameTextview = (TextView) findViewById(R.id.textViewUsername);
        usernameTextview.setText(email);
        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
            String username = usernameTextview.getText().toString();
            String verificationCode = ((EditText)findViewById(R.id.editTextVerificationCode)).getText().toString();

            Amplify.Auth.confirmSignUp(username,verificationCode,
                    success -> {
                        Log.i(TAG,"verification succeeded: "+ success.toString());
                        Intent goToLoginIntent = new Intent(VerifyAccountActivity.this, LoginActivity.class);
                        goToLoginIntent.putExtra(VERIFY_ACCOUNT_EMAIL_TAG, username);
                        startActivity(goToLoginIntent);
                    },
                    fail -> {
                        Log.i(TAG,"verification failed: "+ fail.toString());
                        runOnUiThread(() ->
                        {
                            Toast.makeText(VerifyAccountActivity.this, " Verify account failed!!", Toast.LENGTH_LONG);
                        });
                    });
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