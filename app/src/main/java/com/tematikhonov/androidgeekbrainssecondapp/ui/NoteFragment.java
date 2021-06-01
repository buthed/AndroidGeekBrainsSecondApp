package com.tematikhonov.androidgeekbrainssecondapp.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tematikhonov.androidgeekbrainssecondapp.R;
import com.tematikhonov.androidgeekbrainssecondapp.data.Note;

public class NoteFragment extends Fragment {

    static final String CURRENT_NOTE = "currentNote";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextView tvName = view.findViewById(R.id.noteName);
        TextView tvDescription = view.findViewById(R.id.noteDescription);
        TextView tvDate = view.findViewById(R.id.noteDate);
        tvName.setText(note.getTitle());
        tvDescription.setText(note.getDescription());
        tvDate.setText(note.getDate());
        return view;
    }
}