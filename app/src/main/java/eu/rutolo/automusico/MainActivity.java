package eu.rutolo.automusico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import eu.rutolo.automusico.db.Fragmento;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.ComposicionAdapter;
import eu.rutolo.automusico.manager.Composicion;
import eu.rutolo.automusico.manager.MusicoManager;

public class MainActivity extends AppCompatActivity {

    public static File fragsDir;

    private ComposicionAdapter composicionAdapter;
    private MusicoManager musicoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // region Copiar fragmentos
        FragmentosDBHelper dbh = FragmentosDBHelper.getInstance(getApplicationContext());
        fragsDir = new File(getFilesDir(), Utils.SUBDIR_FRAGMENTOS);
        fragsDir = getExternalFilesDirs(Environment.DIRECTORY_MUSIC)[0];

        // configurar fragmentos
        for (Fragmento f : dbh.listFragmento()) {
            File fragFile = new File(fragsDir, f.getFileName());
            try {
                Log.d(Utils.DTAG, "Probando " + fragFile.getAbsolutePath());
                if (fragFile.createNewFile()) {
                    Log.d(Utils.DTAG, "No existe, creando");

                    InputStream is = getAssets().open(f.getFileName());
                    OutputStream os = new FileOutputStream(fragFile);

                    int tamRead;
                    byte[] buffer = new byte[2048];
                    while ((tamRead = is.read(buffer)) > 0) {
                        os.write(buffer, 0, tamRead);
                    }
                    os.flush();
                    os.close();
                    is.close();

                    Log.d(Utils.DTAG, "Copiado correctamente");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // endregion

        musicoManager = MusicoManager.getInstance(getApplicationContext());
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
