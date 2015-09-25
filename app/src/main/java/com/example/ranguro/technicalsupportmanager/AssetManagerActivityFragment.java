package com.example.ranguro.technicalsupportmanager;

import android.app.SearchableInfo;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.ranguro.technicalsupportmanager.adapters.AssetsAdapter;
import com.example.ranguro.technicalsupportmanager.adapters.TasksAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.example.ranguro.technicalsupportmanager.decorators.DividerItemDecoration;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssetManagerActivityFragment extends Fragment {

    private FloatingActionButton addAssetFabView;
    private RecyclerView assetRecyclerView;
    private AssetsAdapter assetsAdapter;
    private SearchView searchAssetView;
    private List<ParseObjectAsset> assetsList;

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
                assetsList = list;
                assetsAdapter = new AssetsAdapter(assetsList);
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
        searchAssetView = (SearchView) rootView.findViewById(R.id.searchview_assets);
        searchAssetView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ParseObjectAsset> queryList = getAssetsWhereMatchesWith(query);
                assetsAdapter = new AssetsAdapter(queryList);
                assetRecyclerView.setAdapter(assetsAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<ParseObjectAsset> queryList = getAssetsWhereMatchesWith(query);
                assetsAdapter = new AssetsAdapter(queryList);
                assetRecyclerView.setAdapter(assetsAdapter);
                return false;
            }
        });

        return rootView;
    }

    private ArrayList<ParseObjectAsset> getAssetsWhereMatchesWith(String query) {
        ArrayList<ParseObjectAsset> queryResultList = new ArrayList<>();
        for (ParseObjectAsset asset : assetsList){
            if(isEqualInLowerCase(asset.getAssetNumber(), query) || isEqualInLowerCase(asset.getCategory(), query) || isEqualInLowerCase(asset.getLocation(), query)){
                queryResultList.add(asset);
            }
        }
        return queryResultList;
    }

    private boolean isEqualInLowerCase(String firstText, String secondText){
        return firstText.toLowerCase().contains(secondText.toLowerCase());
    }

    @Override
    public void onResume() {
        fetchAllAssets();
        super.onResume();
    }


}
