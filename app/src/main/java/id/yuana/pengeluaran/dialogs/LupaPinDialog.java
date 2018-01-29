package id.yuana.pengeluaran.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import id.yuana.pengeluaran.App;
import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.helper.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/17/17
 */

public class LupaPinDialog extends DialogFragment {

    private EditText etPinAlternatif;

    public static LupaPinDialog newInstance() {
        Bundle args = new Bundle();
        LupaPinDialog fragment = new LupaPinDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_lupa_pin, null);

        initView(view);

        builder.setView(view)
                .setTitle("Lupa PIN")
                .setPositiveButton("Ingatkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkPin(etPinAlternatif.getText().toString());
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }

    private void checkPin(String pinAlternatif) {
        if (pinAlternatif.equals(App.getPinAlternatif())) {
            Helper.toast(getActivity(), "PIN Anda : " + App.getPin());
        } else {
            Helper.toast(getActivity(), "PIN alternatif Anda salah");
        }
    }

    private void initView(View view) {
        etPinAlternatif = (EditText) view.findViewById(R.id.etPinAlternatif);
    }
}
