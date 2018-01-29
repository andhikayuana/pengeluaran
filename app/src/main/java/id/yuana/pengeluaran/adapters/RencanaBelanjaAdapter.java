package id.yuana.pengeluaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.models.RencanaBelanja;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/3/17
 */

public class RencanaBelanjaAdapter extends ArrayAdapter<RencanaBelanja> {

    private RencanaBelanjaListener mCallback;

    public RencanaBelanjaAdapter(Context context, ArrayList<RencanaBelanja> dataRencanaBelanja) {
        super(context, 0, dataRencanaBelanja);
    }

    public void setListener(RencanaBelanjaListener listener) {
        mCallback = listener;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_rencana_belanja, viewGroup, false);
        holder = new ViewHolder(view);

        final RencanaBelanja rencanaBelanja = getItem(position);

        holder.cbRencanaBelanja.setChecked(rencanaBelanja.isChecked());
        holder.cbRencanaBelanja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rencanaBelanja.setChecked(b);
            }
        });
        holder.tvRencanaBelanjaNamaBarang.setText(rencanaBelanja.getNamaBarang());
        holder.tvRencanaBelanjaHarga.setText("Rp " + rencanaBelanja.getHarga());
        holder.llItemRencanaBelanja.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.onLongClick(position, rencanaBelanja);
                return true;
            }
        });

        return view;
    }

    public interface RencanaBelanjaListener {
        void onLongClick(int position, RencanaBelanja rencanaBelanja);
    }

    private class ViewHolder {

        private CheckBox cbRencanaBelanja;
        private TextView tvRencanaBelanjaNamaBarang;
        private TextView tvRencanaBelanjaHarga;
        private LinearLayout llItemRencanaBelanja;

        public ViewHolder(View view) {
            cbRencanaBelanja = (CheckBox) view.findViewById(R.id.cbRencanaBelanja);
            tvRencanaBelanjaNamaBarang = (TextView) view.findViewById(R.id.tvRencanaBelanjaNamaBarang);
            tvRencanaBelanjaHarga = (TextView) view.findViewById(R.id.tvRencanaBelanjaHarga);
            llItemRencanaBelanja = (LinearLayout) view.findViewById(R.id.llItemRencanaBelanja);
        }
    }
}
