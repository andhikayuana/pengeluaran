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


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.adapters.AkunAdapter;
import id.yuana.pengeluaran.db.dao.AkunDao;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.Akun;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class TambahAkunFragment extends Fragment implements View.OnClickListener,
        AkunAdapter.AkunAdapterListener {

    private ListView lvAkun;
    private EditText etAkunNama;
    private Button btnAkunSimpan;

    private AkunAdapter mAdapter;
    private AkunDao mDb;

    public static TambahAkunFragment newInstance() {
        Bundle args = new Bundle();
        TambahAkunFragment fragment = new TambahAkunFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_akun, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.tambah_akun);

        lvAkun = (ListView) view.findViewById(R.id.lvAkun);
        etAkunNama = (EditText) view.findViewById(R.id.etAkunNama);
        btnAkunSimpan = (Button) view.findViewById(R.id.btnAkunSimpan);

        btnAkunSimpan.setOnClickListener(this);
    }

    private void simpanAkun() {
        Akun akun = new Akun(mAdapter.getCount(), etAkunNama.getText().toString());
        mDb.insert(akun);
        mAdapter.add(akun);
        etAkunNama.setText("");
    }

    private void initData() {
        mDb = new AkunDao(getActivity());
        mAdapter = new AkunAdapter(getActivity(), mDb.all());
        mAdapter.setListener(this);
        lvAkun.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(etAkunNama.getText().toString())) {
            Helper.toast(getActivity(), "Harus diisi");
        } else {
            simpanAkun();
        }
    }

    @Override
    public void onLongClick(int position, final Akun akun) {
        Helper.prompt(getActivity(), "Info", "Hapus Data ?", new Helper.Callback() {
            @Override
            public void action() {
                mDb.delete(akun);
                mAdapter.remove(akun);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
