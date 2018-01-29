package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.adapters.KategoriAdapter;
import id.yuana.pengeluaran.db.dao.KategoriDao;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.Kategori;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class TambahKategoriFragment extends Fragment implements View.OnClickListener,
        KategoriAdapter.KategoriAdapterListener {

    private Spinner spinKategori;
    private EditText etKategori;
    private Button btnKategoriSimpan;
    private ListView lvKategori;
    private KategoriAdapter mAdapter;
    private KategoriDao mDb;

    public static TambahKategoriFragment newInstance() {
        Bundle args = new Bundle();
        TambahKategoriFragment fragment = new TambahKategoriFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tambah_kategori, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.tambah_kategori);

        spinKategori = (Spinner) view.findViewById(R.id.spinKategori);
        etKategori = (EditText) view.findViewById(R.id.etKategori);
        lvKategori = (ListView) view.findViewById(R.id.lvKategori);
        btnKategoriSimpan = (Button) view.findViewById(R.id.btnKategoriSimpan);

        btnKategoriSimpan.setOnClickListener(this);
    }

    private void initData() {
        mDb = new KategoriDao(getActivity());
        mAdapter = new KategoriAdapter(getActivity(), mDb.all());
        lvKategori.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(etKategori.getText().toString())) {
            Helper.toast(getActivity(), "Harus diisi");
        } else if (spinKategori.getSelectedItemPosition() == 0) {
            Helper.toast(getActivity(), "Pilih salah satu");
        } else {
            simpanKategori();
        }
    }

    private void simpanKategori() {
        Kategori kategori = new Kategori(
                mAdapter.getCount(),
                etKategori.getText().toString(),
                getKategoriTipe()
                );
        mDb.insert(kategori);
        mAdapter.add(kategori);
        etKategori.setText("");
        spinKategori.setSelection(0);
    }

    private int getKategoriTipe() {
        return spinKategori.getSelectedItemPosition() == 1 ?
                Kategori.PEMASUKKAN : Kategori.PENGELUARAN;
    }

    @Override
    public void onLongClick(int position, final Kategori kategori) {
        Helper.prompt(getActivity(), "Info", "Hapus Data ?", new Helper.Callback() {
            @Override
            public void action() {
                mDb.delete(kategori);
                mAdapter.remove(kategori);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
