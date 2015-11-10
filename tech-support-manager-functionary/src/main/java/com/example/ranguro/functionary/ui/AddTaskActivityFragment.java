package com.example.ranguro.functionary.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ranguro.functionary.R;
import com.example.ranguro.functionary.classes.ParseObjectTask;
import com.example.ranguro.functionary.classes.ParseObjectUser;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTaskActivityFragment extends Fragment {

    private final String LOG_TAG = AddTaskActivityFragment.class.getSimpleName();

    private EditText titleView;
    private EditText descriptionView;
    private Spinner priorityView;
    private EditText deadlineView;
    private Calendar myCalendar;
    private ArrayAdapter priorityAdapter;

    public AddTaskActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_task_option){
            try {
                if(isFieldEmpty(titleView) || isFieldEmpty(descriptionView)){
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_task_empty_field_msg, Toast.LENGTH_SHORT).show();
                }
                else {
                addNewTask();
                }
            } catch (ParseException e) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_task_invalid_date_msg, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().trim().length() == 0;
    }

    private void addNewTask() throws ParseException {
        final ParseObjectTask newTask = new ParseObjectTask();
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();
        String deadlineText = deadlineView.getText().toString();
        String priority = priorityView.getSelectedItem().toString();
        ParseObjectUser creator = ((ParseObjectUser) ParseUser.getCurrentUser());

        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yy");
        Date deadline = myFormat.parse(deadlineText);
        Date todayDate = new Date();
        if (deadline.before(todayDate)){
            throw new ParseException("", 0);
        }
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setDeadline(deadline);
        newTask.setPriority(priority);
        newTask.setCreatorId(creator);
        newTask.setStatus(getString(R.string.task_status_not_started));
        newTask.setAttendants(new ArrayList<String>());
        newTask.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Intent taskManagerIntent = new Intent(getActivity().getApplication(), TaskManagerActivity.class);
                    startActivity(taskManagerIntent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_add_task, container, false);

        titleView = (EditText) rootView.findViewById(R.id.field_task_title);
        descriptionView = (EditText) rootView.findViewById(R.id.field_task_description);
        priorityView = (Spinner) rootView.findViewById(R.id.spinner_task_priority);
        deadlineView = (EditText) rootView.findViewById(R.id.field_task_deadline);
        setUpDatePicker();
        priorityAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.priority_array,android.R.layout.simple_list_item_1);
        priorityView.setAdapter(priorityAdapter);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return rootView;
    }

    private void setUpDatePicker() {
        final Context context = getActivity();

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        deadlineView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        deadlineView.setText(sdf.format(myCalendar.getTime()));
    }
}
