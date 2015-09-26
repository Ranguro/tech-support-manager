package com.example.ranguro.technicalsupportmanager;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TaskManagerDetailsActivity extends AppCompatActivity {

    private final String CREATED_BY = "Created by, ";

    public static final String TASK_TITLE = "task_title";
    public static final String TASK_CREATED_BY = "task_created_by";

    private final String LOG_TAG = TaskManagerDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager_details);
        if (savedInstanceState == null) {
            String subtitleCreatedBy = CREATED_BY+" "+getIntent().getStringExtra(TASK_CREATED_BY);
            String titleTaskTitle = getIntent().getStringExtra(TASK_TITLE);

            ActionBar ab = getSupportActionBar();
            ab.setTitle(titleTaskTitle);
            ab.setSubtitle(subtitleCreatedBy);
            ab.setDisplayHomeAsUpEnabled(true);

            Bundle args = new Bundle();
            String detailId =  getIntent().getStringExtra(TaskManagerDetailsActivityFragment.TASK_DETAIL_KEY);

            args.putString(TaskManagerDetailsActivityFragment.TASK_DETAIL_KEY,
                   detailId);
            TaskManagerDetailsActivityFragment fragment = new TaskManagerDetailsActivityFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.task_details_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_manager_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_assset_manager) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
