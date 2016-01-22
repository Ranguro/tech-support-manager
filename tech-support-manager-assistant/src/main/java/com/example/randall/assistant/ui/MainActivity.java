package com.example.randall.assistant.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.randall.assistant.R;
import com.example.randall.assistant.classes.ParseObjectAsset;
import com.example.randall.assistant.classes.ParseObjectProgress;
import com.example.randall.assistant.classes.ParseObjectTask;
import com.example.randall.assistant.classes.ParseObjectUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

        //Default password: jonathan

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(ParseObjectTask.class);
        ParseObject.registerSubclass(ParseObjectProgress.class);
        ParseObject.registerSubclass(ParseObjectUser.class);
        ParseObject.registerSubclass(ParseObjectAsset.class);

        Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

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

    private boolean isEmpty(EditText field){
        return field.getText().toString().trim().length() == 0;
    }

    private void verifyAccount(String usernameText, String passwordText){
        usernameText = usernameText.trim();
        final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
        dlg.setTitle(R.string.toast_info_login_title);
        dlg.setMessage(getString(R.string.toast_info_login_message));
        dlg.show();
        ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                password.setText("");
                if (e != null) {
                    dlg.setMessage(e.getMessage());
                } else {
                    dlg.setMessage(getString(R.string.toast_info_login_success));
                    username.setText("");
                    Intent taskManagerIntent = new Intent(MainActivity.this, TaskManagerActivity.class);
                    startActivity(taskManagerIntent);
                }
                timerDelayRemoveDialog(dlg);
            }
        });
    }

    private void timerDelayRemoveDialog(final ProgressDialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, (long) 1000);
    }

    //find better way to kill application
    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

}
