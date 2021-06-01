package com.tematikhonov.androidgeekbrainssecondapp.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tematikhonov.androidgeekbrainssecondapp.R;
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSource;
import com.tematikhonov.androidgeekbrainssecondapp.data.Note;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static String TAG = "MyAdapter";
    private CardsSource data;
    private Fragment fragment;
    private int menuPosition;
    private OnItemClickListener listener;

    public MyAdapter(CardsSource cardsSource, Fragment fragment) {
        this.data = cardsSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(data.getNote(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getListener() {
        return listener;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView description;
        private CheckBox favorite;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCard);
            date = itemView.findViewById(R.id.dateCard);
            description = itemView.findViewById(R.id.descriptionCard);
            favorite =   itemView.findViewById(R.id.checkboxCard);

            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });

            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(10,10);
                    return true;
                }
            });

        }

        void setData(Note data) {
            title.setText(data.getTitle());
            date.setText(data.getDate());
            description.setText(data.getDescription());
            favorite.setChecked(data.isFavorite());
//            title.setOnClickListener(v -> {
//                if (listener != null) {
//                    listener.onItemClick(getAdapterPosition());
//                }
//            });
//            title.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    itemView.showContextMenu(10,10);
//                    return true;
//                }
//            });

        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}