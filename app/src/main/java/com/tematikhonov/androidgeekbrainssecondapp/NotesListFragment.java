package com.tematikhonov.androidgeekbrainssecondapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesListFragment extends Fragment {

    private boolean isLandscape;

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

        isLandscape =getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            showNote(NoteFragment.DEFAULT_INDEX);
        }
    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notes_name = getResources().getStringArray(R.array.notes_name);
        for (int i = 0; i < notes_name.length; i++) {
            String noteName = notes_name[i];
            TextView textView = new TextView(getContext());
            textView.setText(noteName);
            textView.setTextSize(30);
            linearLayout.addView(textView);

            final int currentIndex = i;
            textView.setOnClickListener(v -> {
                showNote(currentIndex);
            });
        }
    }

    void showNote(int index) {
        if (isLandscape) {
            showLandNote(index);
        } else {
            showPortNote(index);
        }
    }

    void showLandNote(int index) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.noteLayout, NoteFragment.newInstance(index))
                .commit();
    }

    void showPortNote(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteViewActivity.class);
        intent.putExtra(NoteFragment.ARG_INDEX, index);
        startActivity(intent);
    }


}