package com.example.ranguro.technicalsupportmanager.classes;

import android.location.Location;

import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("Asset")
public class ParseObjectAsset extends ParseObject{

    public static String COLUMN_ASSET_KEY = "objectId";
    public static String COLUMN_ASSET_ASSET_NUMBER = "assetNumber";
    public static String COLUMN_ASSET_CATEGORY = "category";
    public static String COLUMN_ASSET_DESCRIPTION = "description";
    public static String COLUMN_ASSET_LOCATION = "location";
    public static String COLUMN_ASSET_STATUS = "status";
    public static String COLUMN_ASSET_NOTE = "note";

    public ParseObjectAsset()
    {

    }

    public String getKey(){
        return getString(COLUMN_ASSET_KEY);
    }

    public String getAssetNumber(){
        return getString(COLUMN_ASSET_ASSET_NUMBER);
    }

    public String getCategory(){
        return getString(COLUMN_ASSET_CATEGORY);
    }

    public String getDescription(){
        return getString(COLUMN_ASSET_DESCRIPTION);
    }

    public String getLocation(){
        return getString(COLUMN_ASSET_LOCATION);
    }

    public String getStatus(){
        return getString(COLUMN_ASSET_STATUS);
    }

    public String getNote(){
        return getString(COLUMN_ASSET_NOTE);
    }

    public void setAssetNumber(String assetNumber){
        this.put(COLUMN_ASSET_ASSET_NUMBER, assetNumber);
    }

    public void setCategory(String category){
        this.put(COLUMN_ASSET_CATEGORY, category);
    }

    public void setDescription(String description){
        this.put(COLUMN_ASSET_DESCRIPTION, description);
    }

    public void setLocation(String location){
        this.put(COLUMN_ASSET_LOCATION, location);
    }

    public void setStatus(String status){
        this.put(COLUMN_ASSET_STATUS, status);
    }

    public void setNote(String note){
        this.put(COLUMN_ASSET_NOTE, note);
    }
}
