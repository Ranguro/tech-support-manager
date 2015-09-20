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

    private final String COLUMN_TASK_KEY = "objectId";
    private final String COLUMN_TASK_TITLE = "title";
    private final String COLUMN_TASK_DESCRIPTION = "description";
    private final String COLUMN_TASK_CREATED_AT = "createdAt";
    private final String COLUMN_TASK_DEADLINE = "deadline";
    private final String COLUMN_TASK_CREATOR_ID = "creatorID";
    private final String COLUMN_TASK_PRIORITY = "priority";
    private final String COLUMN_TASK_STATUS  = "status";
    private final String COLUMN_TASK_ATTENDATS = "attendats";

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



}
