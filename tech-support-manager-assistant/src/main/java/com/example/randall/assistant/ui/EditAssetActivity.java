package com.example.randall.assistant.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.randall.assistant.R;

public class EditAssetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            String detailId =  getIntent().getStringExtra(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY);

            args.putString(AssetManagerDetailsActivityFragment.ASSET_DETAIL_KEY,
                    detailId);
            EditAssetActivityFragment fragment = new EditAssetActivityFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.asset_edit_container, fragment)
                    .commit();
        }
    }

}
