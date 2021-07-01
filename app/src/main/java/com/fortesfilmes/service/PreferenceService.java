package com.fortesfilmes.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.fortesfilmes.enumeration.SortEnum;

public class PreferenceService {


    private static PreferenceService INSTANCE;

    private static SharedPreferences sharedpreferences;

    private static final String PREFERENCES = "FORTES_MOVIES";
    private static final String SORT_CONFIG = "SORT_CONFIG";

    public static PreferenceService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceService();
            sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        }
        return INSTANCE;
    }

    public void setSortConfig(SortEnum sorted) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(SORT_CONFIG, sorted.getValue());
        editor.commit();
    }

    public SortEnum getSortConfig() {
        int sort = sharedpreferences.getInt(SORT_CONFIG, SortEnum.NONE.getValue());
        return SortEnum.getEnum(sort);
    }
}