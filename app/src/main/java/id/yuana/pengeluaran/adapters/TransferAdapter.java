package id.yuana.pengeluaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.models.Transfer;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/19/17
 */

public class TransferAdapter extends ArrayAdapter<Transfer> {

    public TransferAdapter(Context context, ArrayList<Transfer> transferList) {
        super(context, 0, transferList);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_transfer, viewGroup, false);

        holder = new ViewHolder(view);

        Transfer transfer = getItem(position);

        try {
            holder.etAkunNama.setText(transfer.getAkun().getAkunNama());
            holder.etPemasukkan.setText(transfer.getPemasukkan() + "");
            holder.etPengenluaran.setText(transfer.getPengeluaran() + "");
            holder.etTotal.setText(transfer.getTotal() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public class ViewHolder {

        private final TextView etAkunNama;
        private final TextView etPemasukkan;
        private final TextView etPengenluaran;
        private final TextView etTotal;

        public ViewHolder(View view) {
            etAkunNama = (TextView) view.findViewById(R.id.etAkunNama);
            etPemasukkan = (TextView) view.findViewById(R.id.etPemasukkan);
            etPengenluaran = (TextView) view.findViewById(R.id.etPengenluaran);
            etTotal = (TextView) view.findViewById(R.id.etTotal);
        }
    }
}
