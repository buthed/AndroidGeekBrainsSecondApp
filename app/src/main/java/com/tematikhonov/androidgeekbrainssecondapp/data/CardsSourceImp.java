package com.tematikhonov.androidgeekbrainssecondapp.data;

import android.content.res.Resources;

import com.tematikhonov.androidgeekbrainssecondapp.R;

import java.util.ArrayList;

public class CardsSourceImp implements CardsSource {

    private ArrayList<Note> list;
    private Resources resources;

    public CardsSourceImp(Resources resources) {
        list = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardsSourceImp init() {
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dates = resources.getStringArray(R.array.dates);


        for (int i = 0; i < titles.length; i++) {
            list.add(new Note(
                    titles[i],
                    dates[i],
                    descriptions[i],
                    false
            ));
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

    /**
     *
     * @param note for addition
     * @return
     */
    @Override
    public int addCardData(Note note) {
        list.add(note);
        return list.indexOf(note);
    }

    @Override
    public void clearCardData() {
        list.clear();
    }

}