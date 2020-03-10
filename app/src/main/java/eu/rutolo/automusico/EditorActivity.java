package eu.rutolo.automusico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.CategoriaAdapter;
import eu.rutolo.automusico.gui.SliderCategoriaFragment;

public class EditorActivity extends AppCompatActivity {

    FragmentosDBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        dbh = FragmentosDBHelper.getInstance(getApplicationContext());
        ArrayList<Categoria> categs = new ArrayList<Categoria>();
        for (Categoria c : dbh.listCategoria()) {
            categs.add(c);
        }

        // mostrar categorias
        ListView lv = findViewById(R.id.lvCategorias);
        CategoriaAdapter adapter = new CategoriaAdapter(this, categs);
        lv.setAdapter(adapter);
    }
}
