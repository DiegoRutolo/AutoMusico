package eu.rutolo.automusico.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.db.Categoria;

public class CategoriaAdapter extends BaseAdapter {

    private ArrayList<Categoria> categorias;
    private Context ctx;

    public CategoriaAdapter(Context ctx, ArrayList<Categoria> categorias) {
        this.ctx = ctx;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categorias.get(position).getId();
    }

    public int getValue(int position) {
        View v = (View) getItem(position);
        SeekBar sb = v.findViewById(R.id.sbICvalue);
        return sb.getProgress();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.ctx);
        view = layoutInflater.inflate(R.layout.item_categoria, null);

        TextView tvNom = view.findViewById(R.id.tvICtext);
        tvNom.setText(categorias.get(position).getNom());

        return view;
    }
}
