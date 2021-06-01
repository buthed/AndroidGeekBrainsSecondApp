package com.tematikhonov.androidgeekbrainssecondapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static String TAG = "MyAdapter";
    private CardsSource cardsSource;
    private OnItemClickListener listener;

    public MyAdapter(CardsSource cardsSource) {
        this.cardsSource = cardsSource;
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
        holder.bind(cardsSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return cardsSource.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getListener() {
        return listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCard);
            date = itemView.findViewById(R.id.dateCard);
        }

        void bind(Note data) {
            title.setText(data.getTitle());
            date.setText(data.getDate());

            title.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

}
