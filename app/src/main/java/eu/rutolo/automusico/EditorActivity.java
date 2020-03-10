package eu.rutolo.automusico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.CategoriaAdapter;
import eu.rutolo.automusico.gui.SliderCategoriaFragment;

public class EditorActivity extends AppCompatActivity {

    Random rnd;
    FragmentosDBHelper dbh;
    CategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        rnd = new Random();
        dbh = FragmentosDBHelper.getInstance(getApplicationContext());
        ArrayList<Categoria> categs = new ArrayList<Categoria>();
        for (Categoria c : dbh.listCategoria()) {
            categs.add(c);
        }

        // mostrar categorias
        ListView lv = findViewById(R.id.lvCategorias);
        adapter = new CategoriaAdapter(this, categs);
        lv.setAdapter(adapter);
    }

    // region Botones
    public void btnRandom_click(View v) {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.setValue(i, rnd.nextInt(101));
        }
    }
    // endregion
}
