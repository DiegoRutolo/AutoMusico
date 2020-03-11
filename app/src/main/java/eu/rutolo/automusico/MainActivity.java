package eu.rutolo.automusico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.ComposicionAdapter;
import eu.rutolo.automusico.manager.Composicion;

public class MainActivity extends AppCompatActivity {

    private ComposicionAdapter composicionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscarComposiciones();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarComposiciones();
    }

    private void buscarComposiciones() {
        FragmentosDBHelper.getInstance(getApplicationContext());
        ArrayList<Composicion> composiciones = new ArrayList<>();
        composicionAdapter = new ComposicionAdapter(this, composiciones);

        File dir = new File(getFilesDir(), Utils.SUBDIR_COMPOSICIONES);
        if (dir.list() == null) {
            return;
        }

        for (String fileName : dir.list()) {
            composiciones.add(Composicion.parseComposicion(new File(dir, fileName)));
        }
        GridView gv = findViewById(R.id.gvComposiciones);
        gv.setAdapter(composicionAdapter);
    }

    // region Botones
    public void btnAddComposicion_click(View v) {
        Intent i = new Intent(this, EditorActivity.class);
        startActivity(i);
    }
    // endregion
}
