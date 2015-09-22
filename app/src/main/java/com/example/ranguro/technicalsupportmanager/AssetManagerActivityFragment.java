package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.adapters.AssetsAdapter;
import com.example.ranguro.technicalsupportmanager.adapters.TasksAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.example.ranguro.technicalsupportmanager.decorators.DividerItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssetManagerActivityFragment extends Fragment {

    private FloatingActionButton addAssetFabView;
    private RecyclerView assetRecyclerView;
    private AssetsAdapter assetsAdapter;

    public AssetManagerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fetchAllAssets();
    }

    private void fetchAllAssets() {
        ParseQuery<ParseObjectAsset> getAllAssetsQuery = new ParseQuery<>(ParseObjectAsset.class);
        getAllAssetsQuery.findInBackground(new FindCallback<ParseObjectAsset>() {
            @Override
            public void done(List<ParseObjectAsset> list, ParseException e) {
                assetsAdapter = new AssetsAdapter(list);
                assetRecyclerView.setAdapter(assetsAdapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_asset_manager, container, false);
        assetRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_assets);
        addAssetFabView = (FloatingActionButton) rootView.findViewById(R.id.fab_add_asset);

        assetRecyclerView.setLayoutManager(new LinearLayoutManager(assetRecyclerView.getContext()));
        assetRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        assetRecyclerView.setHasFixedSize(true);
        assetRecyclerView.setItemAnimator(new DefaultItemAnimator());

        addAssetFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAssetIntent = new Intent(getActivity(), AddAssetActivity.class);
                startActivity(addAssetIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        fetchAllAssets();
        super.onResume();
    }
}
