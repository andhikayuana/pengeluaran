package id.yuana.pengeluaran.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import id.yuana.pengeluaran.App;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/17/17
 */

public class SharedPrefHelper {

    private static SharedPreferences getPref() {
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

    public static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }

    public static void saveString(String key, String val) {
        getEditor().putString(key, val).commit();
    }

    public static String getString(String key) {
        return getPref().getString(key, null);
    }
}
