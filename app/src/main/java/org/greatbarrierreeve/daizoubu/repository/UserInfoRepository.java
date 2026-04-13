package org.greatbarrierreeve.daizoubu.repository;
import android.content.Context;
import android.content.SharedPreferences;
public class UserInfoRepository {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TOKEN = "session_token";

    // Returns the Firebase UID
    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_ID, null);
    }

    // Returns the ID token
    public static String getIdToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null);
    }

    // Helper to check if user is actually logged in
    public static boolean isLoggedIn(Context context) {
        return getUserId(context) != null && getIdToken(context) != null;
    }

    // Useful for a "Logout" button later
    public static void clearSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

}
