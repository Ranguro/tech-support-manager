package com.example.ranguro.technicalsupportmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.adapters.TaskAttendantsAdapter;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectUser;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private TextView descriptionView;
    private TextView deadlineView;
    private TextView priorityView;
    private TextView taskStatusView;
    private EditText taskAttendantAddView;
    private RecyclerView taskAttendantsRecyclerView;


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
        taskAttendantAddView = (EditText) rootView.findViewById(R.id.field_task_attendant_add);
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

        taskAttendantAddView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String searchText = taskAttendantAddView.getText().toString();
                if (searchText.trim().length() > 0) {
                    PopupMenu menu = new PopupMenu(getActivity().getApplicationContext(), v);
                    Menu popMenu = menu.getMenu();
                    fillMenu(popMenu, searchText);
                    menu.show();
                }
                return false;
            }
        });



        //loadDetailsOnView();
        return rootView;
    }

    private void fillMenu(Menu popMenu, String searchText) {
        for(ParseObjectUser assistant: taskFreeAssistants){
            if(isEqualInLowerCase(assistant.getFirstName(), searchText)){
                popMenu.add(assistant.getFirstName() + " " + assistant.getLastName());
            }
        }
    }

    private boolean isEqualInLowerCase(String firstText, String secondText){
        return firstText.toLowerCase().contains(secondText.toLowerCase());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_task){
            Context context = getActivity().getApplicationContext();
            Intent intent = new Intent(context,EditTaskActivity.class);
            intent.putExtra(TaskManagerDetailsActivityFragment.TASK_DETAIL_KEY, taskDetailId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchTaskDetails(){
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

    public void fetchAllAssistant(){
        ParseQuery<ParseObjectUser> query = new ParseQuery<>(ParseObjectUser.class);
        query.whereEqualTo(ParseObjectUser.COLUMN_USER_PERMISSION, getString(R.string.assistant));
        query.findInBackground(new FindCallback<ParseObjectUser>() {
            @Override
            public void done(List<ParseObjectUser> listAssistants, ParseException e) {
                taskFreeAssistants = listAssistants;
                taskAttendants = new ArrayList<>();
                taskFreeAssistants = new ArrayList<>();
                List<String> taskAttendantsAssigned = taskDetails.getAttendants();
                    for (int i = 0; i < listAssistants.size(); i++){
                        ParseObjectUser assistant = listAssistants.get(i);
                        if (taskAttendantsAssigned.contains(assistant.getObjectId())){
                            taskAttendants.add(assistant);
                        }
                        else{
                            taskFreeAssistants.add(assistant);
                        }
                    }
                loadAttendantsOnView();
                Log.i("Total assign:", String.valueOf(taskAttendants.size()));
                Log.i("Total free:", String.valueOf(taskFreeAssistants.size()));
            }
        });
    }

    private void loadAttendantsOnView() {
        if (!taskAttendants.isEmpty()){
            taskAttendantsRecyclerView.setAdapter(new TaskAttendantsAdapter(taskAttendants));
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

    private void setColorAccordingToStatus(String status){
        Context context = getActivity().getApplicationContext();
        int backgroundColor = ContextCompat.getColor(context, R.color.white);
        if(status.equals(getString(R.string.task_status_completed))){
            backgroundColor = ContextCompat.getColor(context, R.color.green);
        }
        else if (status.equals(getString(R.string.task_status_in_progress))){
            backgroundColor = ContextCompat.getColor(context, R.color.orange);
        }
        else if (status.equals(getString(R.string.task_status_not_started))){
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
    public boolean onContextItemSelected(MenuItem item){
        String result = taskDetails.getStatus();
        if(item.getItemId() == R.id.option_task_in_progress){
            result = getString(R.string.task_status_in_progress);
        }
        else if(item.getItemId() == R.id.option_task_completed){
            result = getString(R.string.task_status_completed);
        }
        if (!result.equals(taskDetails.getStatus())){
            try {
                taskDetails.setStatus(result);
                taskDetails.save();
            }catch(ParseException e){}
            taskStatusView.setText(result);
            setColorAccordingToStatus(result);
        }
        return true;
    }
}
