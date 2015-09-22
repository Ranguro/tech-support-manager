package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.adapters.AssetsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskManagerDetailsActivityFragment extends Fragment {

    public TaskManagerDetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_manager_details, container, false);
    }


}
