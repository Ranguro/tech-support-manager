package com.example.randall.assistant.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.randall.assistant.R;
import com.example.randall.assistant.adapters.TasksAdapter;
import com.example.randall.assistant.classes.ParseObjectTask;
import com.example.randall.assistant.classes.ParseObjectUser;
import com.example.randall.assistant.decorators.DividerItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TasksAdapter.ClickListener {

    private RecyclerView taskRecyclerView;
    private TasksAdapter tasksAdapter;
    private List<ParseObjectTask> tasksList;
    private SearchView searchTaskView;
    private ItemTouchHelper itemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        taskRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_tasks);
        searchTaskView = (SearchView) findViewById(R.id.searchview_tasks);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(taskRecyclerView.getContext()));
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchAllTasks();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task_manager) {

        } else if (id == R.id.nav_asset_manager) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        Toast.makeText(TaskManagerActivity.this, "Launch task details activity", Toast.LENGTH_SHORT).show();
    }


    private void fetchAllTasks(){

        ParseQuery<ParseObjectTask> query = new ParseQuery<>(ParseObjectTask.class);
        String objectid = ParseObjectUser.getCurrentUser().getObjectId();

        query.whereEqualTo(ParseObjectTask.COLUMN_TASK_ATTENDANTS, ParseObjectUser.getCurrentUser().getObjectId());

        tasksAdapter = new TasksAdapter();
        tasksAdapter.setClickListener(this);
        query.findInBackground(new FindCallback<ParseObjectTask>() {
            @Override
            public void done(List<ParseObjectTask> taskList, ParseException e) {
                tasksAdapter.addAll(taskList);
                tasksList = taskList;
                taskRecyclerView.getAdapter();
                taskRecyclerView.setAdapter(tasksAdapter);
                Log.i("Total tasks obtained: ", String.valueOf(taskList.size()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        ParseUser.logOut();
        invalidateOptionsMenu();
        Toast.makeText(getApplicationContext(), "Disconnected...", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
