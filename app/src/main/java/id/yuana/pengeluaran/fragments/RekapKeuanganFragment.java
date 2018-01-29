package id.yuana.pengeluaran.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.yuana.pengeluaran.R;


/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class RekapKeuanganFragment extends Fragment {

    public static RekapKeuanganFragment newInstance() {
        Bundle args = new Bundle();
        RekapKeuanganFragment fragment = new RekapKeuanganFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rekap_keuangan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        getActivity().setTitle(R.string.rekap_keuangan);
    }
}
