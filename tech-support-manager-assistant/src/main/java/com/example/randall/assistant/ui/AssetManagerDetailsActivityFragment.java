package com.example.randall.assistant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.randall.assistant.R;
import com.example.randall.assistant.classes.ParseObjectAsset;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssetManagerDetailsActivityFragment extends Fragment {

    public static final String ASSET_DETAIL_KEY = "asset_id";

    private String assetDetailId;
    private ParseObjectAsset assetDetails;
    private TextView descriptionView;
    private TextView locationView;
    private TextView assetNumberView;
    private TextView categoryView;

    public AssetManagerDetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        assetDetailId = getArguments().getString(ASSET_DETAIL_KEY);
        fetchAssetDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_asset_manager_details, container, false);

        descriptionView =  (TextView) rootView.findViewById(R.id.asset_detail_description);
        locationView =  (TextView) rootView.findViewById(R.id.asset_detail_location);
        assetNumberView =  (TextView) rootView.findViewById(R.id.asset_detail_number);
        categoryView =   (TextView) rootView.findViewById(R.id.asset_detail_category);


        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", ParseUser.getCurrentUser().getUsername());
        ParseQuery pQuery = ParseInstallation.getQuery();

        String usuario = ParseUser.getCurrentUser().getUsername();
        pQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        ParsePush.sendMessageInBackground("Only for special people", pQuery);

        return rootView;
    }

    private void loadDetailsOnView() {

        assetNumberView.setText(assetDetails.getAssetNumber());
        descriptionView.setText(assetDetails.getDescription());
        categoryView.setText(assetDetails.getCategory());
        locationView.setText(assetDetails.getLocation());

    }


    public void fetchAssetDetails(){
        ParseQuery<ParseObjectAsset> query = ParseQuery.getQuery(ParseObjectAsset.class);
        query.getInBackground(assetDetailId, new GetCallback<ParseObjectAsset>() {
            @Override
            public void done(ParseObjectAsset parseObjectTask, ParseException e) {
                assetDetails = parseObjectTask;
                loadDetailsOnView();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_asset){
            Context context = getActivity().getApplicationContext();
            Intent intent = new Intent(context,EditAssetActivity.class);
            intent.putExtra(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY, assetDetailId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}


