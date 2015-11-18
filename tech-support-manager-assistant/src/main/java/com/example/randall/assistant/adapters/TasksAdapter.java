package com.example.randall.assistant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.randall.assistant.R;
import com.example.randall.assistant.classes.ParseObjectTask;

import java.text.DateFormat;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<ParseObjectTask> assignedTasks;
    private ClickListener clickListener;

    public TasksAdapter(List<ParseObjectTask> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public TasksAdapter() {

    }

    public void addAll(List<ParseObjectTask> taskList) {
        assignedTasks = taskList;
    }

    public List<ParseObjectTask> getassignedTasks(){
        return assignedTasks;
    }


    public interface ClickListener {

        void onTaskSelected(View view, ParseObjectTask task, int position);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

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
        holder.setPosition(position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return assignedTasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView taskTitle;
        public final TextView taskCreator;
        public final TextView taskDeadline;
        public final ImageView taskStatusColor;

        private ParseObjectTask task;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskCreator = (TextView) itemView.findViewById(R.id.task_author);
            taskDeadline = (TextView) itemView.findViewById(R.id.task_deadline);
            taskStatusColor = (ImageView) itemView.findViewById(R.id.task_colorStatus);
        }

        public void setTask(ParseObjectTask task){
            this.task = task;
            String deadline = DateFormat.getDateInstance().format(task.getDeadline());
            String creator = task.getCreatorID().get("firstName") + " " + task.getCreatorID().get("lastName");
            taskTitle.setText(task.getTitle());
            taskCreator.setText(creator);
            taskDeadline.setText(deadline);

            if (task.getStatus().equals("Completed")) {
                taskStatusColor.setColorFilter(Color.rgb(102, 231, 80));
            } else if (task.getStatus().equals("In Progress")) {
                taskStatusColor.setColorFilter(Color.rgb(255, 112, 43));
            } else if (task.getStatus().equals("Not started")) {
                taskStatusColor.setColorFilter(Color.rgb(111, 111, 255));
            }


        }

        @Override
        public void onClick(View view) {
            if (clickListener == null) return;
            clickListener.onTaskSelected(view, task, position);
        }

        public void setPosition(int position) {
            this.position = position;
        }


    }
}
