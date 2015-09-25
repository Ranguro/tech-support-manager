package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_asset){
            if(isFieldEmpty(assetNumberView)){
                Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_asset_empty_field_msg, Toast.LENGTH_SHORT).show();
            }
            else {
                addNewAsset();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewAsset() {
        final ParseObjectAsset newAsset = new ParseObjectAsset();
        String assetNumber = assetNumberView.getText().toString();
        String description = descriptionView.getText().toString();
        String location = locationView.getText().toString();
        String note = noteView.getText().toString();
        String status = statusView.getSelectedItem().toString();
        String category = categoryView.getSelectedItem().toString();

        newAsset.setAssetNumber(assetNumber);
        newAsset.setCategory(category);
        newAsset.setDescription(description);
        newAsset.setLocation(location);
        newAsset.setStatus(status);
        newAsset.setNote(note);
        newAsset.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_success_asset_insertion_msg, Toast.LENGTH_SHORT).show();
                    assetNumberView.setText("");
                    descriptionView.setText("");
                    locationView.setText("");
                    noteView.setText("");
                }
            }
        });
    }

    public boolean areFieldsEmpties(){
        return isFieldEmpty(assetNumberView);
    }

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().trim().length() == 0;
    }
}
