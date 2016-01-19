package com.jk0myn0.audio.knobify;

/**
 * Created by Root on 02/01/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jk0myn0.audio.R;
import com.jk0myn0.audio.equalizer.EqualizerActivity;
import com.jk0myn0.audio.helper.MyPreferenceManager;
import com.jk0myn0.audio.helper.SQLiteHandler;
import com.jk0myn0.audio.record.SoundRecorderActivity;
import com.jk0myn0.audio.services.FloatingHeadService;
import com.jk0myn0.audio.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                Intent i = new Intent(getApplicationContext(), FloatingHeadService.class);
                if (clickCount%2 != 0){
                    startService(i);
                } else {
                    stopService(i);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                //Toast.makeText(this, "ADD!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {
            Intent sc = new Intent(getApplicationContext(), SoundCloudActivity.class);
            startActivity(sc);

        } else if (id == R.id.nav_equalizer) {
            // Display the fragment as the main content.
            Intent eq = new Intent(getApplicationContext(), EqualizerActivity.class);
            startActivity(eq);

        } else if (id == R.id.nav_manage) {
            Intent record = new Intent(getApplicationContext(), SoundRecorderActivity.class);
            startActivity(record);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, check this app out: http://goo.gl/");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_mail) {
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("alberto.schiabel@androidiani.com") +
                    "?subject=" + Uri.encode("Mixer Contact") +
                    "&body=" + Uri.encode("Hi Alberto, I've just seen your app, here's what I think about it: ");
            Uri uri = Uri.parse(uriText);

            send.setData(uri);

            startActivity(Intent.createChooser(send, "Send Email..."));
            return true;

        } else if (id == R.id.nav_logout) {
            logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * settings_page_1 Clears the user data from sqlite users table
     * */

    private void logoutUser() {

        MyPreferenceManager session;
        SQLiteHandler db;

        session = new MyPreferenceManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        session.setLogin(false);
        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}