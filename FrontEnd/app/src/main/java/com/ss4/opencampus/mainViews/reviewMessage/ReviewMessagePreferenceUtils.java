package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel Zumwalt
 *
 * Class to hold methods to access shared preferences for storing review messages.
 */
public class ReviewMessagePreferenceUtils {

    public static final String PREFERENCE_FILE_KEY = "OpenCampusPreference";
    public static final String KEY_REVIEW_MESSAGE_LIST = "review_message_list";

    /**
     * Preference Utils constructor
     */
    public ReviewMessagePreferenceUtils() {}

    public static void addReviewMessageList(ArrayList<ReviewMessage> messageList, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();

        prefsEditor.putString(KEY_REVIEW_MESSAGE_LIST, gson.toJson(messageList));
        prefsEditor.apply();
    }

    public static List<ReviewMessage> getReviewMessageList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(prefs.getString(KEY_REVIEW_MESSAGE_LIST, ""), new TypeToken<List<ReviewMessage>>() {}.getType());
    }

    public static void deleteMessageList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(KEY_REVIEW_MESSAGE_LIST, "");
        prefsEditor.apply();
    }
}