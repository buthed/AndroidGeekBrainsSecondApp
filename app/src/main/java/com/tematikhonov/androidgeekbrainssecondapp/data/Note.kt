package com.tematikhonov.androidgeekbrainssecondapp.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

open class Note : Parcelable {
    var id: String? = null
    var title: String?
        private set
    var date: Date
        private set
    var description: String?
        private set
    var isFavorite: Boolean
        private set

    constructor(title: String?, date: Date, description: String?, favorite: Boolean) {
        this.title = title
        this.date = date
        this.description = description
        isFavorite = favorite
    }

    constructor(`in`: Parcel) {
        title = `in`.readString()
        description = `in`.readString()
        date = Date(`in`.readLong())
        isFavorite = `in`.readByte().toInt() != 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeLong(date.time)
        dest.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}