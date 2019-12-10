package com.ss4.opencampus.mainViews.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel Zumwalt
 *
 * Class to hold methods to access userId shared preferences methods.
 */
public class LoginPreferenceUtils {

    public static final String PREFERENCE_FILE_KEY = "OpenCampusPreference";
    public static final String KEY_USER_ID = "user_id";

    /**
     * Preference Utils constructor
     */
    public LoginPreferenceUtils() {}

    /**
     * Takes a userId, and adds it to shared preferences under the key user_id
     * @param id
     * @param context
     */
    public static void LoginUserId(int id, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(KEY_USER_ID, id);
        prefsEditor.apply();
    }

    public static void LogoutUserId(Context context) {
        LoginUserId(-1, context);
    }

    public static boolean isUserLoggedIn(Context context) {
        if (getUserId(context) == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public static int getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);
    }
}