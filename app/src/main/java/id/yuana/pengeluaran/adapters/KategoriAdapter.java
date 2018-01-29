package id.yuana.pengeluaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.models.Kategori;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/3/17
 */

public class KategoriAdapter extends ArrayAdapter<Kategori> {

    private KategoriAdapterListener mCallback;

    public interface KategoriAdapterListener {
        void onLongClick(int position, Kategori kategori);
    }

    public void setListener(KategoriAdapterListener listener) {
        mCallback = listener;
    }

    public KategoriAdapter(Context context, ArrayList<Kategori> dataKategori) {
        super(context, 0, dataKategori);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_kategori, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Kategori kategori = getItem(position);

        holder.tvKategori.setText(kategori.getKategoriNama());
        holder.llItemKategori.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.onLongClick(position, kategori);
                return true;
            }
        });

        return view;
    }

    private class ViewHolder {

        private LinearLayout llItemKategori;
        private TextView tvKategori;

        public ViewHolder(View view) {
            llItemKategori = (LinearLayout) view.findViewById(R.id.llItemKategori);
            tvKategori = (TextView) view.findViewById(R.id.tvKategori);
        }
    }
}
