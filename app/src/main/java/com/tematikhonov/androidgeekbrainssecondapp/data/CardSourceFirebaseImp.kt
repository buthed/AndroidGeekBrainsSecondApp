package com.tematikhonov.androidgeekbrainssecondapp.data

import android.util.Log
import com.google.firebase.firestore.*
import java.util.*

class CardSourceFirebaseImp : CardsSource {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection(CARD_COLLECTION)
    private val notes: MutableList<Note> = ArrayList()

    override fun init(cardsSourceResponce: CardsSourceResponce?): CardsSource? {
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        notes.clear()
                        for (document in task.result!!) {
                            val doc = document.data
                            val note = NoteMapping.toNote(document.id, doc)
                            notes.add(note)
                        }
                        Log.d(TAG, "isSuccessful")
                        cardsSourceResponce?.initializes(this@CardSourceFirebaseImp)
                    } else {
                        Log.d(TAG, "Failed")
                    }
                }.addOnFailureListener { e -> Log.d(TAG, "Failed", e) }
        return this
    }


    override fun getNote(position: Int): Note {
        return notes[position]
    }

    override fun size(): Int {
        return notes.size
    }

    override fun deleteCardData(position: Int) {
        collection.document(getNote(position).id!!).delete()
        notes.removeAt(position)
    }

    override fun updateCardData(position: Int, note: Note?) {
        val id = note?.id
        collection.document(id!!).set(NoteMapping.toDocument(note))
    }


    override fun clearCardData() {
        for (note in notes) {
            collection.document(note.id!!).delete()
        }
        notes.clear()
    }

    override fun addCardData(note: Note?) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener { documentReference -> note?.id = documentReference.id }
    }

    companion object {
        const val CARD_COLLECTION = "cards"
        const val TAG = "[CardSourceFirebaseImp]"
    }
}