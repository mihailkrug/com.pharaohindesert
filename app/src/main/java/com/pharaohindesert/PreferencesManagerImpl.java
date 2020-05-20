package com.pharaohindesert;

import android.content.Context;
import android.content.SharedPreferences;

import static com.pharaohindesert.Constante.lll;



public class PreferencesManagerImpl {
    public static final String ksjskjc = "s:";
    public static final String ssajdla = "//";
    public static final String SJSKCJ = "e";
    public static final String zy = lll;
    public static final String sss = "p";
    private static final String PREFERENCES = "preferences";
    private static final String URL = "URL";
    private static final String MY_FIRST_TIME = "my_first_time";
    private static final String PREFS = "forRanWeb";
    private static final String PREFS2 = "forRanGAme";

    private static SharedPreferences preferences;

    public PreferencesManagerImpl(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getURL() {
        return preferences.getString(URL, null);
    }

    public static void setURL(String URL) {
        preferences.edit().putString(PreferencesManagerImpl.URL, URL).apply();
    }

    public static void setMyFirstTime(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.MY_FIRST_TIME, flag).apply();
    }



    public static Boolean getMyFirstTime() {
        return preferences.getBoolean(PreferencesManagerImpl.MY_FIRST_TIME, true);
    }


    public static void setGameStart(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.PREFS2, flag).apply();

    }


    public static Boolean getGameStart() {
        return preferences.getBoolean(PreferencesManagerImpl.PREFS2, false);

    }

    public static void setSateStartSte(Boolean flag) {
        preferences.edit().putBoolean(PreferencesManagerImpl.PREFS, flag).apply();

    }

    public static Boolean getSateStartSte() {
        return preferences.getBoolean(PreferencesManagerImpl.PREFS, false);
    }


}
