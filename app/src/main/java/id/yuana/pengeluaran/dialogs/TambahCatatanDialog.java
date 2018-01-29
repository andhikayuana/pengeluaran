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
import id.yuana.pengeluaran.db.dao.KategoriDao;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.CatatanKeuangan;
import id.yuana.pengeluaran.models.Kategori;

import java.util.Calendar;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/28/17
 */

public class TambahCatatanDialog extends DialogFragment implements View.OnClickListener {

    private static TambahCatatanListener mListener;
    private EditText etNominalCatatan;
    private Spinner spinCatatan;
    private Spinner spinKategori;
    private Spinner spinAkun;
    private EditText etDetail;
    private EditText etTglBelanja;
    private AkunDao mDbAkun;
    private KategoriDao mDbKategori;
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

    public static TambahCatatanDialog newInstance(TambahCatatanListener listener) {
        mListener = listener;
        Bundle args = new Bundle();
        TambahCatatanDialog fragment = new TambahCatatanDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tambah_catatan, null);

        initView(view);

        initData();

        builder.setView(view)
                .setTitle("Tambah Catatan")
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        simpanCatatan();
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

    private void updateDateText() {
        etTglBelanja.setText(Helper.DateToString(mCalendar));
    }

    private void initView(View view) {
        etNominalCatatan = (EditText) view.findViewById(R.id.etNominalCatatan);
        spinCatatan = (Spinner) view.findViewById(R.id.spinCatatan);
        spinKategori = (Spinner) view.findViewById(R.id.spinKategori);
        spinAkun = (Spinner) view.findViewById(R.id.spinAkun);
        etDetail = (EditText) view.findViewById(R.id.etDetail);
        etTglBelanja = (EditText) view.findViewById(R.id.etTglBelanja);
        etTglBelanja.setOnClickListener(this);
    }

    private void initData() {
        mDbAkun = new AkunDao(getActivity());
        mDbKategori = new KategoriDao(getActivity());

        ArrayAdapter<CharSequence> catatanAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.data_catatan, android.R.layout.simple_list_item_1);
        spinCatatan.setAdapter(catatanAdapter);

        ArrayAdapter<String> akunAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDbAkun.allAkunString(true));
        spinAkun.setAdapter(akunAdapter);

        ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDbKategori.allKategoriString(true));
        spinKategori.setAdapter(kategoriAdapter);

        updateDateText();
    }

    private void simpanCatatan() {
        if (TextUtils.isEmpty(etNominalCatatan.getText().toString())) {
            Helper.toast(getActivity(), "Nominal harus diisi");
            return;
        }

        if (spinCatatan.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih tipe catatan");
            return;
        }

        if (spinKategori.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih kategori dahulu");
            return;
        }

        if (spinAkun.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih akun dahulu");
            return;
        }

        if (TextUtils.isEmpty(etDetail.getText().toString())) {
            Helper.toast(getActivity(), "Masukkan detail catatan");
            return;
        }

        mListener.saveCatatan(
                new CatatanKeuangan(
                        0,
                        mDbAkun.all().get(spinAkun.getSelectedItemPosition()).getAkunId(),
                        (spinCatatan.getSelectedItemPosition() == 1) ? Kategori.PEMASUKKAN
                                : Kategori.PENGELUARAN,
                        etDetail.getText().toString(),
                        etTglBelanja.getText().toString(),
                        Long.valueOf(etNominalCatatan.getText().toString())
                )
        );
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

    public interface TambahCatatanListener {
        void saveCatatan(CatatanKeuangan catatanKeuangan);
    }
}
