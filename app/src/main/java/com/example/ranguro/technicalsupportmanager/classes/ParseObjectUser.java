package com.example.ranguro.technicalsupportmanager.classes;

import com.parse.ParseClassName;
import com.parse.ParseUser;


@ParseClassName("_User")
public class ParseObjectUser extends ParseUser {

    public final static String COLUMN_USER_KEY = "objectId";
    public final static String COLUMN_USER_FIRSTNAME= "firstName";
    public final static String COLUMN_USER_PERMISSION = "permission";
    public final static String COLUMN_USER_LASTNAME= "lastName";
    public final static String COLUMN_USER_PHONENUMBER= "phoneNumber";
    public final static String COLUMN_USER_USERNAME= "username";
    public final static String COLUMN_USER_PASSWORD= "password";
    public final static String COLUMN_USER_EMAIL= "email";


    public ParseObjectUser()
    {

    }

    public String getKey(){
        return getString(COLUMN_USER_KEY);
    }

    public String getUsername(){
        return getString(COLUMN_USER_USERNAME);
    }

    public String getFirstName(){
        return getString(COLUMN_USER_FIRSTNAME);
    }

    public String getLastName(){
        return getString(COLUMN_USER_LASTNAME);
    }

    public String getEmail(){
        return getString(COLUMN_USER_EMAIL);
    }

    public String getPassword(){
        return getString(COLUMN_USER_PASSWORD);
    }

    public String getPhoneNumber(){
        return getString(COLUMN_USER_PHONENUMBER);
    }

    public String getPermission(){
        return getString(COLUMN_USER_PERMISSION);
    }


}
