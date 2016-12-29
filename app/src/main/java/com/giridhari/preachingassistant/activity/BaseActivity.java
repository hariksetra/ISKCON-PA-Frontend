package com.giridhari.preachingassistant.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shyam on 24/9/16.
 */
public class BaseActivity extends AppCompatActivity
{

    private static final String SHARED_PREF_NAME = "com.giridhari.preachingassistant.authdata";

    /**
     * Shared preferences for the entire app.
     */
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save a String into the application SharedPreferences.
     *
     * @param key   The key for retrieving the value
     * @param value The value
     */
    void saveToSharedPreferences(String key, String value)
    {
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * Save an integer into the application SharedPreferences.
     *
     * @param key   The key for retrieving the value
     * @param value The value
     */
    protected void saveToSharedPreferences(String key, int value)
    {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * Gets a saved String from the SharedPreferences
     *
     * @param key
     * @return
     */
    String getStringFromSharedPreferences(String key)
    {
        return sharedPreferences.getString(key, null);
    }

    /**
     * Gets a saved integer from the SharedPreferences
     *
     * @param key
     * @return
     */
    protected int getIntFromSharedPreferences(String key)
    {
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * Clears everything from the SharedPreference
     */
    public void clearCredentials()
    {
        sharedPreferences.edit().clear().apply();
    }
}
