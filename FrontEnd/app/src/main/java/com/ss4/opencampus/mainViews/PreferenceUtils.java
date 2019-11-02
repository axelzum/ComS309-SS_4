package com.ss4.opencampus.mainViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ss4.opencampus.R;

public class PreferenceUtils {

    public static final String PREFERENCE_FILE_KEY = "OpenCampusPreference";
    public static final String KEY_USER_ID = "user_id";

    public PreferenceUtils() {}

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
