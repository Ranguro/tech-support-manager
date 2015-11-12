package com.example.randall.assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.randall.assistant.R;
import com.example.randall.assistant.adapters.AssetsAdapter;
import com.example.randall.assistant.classes.ParseObjectAsset;
import com.example.randall.assistant.decorators.DividerItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class AssetManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AssetsAdapter.ClickListener{

    private RecyclerView assetRecyclerView;
    private AssetsAdapter assetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        assetRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_assets);
        assetRecyclerView.setLayoutManager(new LinearLayoutManager(assetRecyclerView.getContext()));
        assetRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        assetRecyclerView.setHasFixedSize(true);
        assetRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTaskIntent = new Intent(getApplicationContext(), AddAssetActivity.class);
                startActivity(addTaskIntent);

            }
        });

        fetchAllAssets();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.asset_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task_manager) {
            Intent assetManagerIntent = new Intent(this,TaskManagerActivity.class);
            startActivity(assetManagerIntent);
        } else if (id == R.id.nav_asset_manager) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAssetSelected(View view, ParseObjectAsset asset, int position) {
        Toast.makeText(AssetManagerActivity.this, "This will launch an asset detail", Toast.LENGTH_SHORT).show();
    }

    private void fetchAllAssets() {
        ParseQuery<ParseObjectAsset> getAllAssetsQuery = new ParseQuery<>(ParseObjectAsset.class);
        assetsAdapter = new AssetsAdapter();
        assetsAdapter.setClickListener(this);
        getAllAssetsQuery.findInBackground(new FindCallback<ParseObjectAsset>() {
            @Override
            public void done(List<ParseObjectAsset> list, ParseException e) {
                assetsAdapter.addAll(list);
                assetRecyclerView.setAdapter(assetsAdapter);
            }
        });
    }
}
