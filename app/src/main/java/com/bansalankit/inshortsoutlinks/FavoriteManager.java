package com.bansalankit.inshortsoutlinks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to handle the favorite feature of articles.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>17 Sep 2017</b></i>
 * <br><i>Modified Date : <b>17 Sep 2017</b></i>
 */
public final class FavoriteManager {
    private static final String PREFERENCE_KEY_FAV = "Favorite Articles";
    private static FavoriteManager sInstance;
    private final SharedPreferences mPrefs;
    private final Set<String> mFavorites;

    private FavoriteManager(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mFavorites = mPrefs.getStringSet(PREFERENCE_KEY_FAV, new HashSet<String>());
    }

    public static FavoriteManager getInstance(Context context) {
        if (sInstance == null) sInstance = new FavoriteManager(context);
        return sInstance;
    }

    public boolean isFavorite(String articleId) {
        return mFavorites.contains(articleId);
    }

    public boolean setFavorite(String articleId) {
        try {
            mFavorites.add(articleId);
            writeFavoriteSet();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(String articleId) {
        try {
            mFavorites.remove(articleId);
            writeFavoriteSet();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private void writeFavoriteSet() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putStringSet(PREFERENCE_KEY_FAV, mFavorites);
        editor.apply();
    }
}
