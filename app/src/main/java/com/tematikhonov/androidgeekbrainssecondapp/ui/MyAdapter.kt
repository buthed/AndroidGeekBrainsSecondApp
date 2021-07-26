package com.tematikhonov.androidgeekbrainssecondapp.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tematikhonov.androidgeekbrainssecondapp.R
import com.tematikhonov.androidgeekbrainssecondapp.data.CardsSource
import com.tematikhonov.androidgeekbrainssecondapp.data.Note
import com.tematikhonov.androidgeekbrainssecondapp.ui.MyAdapter.MyViewHolder
import java.text.SimpleDateFormat

class MyAdapter(private val fragment: Fragment?) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: CardsSource? = null
    var menuPosition = 0
    var listener: OnItemClickListener? = null
    fun setDataSource(data: CardsSource?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(data!!.getNote(position))
    }

    override fun getItemCount(): Int {
        return data!!.size()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView
        private val date: TextView
        private val description: TextView
        private val favorite: CheckBox
        fun setData(data: Note?) {
            title.text = data!!.title
            date.text = SimpleDateFormat("dd-MM-yy").format(data.date)
            description.text = data.description
            favorite.isChecked = data.isFavorite
        }

        init {
            title = itemView.findViewById(R.id.titleCard)
            date = itemView.findViewById(R.id.dateCard)
            description = itemView.findViewById(R.id.descriptionCard)
            favorite = itemView.findViewById(R.id.checkboxCard)
            fragment?.registerForContextMenu(itemView)
            title.setOnClickListener {
                if (listener != null) {
                    listener!!.onItemClick(adapterPosition)
                }
            }
            title.setOnLongClickListener {
                menuPosition = layoutPosition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.showContextMenu(10f, 10f)
                }
                true
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        private const val TAG = "MyAdapter"
    }
}