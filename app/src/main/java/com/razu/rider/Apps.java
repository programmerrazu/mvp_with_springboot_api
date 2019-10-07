package com.razu.rider;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.razu.rider.entity.User;
import com.razu.rider.networks.helper.ApiConstants;

public class Apps extends Application implements ApiConstants {

    private static Apps instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Apps getInstance() {
        if (instance == null)
            instance = new Apps();
        return instance;
    }

    public static void saveToSession(User userInfo) {
        SharedPreferences sp = getInstance().getApplicationContext().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(ACCESS_TOKEN, userInfo.getAccessToken());
        editor.putString(REFRESH_TOKEN, userInfo.getRefreshToken());
        editor.putString(USER_ID, userInfo.getUserId());
        editor.putString(PHONE, userInfo.getPhone());
        editor.putString(FIRST_NAME, userInfo.getFirstName());
        editor.putString(LAST_NAME, userInfo.getLastName());
        editor.putString(FULL_NAME, userInfo.getFullName());
        editor.apply();
    }

    public static boolean checkSession() {
        SharedPreferences sp = getInstance().getApplicationContext().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        String aToken = sp.getString(ACCESS_TOKEN, DOUBLE_QUOTES);
        String rfToken = sp.getString(REFRESH_TOKEN, DOUBLE_QUOTES);
        String riderId = sp.getString(USER_ID, DOUBLE_QUOTES);
        String phone = sp.getString(PHONE, DOUBLE_QUOTES);
        String fName = sp.getString(FIRST_NAME, DOUBLE_QUOTES);
        String lName = sp.getString(LAST_NAME, DOUBLE_QUOTES);
        String fullName = sp.getString(FULL_NAME, DOUBLE_QUOTES);
        if (!riderId.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        SharedPreferences sp = getInstance().getApplicationContext().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}