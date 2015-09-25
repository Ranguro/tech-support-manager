package com.example.ranguro.technicalsupportmanager;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ranguro.technicalsupportmanager.adapters.TasksAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Randall on 22/09/2015.
 */
public class TaskManagerActivityFragment extends Fragment implements TasksAdapter.ClickListener {

    private static final String LOG_TAG = TaskManagerActivityFragment.class.getSimpleName();

    private FloatingActionButton addTaskFabView;
    private RecyclerView taskRecyclerView;
    private TasksAdapter tasksAdapter;

    public TaskManagerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fetchAllTasks();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task_manager, container, false);
        taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_tasks);
        addTaskFabView = (FloatingActionButton) rootView.findViewById(R.id.fab_add_task);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(taskRecyclerView.getContext()));
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


    @Override
    public void onTaskSelected(View view, ParseObject task, int position) {

        Context context = view.getContext();
        Intent intent = new Intent(context,TaskManagerDetailsActivity.class);
        intent.putExtra(TaskManagerDetailsActivityFragment.TASK_DETAIL_KEY, task.getObjectId());
        startActivity(intent);
    }

    private void fetchAllTasks(){
        ParseQuery<ParseObjectTask> getAllTaskQuery = new ParseQuery<>(ParseObjectTask.class);
        tasksAdapter = new TasksAdapter();
        tasksAdapter.setClickListener(this);
        getAllTaskQuery.findInBackground(new FindCallback<ParseObjectTask>() {
            @Override
            public void done(List<ParseObjectTask> taskList, ParseException e) {
                tasksAdapter.addAll(taskList);
                taskRecyclerView.setAdapter(tasksAdapter);
                Log.i("Total tasks obtained: ", String.valueOf(taskList.size()));
            }
        });
    }
}
