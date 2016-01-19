package com.jk0myn0.audio.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.jk0myn0.audio.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Keys
    public static final String KEY_PREF_SERVER_PROTOCOL = "pref_server_protocol";
    public static final String KEY_PREF_SERVER_IP = "pref_server_ip";
    public static final String KEY_PREF_SERVER_PORT = "pref_server_port";
    public static final String KEY_PREF_APP_UPDATES = "pref_key_app_updates";
    public static final String KEY_PREF_APP_SPLASH = "pref_key_app_splash";

    // Defaults
    public static String DEFAULT_PREF_SERVER_PROTOCOL;
    public static String DEFAULT_PREF_SERVER_IP;
    public static int DEFAULT_PREF_SERVER_PORT;
    public static Boolean DEFAULT_PREF_APP_UPDATES;
    public static Boolean DEFAULT_PREF_APP_SPLASH;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_page_1);

        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);

        DEFAULT_PREF_SERVER_PROTOCOL = settings.getString(KEY_PREF_SERVER_PROTOCOL, "HTTP");
        DEFAULT_PREF_SERVER_IP = settings.getString(KEY_PREF_SERVER_IP, "192.168.1.86");
        DEFAULT_PREF_SERVER_PORT = settings.getInt(KEY_PREF_SERVER_PORT, 3000);
        DEFAULT_PREF_APP_UPDATES = settings.getBoolean(KEY_PREF_APP_UPDATES, false);
        DEFAULT_PREF_APP_SPLASH = settings.getBoolean(KEY_PREF_APP_SPLASH, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    // Set summary to be the user-description for the selected value
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_SERVER_PROTOCOL)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, DEFAULT_PREF_SERVER_PROTOCOL));
        } else if (key.equals(KEY_PREF_SERVER_IP)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, DEFAULT_PREF_SERVER_IP));
        } else if (key.equals(KEY_PREF_SERVER_PORT)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(Integer.toString(sharedPreferences.getInt(key, DEFAULT_PREF_SERVER_PORT)));
        } else if (key.equals(KEY_PREF_APP_UPDATES)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(Boolean.toString(sharedPreferences.getBoolean(key, DEFAULT_PREF_APP_UPDATES)));
        } else if (key.equals(KEY_PREF_APP_SPLASH)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(Boolean.toString(sharedPreferences.getBoolean(key, DEFAULT_PREF_APP_SPLASH)));
        }
    }
}