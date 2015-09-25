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
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditAssetActivityFragment extends Fragment {

    private ParseObjectAsset selectedAsset;
    private EditText assetNumberView;
    private EditText descriptionView;
    private EditText locationView;
    private EditText noteView;

    private Spinner statusView;
    private Spinner categoryView;
    private ArrayAdapter categoryAdapter;
    private ArrayAdapter statusAdapter;

    public EditAssetActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_edit_asset, container, false);

        assetNumberView = (EditText) rootView.findViewById(R.id.field_edit_asset_assetnumber);
        descriptionView = (EditText) rootView.findViewById(R.id.field_edit_asset_description);
        locationView = (EditText) rootView.findViewById(R.id.field_edit_asset_location);
        noteView = (EditText) rootView.findViewById(R.id.field_edit_asset_note);

        statusView = (Spinner) rootView.findViewById(R.id.spinner_edit_asset_status);
        categoryView = (Spinner) rootView.findViewById(R.id.spinner_edit_asset_category);

        statusAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.status_array,android.R.layout.simple_list_item_1);
        statusView.setAdapter(statusAdapter);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.category_array,android.R.layout.simple_list_item_1);
        categoryView.setAdapter(categoryAdapter);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //get asset from details activity CHANGE FOR BUNDLE ARGUMENT FROM FATHER
        String assetID = "nDAGfQNTt0";
        ParseQuery<ParseObjectAsset> getAssetByIDQuery = new ParseQuery<>(ParseObjectAsset.class);
        try{
            selectedAsset = getAssetByIDQuery.get(assetID);
            assetNumberView.setText(selectedAsset.getAssetNumber());
            descriptionView.setText(selectedAsset.getDescription());
            locationView.setText(selectedAsset.getLocation());
            noteView.setText(selectedAsset.getNote());
            categoryView.setSelection(categoryAdapter.getPosition(selectedAsset.getCategory()));
            statusView.setSelection(statusAdapter.getPosition(selectedAsset.getStatus()));
        }catch (ParseException e){

        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_task_option){
            if(isFieldEmpty(assetNumberView)){
                Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_asset_empty_field_msg, Toast.LENGTH_SHORT).show();
            }
            else {
                try{
                    editAsset();
                    Intent assetManagerActivity = new Intent(getActivity().getApplication(), AssetManagerActivity.class);
                    startActivity(assetManagerActivity);
                    Toast.makeText(getActivity().getApplication(), R.string.toast_success_asset_modification_msg, Toast.LENGTH_LONG).show();
                }
                catch (ParseException pe){

                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void editAsset() throws ParseException {
        String assetNumber = assetNumberView.getText().toString();
        String description = descriptionView.getText().toString();
        String location = locationView.getText().toString();
        String note = noteView.getText().toString();
        String status = statusView.getSelectedItem().toString();
        String category = categoryView.getSelectedItem().toString();
        selectedAsset.setAssetNumber(assetNumber);
        selectedAsset.setDescription(description);
        selectedAsset.setLocation(location);
        selectedAsset.setNote(note);
        selectedAsset.setStatus(status);
        selectedAsset.setCategory(category);
        selectedAsset.save();
    }

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().trim().length() == 0;
    }

}
