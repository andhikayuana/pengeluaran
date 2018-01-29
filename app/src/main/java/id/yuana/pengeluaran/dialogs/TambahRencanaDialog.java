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


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.RencanaBelanja;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/5/17
 */

public class TambahRencanaDialog extends DialogFragment {

    private static TambahRencanaListener mListener;
    private EditText etNamaBarang;
    private EditText etPerkiraanHarga;

    public static TambahRencanaDialog newInstance(TambahRencanaListener listener) {
        mListener = listener;
        Bundle args = new Bundle();
        TambahRencanaDialog fragment = new TambahRencanaDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tambah_rencana, null);

        initView(view);

        builder.setView(view)
                .setTitle("Tambah Rencana")
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        simpanRencanaBelanja();

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

    private void simpanRencanaBelanja() {

        if (TextUtils.isEmpty(etNamaBarang.getText().toString())) {
            Helper.toast(getActivity(), "Nama Barang tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(etPerkiraanHarga.getText().toString())) {
            Helper.toast(getActivity(), "Perkiraan Harga tidak boleh kosong");
            return;
        }

        mListener.saveRencanaBelanja(
                new RencanaBelanja(
                        0,
                        etNamaBarang.getText().toString(),
                        Long.valueOf(etPerkiraanHarga.getText().toString()),
                        false
                )
        );
    }

    private void initView(View view) {
        etNamaBarang = (EditText) view.findViewById(R.id.etNamaBarang);
        etPerkiraanHarga = (EditText) view.findViewById(R.id.etPerkiraanHarga);
    }

    public interface TambahRencanaListener {
        void saveRencanaBelanja(RencanaBelanja rencanaBelanja);
    }
}
