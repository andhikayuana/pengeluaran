package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.adapters.BulananAdapter;
import id.yuana.pengeluaran.db.dao.AkunDao;
import id.yuana.pengeluaran.db.dao.CatatanKeuanganDao;
import id.yuana.pengeluaran.dialogs.TambahCatatanDialog;
import id.yuana.pengeluaran.models.CatatanKeuangan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/30/17
 */

public class BulananFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, TambahCatatanDialog.TambahCatatanListener {

    private Button btnTambahCatatan;
    private Spinner spinBulan;
    private Spinner spinTahun;
    private Spinner spinAkun;
    private ListView lvBulanan;
    private CatatanKeuanganDao mDb;
    private BulananAdapter mAdapter;
    private AkunDao mDbAkun;

    public static BulananFragment newInstance() {
        Bundle args = new Bundle();
        BulananFragment fragment = new BulananFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bulanan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.semua_catatan);

        btnTambahCatatan = (Button) view.findViewById(R.id.btnTambahCatatan);
        spinBulan = (Spinner) view.findViewById(R.id.spinBulan);
        spinTahun = (Spinner) view.findViewById(R.id.spinTahun);
        spinAkun = (Spinner) view.findViewById(R.id.spinAkun);
        lvBulanan = (ListView) view.findViewById(R.id.lvBulanan);

        btnTambahCatatan.setOnClickListener(this);
        spinBulan.setOnItemSelectedListener(this);
        spinTahun.setOnItemSelectedListener(this);
        spinAkun.setOnItemSelectedListener(this);
    }

    private void initData() {
        mDb = new CatatanKeuanganDao(getActivity());
        mDbAkun = new AkunDao(getActivity());
        mAdapter = new BulananAdapter(getActivity(), mDb.all());
        lvBulanan.setAdapter(mAdapter);

        ArrayAdapter<String> akunAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDbAkun.allAkunString(true));
        spinAkun.setAdapter(akunAdapter);

        ArrayAdapter<String> tahunAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mDb.getDates());
        spinTahun.setAdapter(tahunAdapter);

        String bulan = new SimpleDateFormat("M").format(new Date());
        spinBulan.setSelection(Integer.valueOf(bulan) - 1);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTambahCatatan) {
            DialogFragment dialogFragment = TambahCatatanDialog.newInstance(this);
            dialogFragment.show(getFragmentManager(), "DIALOG");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterData(
                spinBulan.getSelectedItemPosition()+1,
                (String) spinTahun.getSelectedItem(),
                spinAkun.getSelectedItemPosition()
        );
    }

    private void filterData(int bulan, String tahun, int spinAkunPos) {
        ArrayList<CatatanKeuangan> catatanKeuangen = (spinAkunPos == 0)
                ? mDb.allFilter(bulan, tahun)
                : mDb.allFilter(bulan, tahun, mDbAkun.all().get(spinAkunPos - 1).getAkunId());

        mAdapter = new BulananAdapter(getActivity(), catatanKeuangen);
        lvBulanan.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void saveCatatan(CatatanKeuangan catatanKeuangan) {
        catatanKeuangan.setCatatanId(mAdapter.getCount() + 1);
        mDb.insert(catatanKeuangan);
        mAdapter.add(catatanKeuangan);
    }
}
