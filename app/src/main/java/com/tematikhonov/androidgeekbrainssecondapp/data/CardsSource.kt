package com.tematikhonov.androidgeekbrainssecondapp.data

interface CardsSource {
    fun init(cardsSourceResponce: CardsSourceResponce?): CardsSource?
    fun getNote(position: Int): Note?
    fun size(): Int
    fun deleteCardData(position: Int)
    fun updateCardData(position: Int, note: Note?)
    fun clearCardData()
    fun addCardData(note: Note?)
}