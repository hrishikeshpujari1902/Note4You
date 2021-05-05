package com.HrishikeshPujari.Note4you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class LoginActivity extends AppCompatActivity {
    public static final String NOTE_PREFS = "NotePrefs";
    public static final String USER_NAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";



    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    SharedPreferences mSharedPreferences;

    private String savedUsername;
    private String savedPassword;
    private String savedEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mSharedPreferences=getApplicationContext().getSharedPreferences(NOTE_PREFS,MODE_PRIVATE);
        if(mSharedPreferences!=null){
            savedEmail=mSharedPreferences.getString(EMAIL_KEY,"");
            savedPassword=mSharedPreferences.getString(PASSWORD_KEY,"");
            savedUsername=mSharedPreferences.getString(USER_NAME_KEY,"");


        }

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });



    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {

        attemptLogin();


    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.HrishikeshPujari.Note4you.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {
        String email=mEmailView.getText().toString();
        String password=mPasswordView.getText().toString();
        if(email.equals("") || password.equals("")){
            return ;
        }else{
            Toast.makeText(this,"Login in progress..",Toast.LENGTH_SHORT).show();
            if (password.equals(savedPassword) && email.equals(savedEmail)) {
                Intent intent=new Intent(LoginActivity.this,AfterLogin.class);
                finish();
                startActivity(intent);
            }else{
                showErrorDialog("There was a Problem Signing in!");
            }
        }




    }

    // TODO: Show error on screen with an alert dialog
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Ooops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }}