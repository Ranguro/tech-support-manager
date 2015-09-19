package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParcelableTask;

import java.util.ArrayList;

/**
 * Created by Randall on 31/08/2015.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{

    private ArrayList<ParcelableTask> assignedTasks;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_task, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setTask(assignedTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return assignedTasks.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView taskTitle;
        public TextView taskAuthor;
        public TextView taskDeadline;


        public ViewHolder(View itemView) {
            super(itemView);

            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskAuthor = (TextView) itemView.findViewById(R.id.task_author);
            taskDeadline = (TextView) itemView.findViewById(R.id.task_deadline);
        }

        public void setTask(ParcelableTask task){
            taskTitle.setText(task.title);
            taskAuthor.setText(task.author);
            taskDeadline.setText(task.deadline);
        }

    }
}
