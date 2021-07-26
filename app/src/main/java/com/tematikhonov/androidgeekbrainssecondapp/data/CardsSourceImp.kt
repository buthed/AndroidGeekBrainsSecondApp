package com.tematikhonov.androidgeekbrainssecondapp.data

import android.content.res.Resources
import com.tematikhonov.androidgeekbrainssecondapp.R
import java.util.*

class CardsSourceImp(resources: Resources) : CardsSource {
    private val list: MutableList<Note?>
    private val resources: Resources
    override fun init(cardsSourceResponce: CardsSourceResponce?): CardsSource? {
        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        for (i in titles.indices) {
            list.add(Note(
                    titles[i],
                    Date(),
                    descriptions[i],
                    false
            ))
        }
        cardsSourceResponce?.initializes(this)
        return this
    }

    override fun getNote(position: Int): Note? {
        return list[position]
    }

    override fun size(): Int {
        return list.size
    }

    override fun deleteCardData(position: Int) {
        list.removeAt(position)
    }

    override fun updateCardData(position: Int, note: Note?) {
        list[position] = note
    }

    override fun addCardData(note: Note?) {
        list.add(note)
    }

    override fun clearCardData() {
        list.clear()
    }

    init {
        list = ArrayList(7)
        this.resources = resources
    }
}