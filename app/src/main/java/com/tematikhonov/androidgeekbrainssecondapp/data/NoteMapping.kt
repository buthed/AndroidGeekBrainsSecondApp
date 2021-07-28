package com.tematikhonov.androidgeekbrainssecondapp.data

import com.google.firebase.Timestamp
import java.util.*

object NoteMapping {
    fun toNote(id: String?, doc: Map<String?, Any?>): Note {
        val title = doc[Fields.TITLE] as String?
        val timeStamp = doc[Fields.DATE] as Timestamp?
        val description = doc[Fields.DESCRIPTION] as String?
        val favorite = doc[Fields.FAVORITE] as Boolean
        val data = Note(
                title,
                timeStamp!!.toDate(),
                description,
                favorite
        )
        data.id = id
        return data
    }

    fun toDocument(note: Note?): Map<String, Any?> {
        val doc: MutableMap<String, Any?> = HashMap()
        doc[Fields.TITLE] = note?.title
        doc[Fields.DATE] = note?.date
        doc[Fields.DESCRIPTION] = note?.description
        doc[Fields.FAVORITE] = note?.isFavorite
        return doc
    }

    object Fields {
        const val ID = "id"
        const val TITLE = "title"
        const val DATE = "date"
        const val DESCRIPTION = "description"
        const val FAVORITE = "favorite"
    }
}