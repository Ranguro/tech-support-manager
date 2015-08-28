package com.example.ranguro.technicalsupportmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Proyecto on 28/08/2015.
 */
public class TaskManagerActivityFragment extends Fragment {

    public TaskManagerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_manager, container, false);
    }
}