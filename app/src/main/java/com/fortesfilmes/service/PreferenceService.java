package com.fortesfilmes.service;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceService {


    private static PreferenceService INSTANCE;
    private static Context context;

    private static SharedPreferences sharedpreferences;

    private static final String PREFERENCES = "FORTES_MOVIES";
    private static final String SORT_CONFIG = "SORTED";

    public static PreferenceService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceService();
            sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        }
        return INSTANCE;
    }

//    public void init(Context context) {
////        PreferenceService.context = context;
////        sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//    }

    public void setSortConfig(boolean sorted) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(SORT_CONFIG, sorted);
        editor.commit();
    }

    public boolean getSortConfig() {
        return sharedpreferences.getBoolean(SORT_CONFIG, false);
    }
}