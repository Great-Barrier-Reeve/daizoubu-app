package org.greatbarrierreeve.daizoubu.data.repository;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoRepository {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TOKEN = "session_token";

    private static SharedPreferences sharedPreferences;

    public static void init(Context context){
        if(sharedPreferences ==null){
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    // Returns the Firebase UID
    public static String getUserId() {
        if(sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // Returns the ID token
    public static String getIdToken() {
        if(sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    // Helper to check if user is actually logged in
    public static boolean isLoggedIn() {
        if (getUserId() != null && getIdToken() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.getIdToken(false).addOnSuccessListener(getTokenResult -> {
                    String freshToken = getTokenResult.getToken();
                    sharedPreferences.edit().putString(KEY_TOKEN, freshToken)
                            .putString(KEY_USER_ID, user.getUid())
                            .apply();
                });
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    // Useful for a "Logout" button later
    public static void clearSession() {
        if(sharedPreferences != null){
            sharedPreferences.edit().clear().apply();

        }
    }

}
