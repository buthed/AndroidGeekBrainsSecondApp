package com.tematikhonov.androidgeekbrainssecondapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    public static final int DEFAULT_INDEX = 0;
    private int index = DEFAULT_INDEX;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(int index) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX, DEFAULT_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        String[] notes_name = getResources().getStringArray(R.array.notes_name);
        String[] notes_description = getResources().getStringArray(R.array.notes_description);
        String[] notes_date = getResources().getStringArray(R.array.notes_date);
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        TextView tvName = view.findViewById(R.id.noteName);
        TextView tvDescription = view.findViewById(R.id.noteDescription);
        TextView tvDate = view.findViewById(R.id.noteDate);
        tvName.setText(notes_name[index]);
        tvDescription.setText(notes_description[index]);
        tvDate.setText(notes_date[index]);
        return view;
    }
}