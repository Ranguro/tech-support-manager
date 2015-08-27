package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NextSignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_sign_up);
        username = (EditText)findViewById(R.id.field_username);
        password = (EditText)findViewById(R.id.field_password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createAccount(View view) {
        //Working in the insertion
        Intent intent = getIntent();
        String firstnameText = intent.getExtras().getString(getString(R.string.firstname));
        String lastNameText = intent.getExtras().getString(getString(R.string.lastname));
        String emailText = intent.getExtras().getString(getString(R.string.email));
        String phoneText = intent.getExtras().getString(getString(R.string.phone));
        String accessTypeText = intent.getExtras().getString(getString(R.string.accessType));
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (usernameText.equals("") || passwordText.equals("")){
            Toast.makeText(this, "Complete all the fields", Toast.LENGTH_SHORT).show();
        }
        else{
            //Add account to database
            Toast.makeText(this, "Creating a fcking account", Toast.LENGTH_LONG).show();
        }
    }

    public void insertAccount(){
        //Makes the insertion in the database
    }

    }
