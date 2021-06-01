package com.tematikhonov.androidgeekbrainssecondapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String title;
    private String date;
    private String description;
    private boolean favorite;

    public Note(String title, String date, String description, boolean favorite) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.favorite = favorite;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date =  in.readString();
        favorite = in.readByte() != 0;
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

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }
}