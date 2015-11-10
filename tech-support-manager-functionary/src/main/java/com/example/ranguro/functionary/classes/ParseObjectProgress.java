package com.example.ranguro.functionary.classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Randall on 19/09/2015.
 */
@ParseClassName("Progress")
public class ParseObjectProgress extends ParseObject {

    private final String COLUMN_PROGRESS_KEY = "objectId";
    private final String COLUMN_PROGRESS_TASK_ID = "taskID";
    private final String COLUMN_PROGRESS_DESCRIPTION = "description";
    private final String COLUMN_PROGRESS_CREATED_AT = "createdAt";
    private final String COLUMN_PROGRESS_ASSISTANT_ID = "assistantID";

    public ParseObjectProgress()
    {

    }

    public String getKey(){
        return getString(COLUMN_PROGRESS_KEY);
    }

    public ParseObjectTask getTaskID(){
        return (ParseObjectTask) getParseObject(COLUMN_PROGRESS_TASK_ID);
    }

    public String getDescription(){
        return getString(COLUMN_PROGRESS_DESCRIPTION);
    }

    public ParseUser getAssistantID(){
        return getParseUser(COLUMN_PROGRESS_ASSISTANT_ID);
    }

    public String getCreateAt(){
        return getString(COLUMN_PROGRESS_CREATED_AT);
    }

    public void setTaskID(ParseObjectTask taskID){
        this.put(COLUMN_PROGRESS_TASK_ID, taskID);
    }

    public void setDescription(String description){
        this.put(COLUMN_PROGRESS_DESCRIPTION, description);
    }

    public void setCreatedAt(Date createdAt){
        this.put(COLUMN_PROGRESS_CREATED_AT, createdAt);
    }

    public void setAssistantID(ParseUser assistantID){
        this.put(COLUMN_PROGRESS_ASSISTANT_ID, assistantID);
    }
}
