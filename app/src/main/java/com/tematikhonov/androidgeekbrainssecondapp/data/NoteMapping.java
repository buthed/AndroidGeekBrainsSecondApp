package com.tematikhonov.androidgeekbrainssecondapp.data;




import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteMapping {

    public static class Fields {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String DESCRIPTION = "description";
        public static final String FAVORITE = "favorite";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        String title = (String) doc.get(Fields.TITLE);
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        String description = (String) doc.get(Fields.DESCRIPTION);
        boolean favorite = (boolean) doc.get(Fields.FAVORITE);

        Note data = new Note(
                title,
                timeStamp.toDate(),
                description,
                favorite
        );
        data.setId(id);
        return data;
    }

    public static Map<String, Object>  toDocument(Note note) {
        Map<String, Object> doc = new HashMap<>();

        doc.put(Fields.TITLE, note.getTitle());
        doc.put(Fields.DATE, note.getDate());
        doc.put(Fields.DESCRIPTION, note.getDescription());
        doc.put(Fields.FAVORITE, note.isFavorite());
        return doc;
    }
}
