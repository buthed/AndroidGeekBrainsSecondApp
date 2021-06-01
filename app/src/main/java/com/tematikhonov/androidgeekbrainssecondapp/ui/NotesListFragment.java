package com.tematikhonov.androidgeekbrainssecondapp.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
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
import java.util.List;


public class NotesListFragment extends Fragment {


    private static final String TAG = "myLogs";
    private RecyclerView recyclerView;
    private List<Note> notes;
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
                  Log.d(TAG, "Клик");
                data.addCardData(new Note(
                        "Title " + data.size(),
                        "Date " + data.size(),
                        "Description" + data.size(),
                        false));

                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.smoothScrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                data.updateCardData(position,
                        new Note(
                                "Clone " + data.size(),
                                "Date " + data.size(),
                                "Description" + data.size(),
                                false));
                adapter.notifyItemChanged(position);
                Log.d(TAG, "Клик");
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);

        adapter.setListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "position =" + position);
                Toast.makeText(getContext(), String.format("Позиция - ", position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}