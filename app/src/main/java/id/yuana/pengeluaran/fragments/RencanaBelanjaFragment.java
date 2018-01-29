package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.adapters.RencanaBelanjaAdapter;
import id.yuana.pengeluaran.db.dao.RencanaBelanjaDao;
import id.yuana.pengeluaran.dialogs.RealisasiDialog;
import id.yuana.pengeluaran.dialogs.TambahRencanaDialog;
import id.yuana.pengeluaran.helper.Helper;
import id.yuana.pengeluaran.models.RencanaBelanja;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class RencanaBelanjaFragment extends Fragment implements
        View.OnClickListener, TambahRencanaDialog.TambahRencanaListener,
        RencanaBelanjaAdapter.RencanaBelanjaListener, RealisasiDialog.RealisasiListener {

    private ListView lvRencanaBelanja;
    private RencanaBelanjaAdapter mAdapter;
    private Button btnTambahRencana;
    private Button btnRealisasi;
    private RencanaBelanjaDao mDb;

    public static RencanaBelanjaFragment newInstance() {
        Bundle args = new Bundle();
        RencanaBelanjaFragment fragment = new RencanaBelanjaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rencana_belanja,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.rencana_belanja);
        lvRencanaBelanja = (ListView) view.findViewById(R.id.lvRencanaBelanja);
        btnTambahRencana = (Button) view.findViewById(R.id.btnTambahRencana);
        btnRealisasi = (Button) view.findViewById(R.id.btnRealisasi);

        btnTambahRencana.setOnClickListener(this);
        btnRealisasi.setOnClickListener(this);
    }

    private void initData() {
        mDb = new RencanaBelanjaDao(getActivity());
        mAdapter = new RencanaBelanjaAdapter(getActivity(), mDb.all());
        mAdapter.setListener(this);
        lvRencanaBelanja.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {

        FragmentManager fm = getActivity().getSupportFragmentManager();

        DialogFragment dialog = null;

        switch (view.getId()) {
            case R.id.btnTambahRencana:

                dialog = TambahRencanaDialog.newInstance(this);

                break;
            case R.id.btnRealisasi:

                dialog = RealisasiDialog.newInstance(getTotalChecked(), this);

                break;
        }

        if (dialog != null) dialog.show(fm, "DIALOG");
    }

    private Long getTotalChecked() {
        Long total = 0L;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            RencanaBelanja item = mAdapter.getItem(i);
            if (item.isChecked()) total += item.getHarga();
        }

        return total;
    }

    @Override
    public void saveRencanaBelanja(RencanaBelanja rencanaBelanja) {
        rencanaBelanja.setRencanaBelanjaId(mAdapter.getCount());
        mDb.insert(rencanaBelanja);
        mAdapter.add(rencanaBelanja);
    }

    @Override
    public void onLongClick(int position, final RencanaBelanja rencanaBelanja) {
        Helper.prompt(getActivity(), "Info", "Hapus Data ?", new Helper.Callback() {
            @Override
            public void action() {
                mDb.delete(rencanaBelanja);
                mAdapter.remove(rencanaBelanja);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void deleteRencanaBelanja() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            RencanaBelanja item = mAdapter.getItem(i);
            if (item.isChecked()) {
                mDb.delete(item);
                mAdapter.notifyDataSetChanged();
            }
        }

        initData();
    }
}
