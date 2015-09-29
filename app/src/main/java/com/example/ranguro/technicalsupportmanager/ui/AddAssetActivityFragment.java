package com.example.ranguro.technicalsupportmanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.parse.SaveCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddAssetActivityFragment extends Fragment {

    private EditText assetNumberView;
    private EditText descriptionView;
    private EditText locationView;
    private EditText noteView;
    private EditText modelView;
    private EditText brandView;

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
        modelView = (EditText) rootView.findViewById(R.id.field_asset_model);
        brandView = (EditText) rootView.findViewById(R.id.field_asset_brand);
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
        String model = modelView.getText().toString();
        String brand = modelView.getText().toString();
        String description = descriptionView.getText().toString();
        String location = locationView.getText().toString();
        String note = noteView.getText().toString();
        String status = statusView.getSelectedItem().toString();
        String category = categoryView.getSelectedItem().toString();

        newAsset.setAssetNumber(assetNumber);
        newAsset.setModel(model);
        newAsset.setBrand(brand);
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
                resetViewFields();
            }
            }
        });
    }

    private void resetViewFields() {
        assetNumberView.setText("");
        descriptionView.setText("");
        modelView.setText("");
        brandView.setText("");
        locationView.setText("");
        noteView.setText("");
    }

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().trim().length() == 0;
    }
}
