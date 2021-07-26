package com.tematikhonov.androidgeekbrainssecondapp.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tematikhonov.androidgeekbrainssecondapp.R

class Navigation(private val fragmentManager: FragmentManager) {
    fun addFragment(fragment: Fragment?, useBackStack: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment!!)
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}