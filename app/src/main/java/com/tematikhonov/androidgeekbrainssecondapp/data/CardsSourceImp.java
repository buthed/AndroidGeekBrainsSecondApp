package com.tematikhonov.androidgeekbrainssecondapp.data;

import android.content.res.Resources;

import com.tematikhonov.androidgeekbrainssecondapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardsSourceImp implements CardsSource {

    private List<Note> list;
    private Resources resources;

    public CardsSourceImp(Resources resources) {
        list = new ArrayList<>(7);
        this.resources = resources;
    }

    @Override
    public CardsSource init(CardsSourceResponce cardsSourceResponce) {
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        for (int i = 0; i < titles.length; i++) {
            list.add(new Note(
                    titles[i],
                    new Date(),
                    descriptions[i],
                    false
            ));
        }
        if (cardsSourceResponce != null){
            cardsSourceResponce.initializes(this);
        }
        return this;
    }



    @Override
    public Note getNote(int position) {
        return list.get(position);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void deleteCardData(int position) {
        list.remove(position);
    }

    @Override
    public void updateCardData(int position, Note note) {
        list.set(position, note);
    }

    @Override
    public void addCardData(Note note) {
        list.add(note);
    }

    @Override
    public void clearCardData() {
        list.clear();
    }
}