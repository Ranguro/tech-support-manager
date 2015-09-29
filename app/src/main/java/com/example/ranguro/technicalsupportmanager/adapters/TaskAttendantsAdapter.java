package com.example.ranguro.technicalsupportmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ranguro.technicalsupportmanager.R;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectTask;
import com.example.ranguro.technicalsupportmanager.classes.ParseObjectUser;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;


public class TaskAttendantsAdapter extends  RecyclerView.Adapter<TaskAttendantsAdapter.ViewHolder> {

    private List<ParseObjectUser> assignedUsers;
    private List<ParseObjectUser> freeUsers;
    private List<String> freeUsersName;
    private ClickListener clickListener;

    private ParseObjectTask assignedUsersTask;

    public TaskAttendantsAdapter(List<ParseObjectUser> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public TaskAttendantsAdapter() {

    }

    public void addAll(List<ParseObjectUser> taskAttendantsList) {
        assignedUsers = taskAttendantsList;
    }

    public void setTask(ParseObjectTask task){
        assignedUsersTask = task;
    }

    public void setFreeUsers(List<ParseObjectUser> parseObjectUserList){
        freeUsers = parseObjectUserList;
    }
    public void setFreeUsersName(List<String> freeUserNames){
        freeUsersName = freeUserNames;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setTaskAttendant(assignedUsers.get(position));
        holder.setPosition(position);
        holder.kickAttendantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAttendant(position);
            }
        });
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addAttendant(ParseObjectUser attendant){

    }

    public void removeAttendant(int position){
        ParseObjectUser attendant = assignedUsers.get(position);
        assignedUsersTask.getAttendants().remove(attendant.getObjectId());
        assignedUsers.remove(position);
        freeUsers.add(attendant);
        freeUsersName.add(attendant.getFirstName() + " " + attendant.getLastName());
        notifyItemRemoved(position);
        notifyDataSetChanged();
        try {
            assignedUsersTask.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return assignedUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView attendantFullName;
        public final ImageButton kickAttendantButton;
        private ParseObjectUser assignedUser;

        private int position;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            attendantFullName = (TextView) itemView.findViewById(R.id.attendant_fullname);
            kickAttendantButton = (ImageButton) itemView.findViewById(R.id.kick_attendant_button);
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
