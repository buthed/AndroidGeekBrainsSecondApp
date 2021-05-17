package com.tematikhonov.androidgeekbrainssecondapp;

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

    private static final String ARG_PARAM1 = "param1";

    public NotesListFragment() {
    }

//    public static NotesListFragment newInstance(String param1, String param2) {
//        NotesListFragment fragment = new NotesListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }
//    }

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

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notes_name = getResources().getStringArray(R.array.notes_name);
        for (int i = 0; i < notes_name.length; i++) {
            String noteName = notes_name[i];
            TextView textView = new TextView(getContext());
            textView.setText(noteName);
            textView.setTextSize(30);
            linearLayout.addView(textView);
        }
    }


}