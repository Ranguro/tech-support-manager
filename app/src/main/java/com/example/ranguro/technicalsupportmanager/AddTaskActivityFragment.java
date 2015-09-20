package com.example.ranguro.technicalsupportmanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTaskActivityFragment extends Fragment {


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
}
