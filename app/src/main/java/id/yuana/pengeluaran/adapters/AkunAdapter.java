package id.yuana.pengeluaran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import id.yuana.pengeluaran.R;
import id.yuana.pengeluaran.models.Akun;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class AkunAdapter extends ArrayAdapter<Akun> {

    private AkunAdapterListener mCallback;

    public interface AkunAdapterListener {
        void onLongClick(int position, Akun akun);
    }

    public void setListener(AkunAdapterListener listener) {
        mCallback = listener;
    }

    public AkunAdapter(Context context, ArrayList<Akun> dataAkun) {
        super(context, 0, dataAkun);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_akun, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Akun akun = getItem(position);

        holder.tvAkun.setText(akun.getAkunNama());
        holder.llItemAkun.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.onLongClick(position, akun);
                return true;
            }
        });

        return view;
    }

    private class ViewHolder {

        private LinearLayout llItemAkun;
        private TextView tvAkun;

        public ViewHolder(View view) {
            llItemAkun = (LinearLayout) view.findViewById(R.id.llItemAkun);
            tvAkun = (TextView) view.findViewById(R.id.tvAkun);
        }
    }
}
