package com.tematikhonov.androidgeekbrainssecondapp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Calendar;

public class Note implements Parcelable {
    private String name;
    private String description;
    private Calendar creationDate;

    public Note(String name, String description, Calendar creationDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        creationDate = (Calendar) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeSerializable(creationDate);
    }
}