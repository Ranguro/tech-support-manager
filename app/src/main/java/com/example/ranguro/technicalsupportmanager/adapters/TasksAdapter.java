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

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{

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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView taskTitle;
        public final TextView taskCreator;
        public final TextView taskDeadline;

        private ParseObjectTask task;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskCreator = (TextView) itemView.findViewById(R.id.task_author);
            taskDeadline = (TextView) itemView.findViewById(R.id.task_deadline);
        }

        public void setTask(ParseObjectTask task){
            this.task = task;
            String deadline = DateFormat.getDateInstance().format(task.getDeadline());
            String creator = task.getCreatorID().get("firstName") + " " + task.getCreatorID().get("lastName");
            taskTitle.setText(task.getTitle());
            taskCreator.setText(creator);
            taskDeadline.setText(deadline);
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
