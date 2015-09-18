package com.example.ranguro.technicalsupportmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.xml.sax.Parser;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "SIWiSHubCFLzS5Pub6ll75vpGivZ1Eg4mgpePp6G", "oC9umnr8EQLRp12Puso4Idditm4oSAQAJyg0NG2i");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void signIn(View view) {
        if(isEmpty(username) || isEmpty(password)){
            Toast.makeText(this, "Complete all the fields", Toast.LENGTH_SHORT).show();
        }
        else{
            verifyAccount(username.getText().toString(), password.getText().toString());
        }
    }

    public boolean isEmpty(EditText field){
        return field.getText().toString().trim().length() == 0;
    }

    public void verifyAccount(String usernameText, final String passwordText){
        final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
        dlg.setTitle("Please wait...");
        dlg.setMessage("Signing in. Please wait.");
        dlg.show();
        ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                password.setText("");
                if (e != null) {
                    dlg.setMessage(e.getMessage());
                } else {
                    username.setText("");
                    Intent taskManagerIntent = new Intent(MainActivity.this, TaskManagerActivity.class);
                    startActivity(taskManagerIntent);
                    dlg.setMessage("Login successful");
                }
                timerDelayRemoveDialog(1800, dlg);
            }
        });
    }

    public void timerDelayRemoveDialog(long time, final ProgressDialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void signUp(View view) {
        Intent signUpIntent = new Intent(this,SignUpActivity.class);
        startActivity(signUpIntent);
    }
}
