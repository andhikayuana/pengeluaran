package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.adapters.TransferAdapter;
import id.yuana.pengeluaran.db.dao.CatatanKeuanganDao;
import id.yuana.pengeluaran.dialogs.TransferDialog;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class TransferFragment extends Fragment implements View.OnClickListener,
        TransferDialog.TransferListener {

    private Button btnTransferUang;
    private ListView lvTransfer;
    private CatatanKeuanganDao mDb;
    private TransferAdapter mAdapter;

    public static TransferFragment newInstance() {
        Bundle args = new Bundle();
        TransferFragment fragment = new TransferFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();
    }

    private void initData() {
        mDb = new CatatanKeuanganDao(getActivity());
        mAdapter = new TransferAdapter(getActivity(), mDb.allTransfer());
        lvTransfer.setAdapter(mAdapter);
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.transfer);

        lvTransfer = (ListView) view.findViewById(R.id.lvTransfer);
        btnTransferUang = (Button) view.findViewById(R.id.btnTransferUang);
        btnTransferUang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialog = TransferDialog.newInstance(this);
        dialog.show(getFragmentManager(), "DIALOG");
    }

    @Override
    public void saveTransfer() {
        initData();
    }
}
