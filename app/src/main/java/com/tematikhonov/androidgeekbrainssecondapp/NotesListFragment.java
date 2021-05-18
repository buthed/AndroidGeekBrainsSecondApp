package com.tematikhonov.androidgeekbrainssecondapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesListFragment extends Fragment {

    private boolean isLandscape;
    private Note[] notes;
    private Note currentNote;

    public NotesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(NoteFragment.CURRENT_NOTE);
        } else {
            currentNote = notes[0];
        }
        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void initList(View view) {
        notes = new Note[]{
                new Note(getString(R.string.note1_name), getString(R.string.note1_desc), Calendar.getInstance()),
                new Note(getString(R.string.note2_name), getString(R.string.note2_desc), Calendar.getInstance()),
                new Note(getString(R.string.note3_name), getString(R.string.note3_desc), Calendar.getInstance()),
                new Note(getString(R.string.note4_name), getString(R.string.note4_desc), Calendar.getInstance()),
                new Note(getString(R.string.note5_name), getString(R.string.note5_desc), Calendar.getInstance()),
        };

        for (Note note : notes) {
            Context context = getContext();
            if (context != null) {
                LinearLayout linearView = (LinearLayout) view;
                TextView firstTextView = new TextView(context);
                TextView secondTextView = new TextView(context);
                firstTextView.setText(note.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
                secondTextView.setText(formatter.format(note.getCreationDate().getTime()));
                linearView.addView(firstTextView);
                linearView.addView(secondTextView);
                firstTextView.setPadding(0, 22, 0, 0);
                firstTextView.setOnClickListener(v -> {
                    currentNote = note;
                    showNote(currentNote);
                });
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NoteFragment.CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }


    private void showPortNote(Note currentNote) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), NoteViewActivity.class);
            intent.putExtra(NoteFragment.CURRENT_NOTE, currentNote);
            startActivity(intent);
    }

    private void showLandNote(Note currentNote) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.noteLayout, NoteFragment.newInstance(currentNote))
                .commit();
    }
}