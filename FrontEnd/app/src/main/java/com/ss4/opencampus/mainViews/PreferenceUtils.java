package com.ss4.opencampus.mainViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ss4.opencampus.R;

/**
 * @author Axel Zumwalt
 *
 * Class to hold methods to access shared preferences.
 */
public class PreferenceUtils {

    public static final String PREFERENCE_FILE_KEY = "OpenCampusPreference";
    public static final String KEY_USER_ID = "user_id";

    /**
     * Preference Utils constructor
     */
    public PreferenceUtils() {}

    /**
     * Takes a userId, and adds it to shared preferences under the key user_id
     * @param id
     * @param context
     */
    public static void saveUserId(int id, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(KEY_USER_ID, id);
        prefsEditor.apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);
    }
}
