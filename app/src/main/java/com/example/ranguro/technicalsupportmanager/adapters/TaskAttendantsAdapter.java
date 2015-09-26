package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectUser;
import com.parse.ParseObject;

import java.util.List;


public class TaskAttendantsAdapter extends  RecyclerView.Adapter<TaskAttendantsAdapter.ViewHolder>  {

    private List<ParseObjectUser> assignedUsers;
    private ClickListener clickListener;

    public TaskAttendantsAdapter(List<ParseObjectUser> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public TaskAttendantsAdapter() {

    }

    public void addAll(List<ParseObjectUser> taskAttendantsList) {
        assignedUsers = taskAttendantsList;
    }


    public interface ClickListener {

        void onTaskSelected(View view, ParseObject task, int position);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_task_attendants, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setTaskAttendant(assignedUsers.get(position));
        holder.setPosition(position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return assignedUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView attendantFullName;
        private ParseObjectUser assignedUser;

        private int position;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            attendantFullName = (TextView) itemView.findViewById(R.id.attendant_fullname);
        }

        public void setTaskAttendant(ParseObjectUser user){
            this.assignedUser = user;
            String fullName = assignedUser.getFirstName() + " " + assignedUser.getLastName();
            attendantFullName.setText(fullName);
        }

        @Override
        public void onClick(View view) {
            if (clickListener == null) return;
            clickListener.onTaskSelected(view, assignedUser, position);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
