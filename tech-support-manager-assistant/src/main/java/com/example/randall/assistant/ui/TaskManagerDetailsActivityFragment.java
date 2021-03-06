package com.example.randall.assistant.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.randall.assistant.R;
import com.example.randall.assistant.adapters.TaskAttendantsAdapter;
import com.example.randall.assistant.classes.ParseObjectTask;
import com.example.randall.assistant.classes.ParseObjectUser;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskManagerDetailsActivityFragment extends Fragment {


    private final String LOG_TAG = TaskManagerDetailsActivityFragment.class.getSimpleName();


    public static final String TASK_DETAIL_KEY = "task_detail_id";


    private String taskDetailId;

    private ParseObjectTask taskDetails;
    private List<ParseObjectUser> taskAttendants;
    private List<ParseObjectUser> taskFreeAssistants;
    private List<String> taskFreeAssistantsNames;

    private TextView descriptionView;
    private TextView deadlineView;
    private TextView priorityView;
    private TextView taskStatusView;
    private RecyclerView taskAttendantsRecyclerView;
    private TaskAttendantsAdapter taskAttendantsAdapter;


    public TaskManagerDetailsActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        taskDetailId = getArguments().getString(TASK_DETAIL_KEY);
        fetchTaskDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_manager_details, container, false);
        descriptionView = (TextView) rootView.findViewById(R.id.field_task_detail_description);
        priorityView = (TextView) rootView.findViewById(R.id.field_task_detail_priority);
        deadlineView = (TextView) rootView.findViewById(R.id.field_task_detail_deadline);
        taskStatusView = (TextView) rootView.findViewById(R.id.label_task_detail_status);
        taskAttendantsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_task_attendants);
        taskAttendantsRecyclerView.setLayoutManager(new LinearLayoutManager(taskAttendantsRecyclerView.getContext()));
        taskAttendantsRecyclerView.setHasFixedSize(false);
        taskAttendantsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        registerForContextMenu(taskStatusView);

        taskStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });



        //loadDetailsOnView();
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void fetchTaskDetails() {
        ParseQuery<ParseObjectTask> query = ParseQuery.getQuery(ParseObjectTask.class);
        query.getInBackground(taskDetailId, new GetCallback<ParseObjectTask>() {
            @Override
            public void done(ParseObjectTask parseObjectTask, ParseException e) {
                taskDetails = parseObjectTask;
                loadDetailsOnView();
                fetchAllAssistant();
            }
        });
    }

    public void fetchAllAssistant() {
        ParseQuery<ParseObjectUser> query = new ParseQuery<>(ParseObjectUser.class);
        query.whereEqualTo(ParseObjectUser.COLUMN_USER_PERMISSION, getString(R.string.assistant));
        query.findInBackground(new FindCallback<ParseObjectUser>() {
            @Override
            public void done(List<ParseObjectUser> listAssistants, ParseException e) {
                taskFreeAssistants = listAssistants;
                taskAttendants = new ArrayList<>();
                taskFreeAssistants = new ArrayList<>();
                taskFreeAssistantsNames = new ArrayList<>();
                List<String> taskAttendantsAssigned = taskDetails.getAttendants();
                for (int i = 0; i < listAssistants.size(); i++) {
                    ParseObjectUser assistant = listAssistants.get(i);
                    if (taskAttendantsAssigned.contains(assistant.getObjectId())) {
                        taskAttendants.add(assistant);
                    } else {
                        taskFreeAssistants.add(assistant);
                        taskFreeAssistantsNames.add(assistant.getFirstName() + " " + assistant.getLastName());
                    }
                }
                loadAttendantsOnView();
                initAutocompleteAttendants();
                Log.i("Total assign:", String.valueOf(taskAttendants.size()));
                Log.i("Total free:", String.valueOf(taskFreeAssistants.size()));
            }
        });
    }

    public void initAutocompleteAttendants() {
        String[] taskFreeAssistantsNamesArray = new String[taskFreeAssistantsNames.size()];
        taskFreeAssistantsNames.toArray(taskFreeAssistantsNamesArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.list_item_assistant, taskFreeAssistantsNamesArray);
    }

    private void loadAttendantsOnView() {
        if (!taskAttendants.isEmpty()) {
            taskAttendantsAdapter = new TaskAttendantsAdapter(taskAttendants);
            taskAttendantsRecyclerView.setAdapter(taskAttendantsAdapter);
        }
    }

    private void loadDetailsOnView() {
        String status = taskDetails.getStatus();
        descriptionView.setText(taskDetails.getDescription());
        priorityView.setText(taskDetails.getPriority());
        taskStatusView.setText(status);
        deadlineView.setText(DateFormat.getDateInstance().format(taskDetails.getDeadline()));
        setColorAccordingToStatus(status);
    }

    private void setColorAccordingToStatus(String status) {
        Context context = getActivity().getApplicationContext();
        int backgroundColor = ContextCompat.getColor(context, R.color.white);
        if (status.equals(getString(R.string.task_status_completed))) {
            backgroundColor = ContextCompat.getColor(context, R.color.green);
        } else if (status.equals(getString(R.string.task_status_in_progress))) {
            backgroundColor = ContextCompat.getColor(context, R.color.orange);
        } else if (status.equals(getString(R.string.task_status_not_started))) {
            backgroundColor = ContextCompat.getColor(context, R.color.sky_blue);
        }
        taskStatusView.setBackgroundColor(backgroundColor);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_task_status, menu);
        menu.setHeaderTitle(R.string.title_contextual_task_status);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String result = taskDetails.getStatus();
        if (item.getItemId() == R.id.option_task_in_progress) {
            result = getString(R.string.task_status_in_progress);
        } else if (item.getItemId() == R.id.option_task_completed) {
            result = getString(R.string.task_status_completed);
        }
        if (!result.equals(taskDetails.getStatus())) {
            try {
                taskDetails.setStatus(result);
                taskDetails.save();
            } catch (ParseException e) {
            }
            taskStatusView.setText(result);
            setColorAccordingToStatus(result);
        }
        return true;
    }
}
