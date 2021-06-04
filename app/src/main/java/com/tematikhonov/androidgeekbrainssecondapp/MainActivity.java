package com.tematikhonov.androidgeekbrainssecondapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tematikhonov.androidgeekbrainssecondapp.data.Navigation;
import com.tematikhonov.androidgeekbrainssecondapp.data.Publisher;
import com.tematikhonov.androidgeekbrainssecondapp.ui.NotesListFragment;
import com.tematikhonov.androidgeekbrainssecondapp.ui.StartFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";
    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initView();
        getNavigation().addFragment(StartFragment.newInstance(), false);

    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_main:
                        Toast.makeText(getBaseContext(), "Main", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_settings:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_favorite:
                        Toast.makeText(getBaseContext(), "Favorite", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_journal:
                        Toast.makeText(getBaseContext(), "Journal", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_about:
                        Toast.makeText(getBaseContext(), "About", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}