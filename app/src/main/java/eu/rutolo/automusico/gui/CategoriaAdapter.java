package eu.rutolo.automusico.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.Utils;
import eu.rutolo.automusico.db.Categoria;

public class CategoriaAdapter extends BaseAdapter {

    private ArrayList<Categoria> categorias;
    private HashMap<Categoria, Integer> valores;
    private Context ctx;

    public CategoriaAdapter(Context ctx, ArrayList<Categoria> categorias) {
        this.ctx = ctx;
        this.categorias = categorias;
        valores = new HashMap<>();
        for (Categoria c : categorias) {
            valores.put(c, 50);
        }
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
        return valores.get(categorias.get(position));
    }

    public void setValue(ViewGroup parentView, int position, int value) {
        // valor en el mapa
        valores.put(categorias.get(position), value);

        // valor en la barra
        SeekBar sb = (SeekBar) Utils.getViewsByTag((ViewGroup) parentView, "barra").get(position);
        sb.setProgress(value);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.ctx);
        view = layoutInflater.inflate(R.layout.item_categoria, null);

        // nombre
        TextView tvNom = view.findViewById(R.id.tvICtext);
        tvNom.setText(categorias.get(position).getNom());

        // listener para actualizar valores
        SeekBar sb = view.findViewById(R.id.sbICvalue);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valores.put(categorias.get(position), progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}
