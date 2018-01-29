package id.yuana.pengeluaran;

import android.app.Application;
import android.content.Context;

import id.yuana.pengeluaran.helper.SharedPrefHelper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/17/17
 */

public class App extends Application {

    public static final String PIN = "PIN";
    public static final String PIN_ALTERNATIF = "PIN_ALTERNATIF";

    private static Context context;

    public static synchronized Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void savePin(String pin, String pinAlternatif) {
        SharedPrefHelper.saveString(App.PIN, pin);
        SharedPrefHelper.saveString(App.PIN_ALTERNATIF, pinAlternatif);
    }

    public static String getPin() {
        return SharedPrefHelper.getString(App.PIN);
    }

    public static void clearPin() {
        SharedPrefHelper.getEditor().clear().commit();
    }

    public static String getPinAlternatif() {
        return SharedPrefHelper.getString(App.PIN_ALTERNATIF);
    }
}
