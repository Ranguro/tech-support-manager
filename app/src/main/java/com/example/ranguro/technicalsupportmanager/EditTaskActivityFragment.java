package com.example.ranguro.technicalsupportmanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ranguro.technicalsupportmanager.classes.ParseObjectAsset;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditTaskActivityFragment extends Fragment {

    private EditText titleView;
    private EditText descriptionView;
    private Spinner priorityView;
    private EditText deadlineView;
    private Calendar myCalendar;
    private ArrayAdapter priorityAdapter;
    private ParseObjectTask selectedTask;

    public EditTaskActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_edit_task, container, false);

        titleView = (EditText) rootView.findViewById(R.id.field_edit_task_title);
        descriptionView = (EditText) rootView.findViewById(R.id.field_edit_task_description);
        priorityView = (Spinner) rootView.findViewById(R.id.spinner_edit_task_priority);
        deadlineView = (EditText) rootView.findViewById(R.id.field_edit_task_deadline);
        setUpDatePicker();
        priorityAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.priority_array,android.R.layout.simple_list_item_1);
        priorityView.setAdapter(priorityAdapter);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //get asset from details activity CHANGE FOR BUNDLE ARGUMENT FROM FATHER
        String taskId = "fRfT8ac10S";

        ParseQuery<ParseObjectTask> getTaskByIDQuery = new ParseQuery<>(ParseObjectTask.class);
        try{
            selectedTask = getTaskByIDQuery.get(taskId);
            String taskDeadline = DateFormat.getDateInstance().format(selectedTask.getDeadline());

            titleView.setText(selectedTask.getTitle());
            descriptionView.setText(selectedTask.getDescription());
            priorityView.setSelection (priorityAdapter.getPosition(selectedTask.getPriority()));
            deadlineView.setText(taskDeadline);
        }catch (com.parse.ParseException e){}
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_task_option){
            try {
                if(isFieldEmpty(titleView) || isFieldEmpty(descriptionView)){
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_task_empty_field_msg, Toast.LENGTH_SHORT).show();
                }
                else {
                    editTask();
                    Intent taskManagerIntent = new Intent(getActivity().getApplication(), TaskManagerActivity.class);
                    startActivity(taskManagerIntent);
                    Toast.makeText(getActivity().getApplication(), R.string.toast_success_task_modification_msg, Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.toast_error_task_invalid_date_msg, Toast.LENGTH_SHORT).show();
            } catch (Exception e){

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void editTask() throws ParseException, com.parse.ParseException {
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();
        String priority = priorityView.getSelectedItem().toString();
        String deadlineText = deadlineView.getText().toString();

        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yy");
        Date deadline = myFormat.parse(deadlineText);
        Date todayDate = new Date();

        if (deadline.before(todayDate)){
            throw new ParseException("", 0);
        }

        selectedTask.setTitle(title);
        selectedTask.setDescription(description);
        selectedTask.setPriority(priority);
        selectedTask.setDeadline(deadline);
        selectedTask.save();
    }

    private void setUpDatePicker() {
        final Context context = getActivity();

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        deadlineView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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

    private boolean isFieldEmpty(EditText field) {
        return field.getText().toString().trim().length() == 0;
    }
}
