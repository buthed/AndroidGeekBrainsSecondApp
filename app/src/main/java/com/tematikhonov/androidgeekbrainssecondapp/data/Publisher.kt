package com.tematikhonov.androidgeekbrainssecondapp.data

import java.util.*

class Publisher {
    private val observers: MutableList<Observer>
    fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }

    fun notifySingle(note: Note?) {
        for (observer in observers) {
            observer.updateCardData(note)
            unsubscribe(observer)
        }
    }

    init {
        observers = ArrayList()
    }
}