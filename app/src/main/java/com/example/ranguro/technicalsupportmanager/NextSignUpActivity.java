package com.example.ranguro.technicalsupportmanager;

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

import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class NextSignUpActivity extends AppCompatActivity {

    private final String LOG_TAG = NextSignUpActivity.class.getSimpleName();

    private EditText username;
    private EditText password;
    private boolean userExist = false;

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
        // getMenuInflater().inflate(R.menu.menu_next_sign_up, menu);
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
        if (isEmpty(username) || isEmpty(password)){
            Toast.makeText(this, "Complete all the fields", Toast.LENGTH_SHORT).show();
        }
        else{
            ParseUser user = generateNewParseUser();
            signUpNewAccount(user);
        }
    }

    private ParseUser generateNewParseUser(){

        ParseUser user = new ParseUser();
        user.put("firstName", getStringFromBundle(R.string.firstname));
        user.put("lastName", getStringFromBundle(R.string.lastname));
        user.setEmail(getStringFromBundle(R.string.email));
        user.put("phoneNumber", getStringFromBundle(R.string.phone));
        user.put("permission", getStringFromBundle(R.string.accessType));
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        return user;
    }

    private String getStringFromBundle(int id){
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        return intentBundle.getString(getString(id));
    }

    private boolean isEmpty(EditText field){
        return field.getText().toString().trim().length() == 0;
    }

    private void signUpNewAccount(ParseUser user) {
        //ParseUser user = new ParseUser();
        final ProgressDialog dlg = new ProgressDialog(NextSignUpActivity.this);
        dlg.setTitle("Please wait...");
        dlg.setMessage("Signing up. Please wait.");
        dlg.show();
        user.signUpInBackground(new SignUpCallback() {
            public void done(com.parse.ParseException e) {
                if (e != null) {
                    dlg.setMessage(e.getMessage());
                } else {
                    dlg.setMessage("Account successfully created.");
                    Intent signInIntent = new Intent(NextSignUpActivity.this, MainActivity.class);
                    signInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(signInIntent);
                }
            }
        });
        timerDelayRemoveDialog(1800, dlg);
    }

    private void timerDelayRemoveDialog(long time, final ProgressDialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
}
























