package com.tematikhonov.androidgeekbrainssecondapp.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tematikhonov.androidgeekbrainssecondapp.R;
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSource;
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSourceImp;
import com.tematikhonov.androidgeekbrainssecondapp.data.Note;

import java.util.ArrayList;


public class NotesListFragment extends Fragment {


    private static final String TAG = "myLogs";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private MyAdapter adapter;
    private CardsSource data;

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        data = new CardsSourceImp(getResources()).init();
        initRecyclerView();

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                        int position = data.addCardData(new Note(
                        "Title " + data.size(),
                        "Date" + data.size(),
                        "Description" + data.size(),
                        false));
                adapter.notifyItemInserted(position);
                recyclerView.scrollToPosition(position);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(new CardsSourceImp(getResources()).init());
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "position =" + position);
                Toast.makeText(getContext(), String.format("Позиция - ", position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}