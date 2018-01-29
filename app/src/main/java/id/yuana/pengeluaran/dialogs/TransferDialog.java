package id.yuana.pengeluaran.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.db.dao.AkunDao;
import id.yuana.pengeluaran.db.dao.CatatanKeuanganDao;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.Akun;
import id.yuana.pengeluaran.models.CatatanKeuangan;
import id.yuana.pengeluaran.models.Kategori;

import java.util.Calendar;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/26/17
 */

public class TransferDialog extends DialogFragment implements View.OnClickListener {

    private static TransferListener mListener;
    private Spinner spinAkunAsal;
    private Spinner spinAkunTujuan;
    private EditText etNominalTransfer;
    private EditText etTglTransfer;
    private AkunDao mDb;
    private CatatanKeuanganDao mDbCatatan;
    private Calendar mCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateText();
        }

    };

    public static TransferDialog newInstance(TransferListener listener) {
        mListener = listener;
        Bundle args = new Bundle();
        TransferDialog fragment = new TransferDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateDateText() {
        etTglTransfer.setText(Helper.DateToString(mCalendar));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_transfer, null);

        initView(view);

        initData();

        builder.setView(view)
                .setTitle("Transfer Antar Akun")
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveTransfer();

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

    private void saveTransfer() {
        if (spinAkunAsal.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih Akun asal terlebih dahulu");
            return;
        }

        if (spinAkunTujuan.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih Akun tujuan terlebih dahulu");
            return;
        }

        if (Long.valueOf(etNominalTransfer.getText().toString()) == 0L) {
            Helper.toast(getActivity(), "Masukkan nominal");
            return;
        }

        Akun akun = mDb.all().get(spinAkunTujuan.getSelectedItemPosition() - 1);
        CatatanKeuangan catatanKeuangan = new CatatanKeuangan(
                mDbCatatan.all().size() + 1,
                akun.getAkunId(),
                Kategori.PEMASUKKAN,
                "Pemasukkan",
                etTglTransfer.getText().toString(),
                Long.valueOf(etNominalTransfer.getText().toString())
        );

        mDbCatatan.insert(catatanKeuangan);
        mListener.saveTransfer();
        Helper.toast(getActivity(), "Berhasil transfer");
    }

    private void initData() {
        mDb = new AkunDao(getActivity());
        mDbCatatan = new CatatanKeuanganDao(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDb.allAkunString(true));
        spinAkunAsal.setAdapter(adapter);
        spinAkunTujuan.setAdapter(adapter);

        updateDateText();
    }

    private void initView(View view) {
        spinAkunAsal = (Spinner) view.findViewById(R.id.spinAkunAsal);
        spinAkunTujuan = (Spinner) view.findViewById(R.id.spinAkunTujuan);
        etNominalTransfer = (EditText) view.findViewById(R.id.etNominalTransfer);
        etTglTransfer = (EditText) view.findViewById(R.id.etTglTransfer);
        etTglTransfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etTglTransfer) {
            new DatePickerDialog(getActivity(), date,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    }

    public interface TransferListener {
        void saveTransfer();
    }
}
