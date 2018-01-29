package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import id.yuana.pengeluaran.App;
import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.dialogs.AturPinDialog;
import id.yuana.pengeluaran.helper.Helper;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class PengaturanFragment extends Fragment implements View.OnClickListener {

    private LinearLayout llAturPin;
    private LinearLayout llNonaktifkanPin;
    private LinearLayout llTentangAplikasi;
    private LinearLayout llAturPengingat;

    public static PengaturanFragment newInstance() {
        Bundle args = new Bundle();
        PengaturanFragment fragment = new PengaturanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pengaturan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.pengaturan);

        llAturPin = (LinearLayout) view.findViewById(R.id.llAturPin);
        llNonaktifkanPin = (LinearLayout) view.findViewById(R.id.llNonaktifkanPin);
        llAturPengingat = (LinearLayout) view.findViewById(R.id.llAturPengingat);
        llTentangAplikasi = (LinearLayout) view.findViewById(R.id.llTentangAplikasi);

        llAturPin.setOnClickListener(this);
        llNonaktifkanPin.setOnClickListener(this);
        llAturPengingat.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAturPin:

                DialogFragment dialogFragment = AturPinDialog.newInstance();
                dialogFragment.show(getFragmentManager(), "DIALOG");

                break;
            case R.id.llNonaktifkanPin:

                if (App.getPin() != null) {
                    App.clearPin();
                    Helper.toast(getActivity(), "PIN berhasil dinonaktifkan");
                } else {
                    Helper.toast(getActivity(), "Anda belum mengatur PIN");
                }

                break;

            case R.id.llAturPengingat:

                Helper.toast(getActivity(), "Comming Soon");

                break;
        }
    }
}
