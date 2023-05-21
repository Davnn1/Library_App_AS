package com.example.projectuas_kelompok6;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String KEY_START_TIME = "start_time";
    private static final long SESSION_TIMEOUT =30* 60 * 1000; // Waktu timeout sesi dalam milidetik (misalnya, 30 menit)

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(long startTime) {
        editor.putLong(KEY_START_TIME, startTime);
        editor.apply();
    }

    public boolean isSessionExpired() {
        long startTime = sharedPreferences.getLong(KEY_START_TIME, 0);
        long currentTime = System.currentTimeMillis();

        return currentTime - startTime > SESSION_TIMEOUT;
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}

