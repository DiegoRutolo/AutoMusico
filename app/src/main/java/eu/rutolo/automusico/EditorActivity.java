package eu.rutolo.automusico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.SliderCategoriaFragment;

public class EditorActivity extends AppCompatActivity {

    FragmentosDBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        dbh = FragmentosDBHelper.getInstance(getApplicationContext());
        LinearLayout cont = findViewById(R.id.contCategs);
        for (Categoria cat : dbh.listCategoria()) {
            SliderCategoriaFragment frag = SliderCategoriaFragment.newInstance(cat.getId(), cat.getNom());
        }
    }
}
