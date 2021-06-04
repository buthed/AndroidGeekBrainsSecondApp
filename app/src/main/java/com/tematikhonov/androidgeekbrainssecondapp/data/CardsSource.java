package com.tematikhonov.androidgeekbrainssecondapp.data;

public interface CardsSource {
    CardsSource init(CardsSourceResponce cardsSourceResponce);
    Note getNote(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, Note note);
    void clearCardData();
    void addCardData(Note note);
}