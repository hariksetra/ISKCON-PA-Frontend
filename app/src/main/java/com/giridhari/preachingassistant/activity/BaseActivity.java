package com.giridhari.preachingassistant.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.giridhari.preachingassistant.R;

/**
 * Created by shyam on 24/9/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String SHARED_PREF_NAME = "com.giridhari.preachingassistant.authdata";

    /**
     * Shared preferences for the entire app.
     */
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save something into the application SharedPreferences.
     * @param key The key for retrieving the value
     * @param value The value
     */
    protected void saveToSharedPreferences(String key, String value) {
        sharedPreferences.edit().putString(key, value);
    }

    /**
     * Gets a saved String from the SharedPreferences
     * @param key
     * @return
     */
    protected String getStringFromSharedPreference(String key) {
        return sharedPreferences.getString(key, null);
    }

}
