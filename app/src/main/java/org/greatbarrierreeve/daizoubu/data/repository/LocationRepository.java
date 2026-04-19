package org.greatbarrierreeve.daizoubu.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import org.greatbarrierreeve.daizoubu.data.model.Location;

public class LocationRepository {

    private static final String PREF_NAME = "location_prefs";
    private static final String KEY_ID = "location_id";
    private static final String KEY_NAME = "location_name";
    private static final String KEY_ADDRESS = "location_address";

    private static final String DEFAULT_NAME = "Albert Hong Lecture Theatre (LT1)";
    private static final String DEFAULT_ADDRESS = "1.102";

    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static Location getLocation() {
        if (sharedPreferences == null) return buildDefault();
        String name = sharedPreferences.getString(KEY_NAME, DEFAULT_NAME);
        String address = sharedPreferences.getString(KEY_ADDRESS, DEFAULT_ADDRESS);
        Location location = new Location(name, address);
        location.setId(sharedPreferences.getString(KEY_ID, null));
        return location;
    }

    public static void saveLocation(Location location) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit()
                .putString(KEY_ID, location.getId())
                .putString(KEY_NAME, location.getDisplayName())
                .putString(KEY_ADDRESS, location.getAddress())
                .apply();
    }

    private static Location buildDefault() {
        return new Location(DEFAULT_NAME, DEFAULT_ADDRESS);
    }

}
