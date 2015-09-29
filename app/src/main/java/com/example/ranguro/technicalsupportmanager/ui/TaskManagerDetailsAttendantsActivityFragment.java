package com.example.ranguro.technicalsupportmanager.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.adapters.TaskAttendantsAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectUser;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskManagerDetailsAttendantsActivityFragment extends Fragment {

    private List<ParseObjectUser> assistantsList;

    private RecyclerView taskAssistantsRecyclerView;

    public TaskManagerDetailsAttendantsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_manager_details_attendants, container, false);
    }

    public void loadFreeAssistants(){
        taskAssistantsRecyclerView.setAdapter(new TaskAttendantsAdapter(assistantsList));
    }
}
