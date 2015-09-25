package com.example.ranguro.technicalsupportmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssetManagerDetailsActivityFragment extends Fragment {

    public static final String ASSET_DETAIL_KEY = "asset_id";

    private String assetDetailId;
    private ParseObjectAsset assetDetails;

    public AssetManagerDetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        assetDetailId = getArguments().getString(ASSET_DETAIL_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset_manager_details, container, false);
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
}
