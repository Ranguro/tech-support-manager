package com.example.ranguro.technicalsupportmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.adapters.TasksAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.example.ranguro.technicalsupportmanager.decorators.DividerItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class TaskManagerActivityFragment extends Fragment implements TasksAdapter.clickListener {

    private static final String LOG_TAG = TaskManagerActivityFragment.class.getSimpleName();

    private FloatingActionButton addTaskFabView;
    private RecyclerView taskRecyclerView;

    public TaskManagerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchAllTasks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task_manager, container, false);
        taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_tasks);
        addTaskFabView = (FloatingActionButton) rootView.findViewById(R.id.fab_add_task);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(taskRecyclerView.getContext()));
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());



        addTaskFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskIntent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });


        return rootView;
    }

    private void fetchAllTasks(){
        ParseQuery<ParseObjectTask> getAllTaskQuery = new ParseQuery<>(ParseObjectTask.class);
        getAllTaskQuery.findInBackground(new FindCallback<ParseObjectTask>() {
            @Override
            public void done(List<ParseObjectTask> list, ParseException e) {
                taskRecyclerView.setAdapter(new TasksAdapter(list));
                Log.i("Total tasks obtained: ", String.valueOf(list.size()));
            }
        });
    }

    @Override
    public void onTaskSelected(View view, ParseObject task, int position) {


    }
}