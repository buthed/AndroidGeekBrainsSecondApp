package com.tematikhonov.androidgeekbrainssecondapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tematikhonov.androidgeekbrainssecondapp.ui.NoteFragment

class NoteViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }
        if (savedInstanceState == null) {
            val fragment = NoteFragment()
            fragment.arguments = intent.extras
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }
}