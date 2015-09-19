package com.example.ranguro.technicalsupportmanager.classes;

/**
 * Created by Proyecto on 31/08/2015.
 */
public class Users {
    public int id;
    public String FirstName;
    public String LastName;
    public String Email;
    public String PhoneNumber;
    public String Permission;
    public String Username;
    public String Password;

    public Users(){}

    public Users(int id, String firstName, String lastName, String email, String permission, String phoneNumber, String username, String password) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Permission = permission;
        PhoneNumber = phoneNumber;
        Username = username;
        Password = password;
    }

    public void createNewAccount() {

    }
}
