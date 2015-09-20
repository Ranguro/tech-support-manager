package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private Spinner accessType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = (EditText)findViewById(R.id.field_firstname);
        lastname = (EditText)findViewById(R.id.field_lastname);
        email = (EditText)findViewById(R.id.field_email);
        phone = (EditText)findViewById(R.id.field_phone);
        accessType = (Spinner)findViewById(R.id.field_access_type);
        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((phone.getText().length() == 8 && keyCode != 67)){
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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



    public void nextSignUp(View view) {
        String firstNameText = firstname.getText().toString();
        String lastNameText = lastname.getText().toString();
        String emailText = email.getText().toString();
        String phoneText = phone.getText().toString();
        String accessTypeText = accessType.getSelectedItem().toString();
        if (firstNameText.equals("") || lastNameText.equals("") || emailText.equals("") || phoneText.equals("")) {
            Toast.makeText(this, "Complete all the fields", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent nextSignUpIntent = new Intent(this, NextSignUpActivity.class);
            nextSignUpIntent.putExtra(getString(R.string.firstname), firstNameText);
            nextSignUpIntent.putExtra(getString(R.string.lastname), lastNameText);
            nextSignUpIntent.putExtra(getString(R.string.email), emailText);
            nextSignUpIntent.putExtra(getString(R.string.phone), phoneText);
            nextSignUpIntent.putExtra(getString(R.string.accessType), accessTypeText);
            startActivity(nextSignUpIntent);
        }

    }
}
