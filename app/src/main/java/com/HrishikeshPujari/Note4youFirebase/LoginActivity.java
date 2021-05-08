package com.HrishikeshPujari.Note4youFirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    public static final String APP_TAG = "Note4You";

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

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


        mAuth=FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {

        attemptLogin();


    }


    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.HrishikeshPujari.Note4youFirebase.RegisterActivity.class);
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
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(APP_TAG,"Signin() complete"+task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d(APP_TAG,"Problem Signing in :"+task.getException());
                    showErrorDialog("There was a Problem Signing in!");
                }else{
                    FirebaseUser user =mAuth.getCurrentUser();
                    String userUID=user.getUid();
                    Intent intent=new Intent(LoginActivity.this,AfterLogin.class);
                    intent.putExtra("uid",userUID);
                    Log.d("Note4You","uid:"+userUID);
                    finish();
                    startActivity(intent);
                }
            }
        });



    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Ooops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }}