package com.example.ranguro.functionary.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.ranguro.functionary.R;
import com.example.ranguro.functionary.adapters.TasksAdapter;
import com.example.ranguro.functionary.classes.ParseObjectTask;
import com.example.ranguro.functionary.decorators.DividerItemDecoration;
import com.example.ranguro.functionary.swipe_helper.SimpleItemTouchHelperCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randall on 22/09/2015.
 */
public class TaskManagerActivityFragment extends Fragment implements TasksAdapter.ClickListener {

    private static final String LOG_TAG = TaskManagerActivityFragment.class.getSimpleName();

    private FloatingActionButton addTaskFabView;
    private RecyclerView taskRecyclerView;
    private TasksAdapter tasksAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ParseObjectTask> tasksList;
    private SearchView searchTaskView;
    private ItemTouchHelper itemTouchHelper;

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
        if (item.getItemId() == R.id.action_assset_manager) {
            Intent assetManagerActivity = new Intent(getActivity().getApplication(), AssetManagerActivity.class);
            startActivity(assetManagerActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task_manager, container, false);
        taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_tasks);
        addTaskFabView = (FloatingActionButton) rootView.findViewById(R.id.fab_add_task);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshlayout_tasks);
        searchTaskView = (SearchView) rootView.findViewById(R.id.searchview_tasks);

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAssetsList();
            }
        });

        searchTaskView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<ParseObjectTask> queryList = getAssetsWhereMatchesWith(query);
                tasksAdapter.addAll(queryList);
                taskRecyclerView.setAdapter(tasksAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<ParseObjectTask> queryList = getAssetsWhereMatchesWith(query);
                tasksAdapter.addAll(queryList);
                taskRecyclerView.setAdapter(tasksAdapter);
                return false;
            }
        });
        return rootView;
    }

    private ArrayList<ParseObjectTask> getAssetsWhereMatchesWith(String query) {
        ArrayList<ParseObjectTask> queryResultList = new ArrayList<>();
        for (ParseObjectTask task : tasksList){
            if(isEqualInLowerCase(task.getTitle(), query) ||
                    isEqualInLowerCase(task.getStatus(), query) ||
                    isEqualInLowerCase(task.getDescription(), query))
            {
                queryResultList.add(task);
            }
        }
        return queryResultList;
    }

    private boolean isEqualInLowerCase(String firstText, String secondText){
        return firstText.toLowerCase().contains(secondText.toLowerCase());
    }


    @Override
    public void onTaskSelected(View view, ParseObjectTask task, int position) {

        Context context = view.getContext();
        Intent intent = new Intent(context,TaskManagerDetailsActivity.class);
        intent.putExtra(TaskManagerDetailsActivityFragment.TASK_DETAIL_KEY, task.getObjectId());
        String fullName = task.getCreatorID().getFirstName() + " "+task.getCreatorID().getLastName();
        intent.putExtra(TaskManagerDetailsActivity.TASK_TITLE, task.getTitle());
        intent.putExtra(TaskManagerDetailsActivity.TASK_CREATED_BY, fullName);
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
                tasksList = taskList;
                taskRecyclerView.getAdapter();
                taskRecyclerView.setAdapter(tasksAdapter);
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(tasksAdapter);
                itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(taskRecyclerView);

                Log.i("Total tasks obtained: ", String.valueOf(taskList.size()));
            }
        });
    }

    private void refreshAssetsList() {
        fetchAllTasks();
        onItemsLoadComplete();
    }


    private void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }
}