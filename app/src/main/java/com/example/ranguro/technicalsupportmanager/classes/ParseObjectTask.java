package com.example.ranguro.technicalsupportmanager.classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

/**
 * Created by Randall on 03/09/2015.
 */
@ParseClassName("Task")
public class ParseObjectTask extends ParseObject{

    public static String COLUMN_TASK_KEY = "objectId";
    public static String COLUMN_TASK_TITLE = "title";
    public static String COLUMN_TASK_DESCRIPTION = "description";
    public static String COLUMN_TASK_CREATED_AT = "createdAt";
    public static String COLUMN_TASK_DEADLINE = "deadline";
    public static String COLUMN_TASK_CREATOR_ID = "creatorID";
    public static String COLUMN_TASK_PRIORITY = "priority";
    public static String COLUMN_TASK_STATUS  = "status";
    public static String COLUMN_TASK_ATTENDATS = "attendats";

    public ParseObjectTask() {

    }

    public List<String> getAttendants(){
        return getList(COLUMN_TASK_ATTENDATS);
    }

    public String getStatus(){
        return getString(COLUMN_TASK_STATUS);
    }

    public ParseUser getCreatorID(){
        return getParseUser(COLUMN_TASK_CREATOR_ID);
    }

    public String getPriority(){
        return getString(COLUMN_TASK_PRIORITY);
    }

    public Date getCreatedAt(){
        return getDate(COLUMN_TASK_CREATED_AT);
    }


    public Date getDeadline(){
        return getDate(COLUMN_TASK_DEADLINE);
    }

    public String getDescription(){
        return getString(COLUMN_TASK_DESCRIPTION);
    }

    public String getTitle(){
        return getString(COLUMN_TASK_TITLE);
    }

    public String getKey(){
        return getString(COLUMN_TASK_KEY);
    }

    public void setTitle(String title){
        this.put(COLUMN_TASK_TITLE, title);
    }

    public void setDescription(String description){
        this.put(COLUMN_TASK_DESCRIPTION, description);
    }

    public void setDeadline(Date date){
        this.put(COLUMN_TASK_DEADLINE, date);
    }

    public void setPriority(String priority){
        this.put(COLUMN_TASK_PRIORITY, priority);
    }

    public void setCreatorId(ParseUser creator){
        this.put(COLUMN_TASK_CREATOR_ID, creator);
    }

    public void setStatus(String status) {
        this.put(COLUMN_TASK_STATUS, status);
    }

    public void setAttendant(String attendant){
        this.getAttendants().add(attendant);
    }
}
