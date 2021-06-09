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
import com.tematikhonov.androidgeekbrainssecondapp.data.CardSourceFirebaseImp;
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSource;
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSourceResponce;
import com.tematikhonov.androidgeekbrainssecondapp.data.Navigation;
import com.tematikhonov.androidgeekbrainssecondapp.data.Note;
import com.tematikhonov.androidgeekbrainssecondapp.data.Observer;
import com.tematikhonov.androidgeekbrainssecondapp.data.Publisher;

public class NotesListFragment extends Fragment implements DialogBuilderFragment.DeleteDialogCaller {

    private static final String TAG = "myLogs";
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private CardsSource data;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView();
        setHasOptionsMenu(true);
        data = new CardSourceFirebaseImp().init(new CardsSourceResponce() {
            @Override
            public void initializes(CardsSource cardsSource) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.action_add:
                navigation.addFragment(NoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(Note note) {
                        data.addCardData(note);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(NoteFragment.newInstance(data.getNote(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(Note note) {
                        data.updateCardData(updatePosition, note);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                DialogBuilderFragment dialogFragment = new DialogBuilderFragment();
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

        adapter.setListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "position =" + position);
                Toast.makeText(getContext(), String.format("Позиция - ", position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDialogResult(String result) {
        int position = adapter.getMenuPosition();
        if (result.equals("DELETE")) {
            int deletePosition = adapter.getMenuPosition();
            data.deleteCardData(deletePosition);
            adapter.notifyItemRemoved(deletePosition);
        } else if (result.equals("CANCEL")) {
            Toast.makeText(getContext(), "Отклонено удаление заметки " + position, Toast.LENGTH_SHORT).show();
        }
    }
}