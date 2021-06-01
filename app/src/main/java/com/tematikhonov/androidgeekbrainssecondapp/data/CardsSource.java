package com.tematikhonov.androidgeekbrainssecondapp.data;

public interface CardsSource {
    Note getNote(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, Note note);
    int addCardData(Note note);
    void clearCardData();
}