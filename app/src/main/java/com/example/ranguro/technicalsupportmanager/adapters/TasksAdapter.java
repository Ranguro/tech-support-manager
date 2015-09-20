package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Randall on 31/08/2015.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{

    private List<ParseObjectTask> assignedTasks;

    public TasksAdapter(List<ParseObjectTask> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

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

        public final TextView taskTitle;
        public final TextView taskCreator;
        public final TextView taskDeadline;


        public ViewHolder(View itemView) {
            super(itemView);

            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskCreator = (TextView) itemView.findViewById(R.id.task_author);
            taskDeadline = (TextView) itemView.findViewById(R.id.task_deadline);
        }

        public void setTask(ParseObjectTask task){
            String deadline = DateFormat.getDateInstance().format(task.getDeadline());;
            String creator = task.getCreatorID().get("firstName") + " " + task.getCreatorID().get("lastName");
            taskTitle.setText(task.getTitle());
            taskCreator.setText(creator);
            taskDeadline.setText(deadline);
        }

    }
}
