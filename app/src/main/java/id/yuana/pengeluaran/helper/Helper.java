package id.yuana.pengeluaran.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/3/17
 */

public class Helper {

    public static void prompt(Context context, String title, String msg, final Callback call) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.action();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String DateToString(Calendar calendar, boolean isDbFormat) {
        String format = (isDbFormat) ? "yyyy-MM-dd" : "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("in_ID"));
        return sdf.format(calendar.getTime());
    }

    public static String DateToString(Calendar calendar) {
        return DateToString(calendar, false);
    }

    public interface Callback {
        void action();
    }

}
