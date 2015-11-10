package com.example.ranguro.functionary.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.ranguro.functionary.R;
import com.example.ranguro.functionary.adapters.AssetsAdapter;
import com.example.ranguro.functionary.classes.ParseObjectAsset;
import com.example.ranguro.functionary.decorators.DividerItemDecoration;
import com.example.ranguro.functionary.swipe_helper.SimpleItemTouchHelperCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssetManagerActivityFragment extends Fragment implements AssetsAdapter.ClickListener {

    private FloatingActionButton addAssetFabView;
    private RecyclerView assetRecyclerView;
    private AssetsAdapter assetsAdapter;
    private SearchView searchAssetView;
    private List<ParseObjectAsset> assetsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemTouchHelper itemTouchHelper;

    public AssetManagerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fetchAllAssets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

          if (id == R.id.action_settings) {
            Intent editAssetIntent = new Intent(getActivity().getApplicationContext(), EditTaskActivity.class);
            startActivity(editAssetIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchAllAssets() {
        ParseQuery<ParseObjectAsset> getAllAssetsQuery = new ParseQuery<>(ParseObjectAsset.class);
        assetsAdapter = new AssetsAdapter();
        assetsAdapter.setClickListener(this);
        getAllAssetsQuery.findInBackground(new FindCallback<ParseObjectAsset>() {
            @Override
            public void done(List<ParseObjectAsset> list, ParseException e) {
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(assetsAdapter);
                itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(assetRecyclerView);
                assetsAdapter.addAll(list);
                assetsList = list;
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
        searchAssetView = (SearchView) rootView.findViewById(R.id.searchview_assets);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshlayout_assets);

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
        searchAssetView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ParseObjectAsset> queryList = getAssetsWhereMatchesWith(query);
                assetsAdapter.addAll(queryList);
                assetRecyclerView.setAdapter(assetsAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<ParseObjectAsset> queryList = getAssetsWhereMatchesWith(query);
                assetsAdapter.addAll(queryList);
                assetRecyclerView.setAdapter(assetsAdapter);
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAssetsList();
            }
        });

        return rootView;
    }



    private ArrayList<ParseObjectAsset> getAssetsWhereMatchesWith(String query) {
        ArrayList<ParseObjectAsset> queryResultList = new ArrayList<>();
        for (ParseObjectAsset asset : assetsList){
            if(isEqualInLowerCase(asset.getAssetNumber(), query) ||
                    isEqualInLowerCase(asset.getCategory(), query) ||
                    isEqualInLowerCase(asset.getLocation(), query))
            {
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

    private void refreshAssetsList() {
        fetchAllAssets();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAssetSelected(View view, ParseObjectAsset asset, int position) {
        Context context = view.getContext();
        Intent intent = new Intent(context,AssetManagerDetailsActivity.class);
        intent.putExtra(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY, asset.getObjectId());
        intent.putExtra(AssetManagerDetailsActivity.ASSET_BRAND, asset.getBrand());
        intent.putExtra(AssetManagerDetailsActivity.ASSET_MODEL, asset.getModel());
        startActivity(intent);
    }
}
