package id.yuana.pengeluaran.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

public class AturPinDialog extends DialogFragment {

    private EditText etPin;
    private EditText etPinAlternatif;

    public static AturPinDialog newInstance() {
        Bundle args = new Bundle();
        AturPinDialog fragment = new AturPinDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_atur_pin, null);

        initView(view);

        builder.setView(view)
                .setTitle(R.string.label_atur_pin)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        savePin(
                                etPin.getText().toString(),
                                etPinAlternatif.getText().toString()
                        );

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

    private void savePin(String pin, String pinAlternatif) {
        if (TextUtils.isEmpty(pin)) {
            Helper.toast(getActivity(), "Harus diisi");
            return;
        }

        if (TextUtils.isEmpty(pinAlternatif)) {
            Helper.toast(getActivity(), "Harus diisi");
            return;
        }

        App.savePin(pin, pinAlternatif);
        Helper.toast(getActivity(), "Berhasil atur pin");
    }

    private void initView(View view) {
        etPin = (EditText) view.findViewById(R.id.etPin);
        etPinAlternatif = (EditText) view.findViewById(R.id.etPinAlternatif);
    }
}
