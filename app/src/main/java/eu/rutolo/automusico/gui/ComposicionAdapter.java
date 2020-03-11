package eu.rutolo.automusico.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.manager.Composicion;

public class ComposicionAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Composicion> composicions;

    public ComposicionAdapter(Context ctx, ArrayList<Composicion> composicions) {
        this.ctx = ctx;
        this.composicions = composicions;
    }

    @Override
    public int getCount() {
        return composicions.size();
    }

    @Override
    public Object getItem(int position) {
        return composicions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.ctx);
        view = layoutInflater.inflate(R.layout.item_composicion, null);

        // nombre
        TextView tvNom = view.findViewById(R.id.tvICompTitulo);
        tvNom.setText(composicions.get(position).getNombre());

        return view;
    }
}
