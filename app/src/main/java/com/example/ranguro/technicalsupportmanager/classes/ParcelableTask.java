package com.example.ranguro.technicalsupportmanager.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Randall on 03/09/2015.
 */
public class ParcelableTask implements Parcelable {

    public String title;
    public String author;
    public String description;
    public String deadline;


    public ParcelableTask(String title, String author, String description, String deadline) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.description);
        dest.writeString(this.deadline);
    }

    protected ParcelableTask(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.deadline = in.readString();
    }

    public static final Creator<ParcelableTask> CREATOR = new Creator<ParcelableTask>() {
        public ParcelableTask createFromParcel(Parcel source) {
            return new ParcelableTask(source);
        }

        public ParcelableTask[] newArray(int size) {
            return new ParcelableTask[size];
        }
    };
}
