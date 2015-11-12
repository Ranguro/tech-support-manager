package com.example.randall.assistant.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.randall.assistant.R;

public class AssetManagerDetailsActivity extends AppCompatActivity {

    public static final String ASSET_BRAND = "asset_brand";
    public static final String ASSET_MODEL = "asset_model";

    private String assetDetailId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asset_manager_details,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_manager_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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



}
