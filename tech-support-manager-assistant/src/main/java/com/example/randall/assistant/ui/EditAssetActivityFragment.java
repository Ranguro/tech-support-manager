package com.example.randall.assistant.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.randall.assistant.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditAssetActivityFragment extends Fragment {

    public EditAssetActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_asset, container, false);
    }
}
