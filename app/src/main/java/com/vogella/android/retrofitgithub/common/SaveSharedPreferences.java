package com.vogella.android.retrofitgithub.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {

    static final String EMAIL = "";
    static final String FIRSTNAME = "";
    static final String LASTNAME = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(EMAIL, "");
    }

    public static void setFirstname(Context ctx, String firstName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(FIRSTNAME, firstName);
        editor.apply();
    }

    public static String getFirstname(Context ctx) {
        return getSharedPreferences(ctx).getString(FIRSTNAME, "");
    }

    public static void setLastname(Context ctx, String lastName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LASTNAME, lastName);
        editor.apply();
    }

    public static String getLastname(Context ctx) {
        return getSharedPreferences(ctx).getString(LASTNAME, "");
    }

}
