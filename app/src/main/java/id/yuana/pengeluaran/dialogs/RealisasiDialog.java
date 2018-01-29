package id.yuana.pengeluaran.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
 * @since 4/5/17
 */

public class RealisasiDialog extends DialogFragment implements View.OnClickListener {

    private static RealisasiListener mListener;
    private Calendar mCalendar = Calendar.getInstance();
    private Spinner spinAkun;
    private EditText etRealisasiBelanja;
    private EditText etTotalBelanja;
    private EditText etTglBelanja;
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
    private long mTotal;
    private AkunDao mDb;
    private CatatanKeuanganDao mDbCatatan;

    public static RealisasiDialog newInstance(Long totalChecked, RealisasiListener listener) {
        mListener = listener;
        Bundle args = new Bundle();
        args.putLong("TOTAL", totalChecked);
        RealisasiDialog fragment = new RealisasiDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateDateText() {
        etTglBelanja.setText(Helper.DateToString(mCalendar));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_realisasi_belanja, null);

        initView(view);

        initData();

        builder.setView(view)
                .setTitle("Realisasi Belanja")
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        simpanRealisasiBelanja();

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

    private void initData() {
        mDb = new AkunDao(getActivity());
        mDbCatatan = new CatatanKeuanganDao(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDb.allAkunString(true));
        spinAkun.setAdapter(adapter);
        mTotal = getArguments().getLong("TOTAL");
        etTotalBelanja.setText(String.valueOf(mTotal));
        updateDateText();
    }

    private void simpanRealisasiBelanja() {
        if (spinAkun.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih Akun terlebih dahulu");
            return;
        }
        if (TextUtils.isEmpty(etRealisasiBelanja.getText().toString())) {
            Helper.toast(getActivity(), "Realisasi belanja harus diisi");
            return;
        }
        if (Integer.parseInt(etTotalBelanja.getText().toString()) == 0) {
            Helper.toast(getActivity(), "Harus pilih rencana belanja");
            return;
        }

        int id = 1;
        if (mDbCatatan.all().size() > 0) {
            id = mDbCatatan.all().size() + 1;
        }
        Akun akun = mDb.all().get(spinAkun.getSelectedItemPosition() - 1);
        CatatanKeuangan catatanKeuangan = new CatatanKeuangan(
                id,
                akun.getAkunId(),
                Kategori.PENGELUARAN,
                etRealisasiBelanja.getText().toString(),
                etTglBelanja.getText().toString(),
                Long.valueOf(etTotalBelanja.getText().toString())
        );

        mDbCatatan.insert(catatanKeuangan);
        mListener.deleteRencanaBelanja();
        Helper.toast(getActivity(), "Berhasil melakukan realisasi belanja");
    }

    private void initView(View view) {
        spinAkun = (Spinner) view.findViewById(R.id.spinAkun);
        etRealisasiBelanja = (EditText) view.findViewById(R.id.etRealisasiBelanja);
        etTotalBelanja = (EditText) view.findViewById(R.id.etTotalBelanja);
        etTglBelanja = (EditText) view.findViewById(R.id.etTglBelanja);
        etTglBelanja.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etTglBelanja) {
            new DatePickerDialog(getActivity(), date,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    }

    public interface RealisasiListener {
        void deleteRencanaBelanja();
    }
}
