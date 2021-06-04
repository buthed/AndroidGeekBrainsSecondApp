package com.tematikhonov.androidgeekbrainssecondapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImp  implements CardsSource {

    public static final String CARD_COLLECTION = "cards";
    public static final String TAG = "[CardSourceFirebaseImp]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(CARD_COLLECTION);

    final private List<Note> notes = new ArrayList<>();

    @Override
    public CardsSource init(CardsSourceResponce cardsSourceResponce) {
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notes.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();

                                Note note = NoteMapping.toNote(document.getId(), doc);
                                notes.add(note);
                            }
                            Log.d(TAG, "isSuccessful");
                            cardsSourceResponce.initializes(CardSourceFirebaseImp.this);
                        } else {
                            Log.d(TAG, "Failed");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed", e);
            }
        });

        return this;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(getNote(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void updateCardData(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));
    }

    @Override
    public void addCardData(Note note) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearCardData() {
        for (Note note : notes) {
            collection.document(note.getId()).delete();
        }

        notes.clear();
    }

}