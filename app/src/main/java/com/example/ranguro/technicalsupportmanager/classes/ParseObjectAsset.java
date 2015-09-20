package com.example.ranguro.technicalsupportmanager.classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;


/**
 * Created by Randall on 19/09/2015.
 */
@ParseClassName("Asset")
public class ParseObjectAsset extends ParseObject{

    private final String COLUMN_ASSET_KEY = "objectId";
    private final String COLUMN_ASSET_ASSET_NUMBER = "assetNumber";
    private final String COLUMN_ASSET_CATEGORY = "category";
    private final String COLUMN_ASSET_DESCRIPTION = "description";
    private final String COLUMN_ASSET_LOCATION = "location";
    private final String COLUMN_ASSET_STATUS = "status";
    private final String COLUMN_ASSET_NOTE = "note";

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


}
