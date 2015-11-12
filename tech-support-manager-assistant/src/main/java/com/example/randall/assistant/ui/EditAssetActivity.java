package com.example.randall.assistant.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.randall.assistant.R;

public class EditAssetActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_asset,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
