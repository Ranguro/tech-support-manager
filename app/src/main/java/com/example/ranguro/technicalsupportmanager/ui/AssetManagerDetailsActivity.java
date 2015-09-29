package com.example.ranguro.technicalsupportmanager.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ranguro.technicalsupportmanager.R;

public class AssetManagerDetailsActivity extends AppCompatActivity {

    public static final String ASSET_BRAND = "asset_brand";
    public static final String ASSET_MODEL = "asset_model";

    private String assetDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_manager_details);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            String detailId =  getIntent().getStringExtra(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY);

            String titleAssetBrand = getIntent().getStringExtra(ASSET_BRAND);
            String subtitleModel = getIntent().getStringExtra(ASSET_MODEL);

            ActionBar ab = getSupportActionBar();
            ab.setTitle(titleAssetBrand);
            ab.setSubtitle(subtitleModel);
            ab.setDisplayHomeAsUpEnabled(true);

            args.putString(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY,
                    detailId);
            AssetManagerDetailsActivityFragment fragment = new AssetManagerDetailsActivityFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.asset_details_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asset_manager_details, menu);
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


}
