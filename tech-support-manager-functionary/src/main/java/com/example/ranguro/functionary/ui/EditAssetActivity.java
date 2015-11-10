package com.example.ranguro.functionary.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ranguro.functionary.R;

public class EditAssetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_asset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
