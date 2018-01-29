package id.yuana.pengeluaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.models.CatatanKeuangan;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/27/17
 */

public class BulananAdapter extends ArrayAdapter<CatatanKeuangan> {

    public BulananAdapter(Context context, ArrayList<CatatanKeuangan> catatanKeuanganList) {
        super(context, 0, catatanKeuanganList);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_bulanan, viewGroup, false);

        holder = new ViewHolder(view);

        CatatanKeuangan catatanKeuangan = getItem(position);

        holder.tvTglBulanan.setText(catatanKeuangan.getTglCatatan());
        holder.tvKategori.setText(catatanKeuangan.getKategoriString());
        holder.tvDetail.setText(catatanKeuangan.getDetail());
        holder.tvNominal.setText(catatanKeuangan.getNominal() + "");

        return view;
    }

    private class ViewHolder {

        private final TextView tvTglBulanan;
        private final TextView tvKategori;
        private final TextView tvDetail;
        private final TextView tvNominal;

        public ViewHolder(View view) {
            tvTglBulanan = (TextView) view.findViewById(R.id.tvTglBulanan);
            tvKategori = (TextView) view.findViewById(R.id.tvKategori);
            tvDetail = (TextView) view.findViewById(R.id.tvDetail);
            tvNominal = (TextView) view.findViewById(R.id.tvNominal);
        }
    }
}
