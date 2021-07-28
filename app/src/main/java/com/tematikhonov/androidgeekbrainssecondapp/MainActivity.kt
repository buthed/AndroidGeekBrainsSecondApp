package com.tematikhonov.androidgeekbrainssecondapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tematikhonov.androidgeekbrainssecondapp.data.*
import com.tematikhonov.androidgeekbrainssecondapp.ui.NotesListFragment

class MainActivity : AppCompatActivity() {
    var navigation: Navigation? = null
        private set
    val publisher = Publisher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation = Navigation(supportFragmentManager)
        initView()

    }

    private fun initView() {
        val toolbar = initToolbar()
        initDrawer(toolbar)
        navigation?.addFragment(NotesListFragment(), false)

    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.action_main -> {
                    Toast.makeText(baseContext, "Main", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_settings -> {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_favorite -> {
                    Toast.makeText(baseContext, "Favorite", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_journal -> {
                    Toast.makeText(baseContext, "Journal", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_about -> {
                    Toast.makeText(baseContext, "About", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        return toolbar
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    companion object {
        private const val TAG = "myLogs"
    }
}