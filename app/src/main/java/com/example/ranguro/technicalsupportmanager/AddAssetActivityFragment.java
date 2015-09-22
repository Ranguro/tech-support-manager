package com.example.ranguro.technicalsupportmanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddAssetActivityFragment extends Fragment {

    private EditText assetNumberView;
    private EditText descriptionView;
    private EditText locationView;
    private EditText noteView;

    private Spinner statusView;
    private Spinner categoryView;
    private ArrayAdapter categoryAdapter;
    private ArrayAdapter statusAdapter;

    public AddAssetActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_add_asset, container, false);

        assetNumberView = (EditText) rootView.findViewById(R.id.field_asset_assetnumber);
        descriptionView = (EditText) rootView.findViewById(R.id.field_asset_description);
        locationView = (EditText) rootView.findViewById(R.id.field_asset_location);
        noteView = (EditText) rootView.findViewById(R.id.field_asset_note);

        statusView = (Spinner) rootView.findViewById(R.id.spinner_asset_status);
        categoryView = (Spinner) rootView.findViewById(R.id.spinner_asset_category);

        statusAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.status_array,android.R.layout.simple_list_item_1);
        statusView.setAdapter(statusAdapter);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.category_array,android.R.layout.simple_list_item_1);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return rootView;
    }
}
