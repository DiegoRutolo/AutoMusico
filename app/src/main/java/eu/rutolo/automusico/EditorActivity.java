package eu.rutolo.automusico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.gui.CategoriaAdapter;
import eu.rutolo.automusico.manager.MusicoManager;

public class EditorActivity extends AppCompatActivity {

    Random rnd;
    FragmentosDBHelper dbh;
    CategoriaAdapter adapter;
    MusicoManager musicoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        rnd = new Random();
        musicoManager = MusicoManager.getInstance(getApplicationContext());
        dbh = FragmentosDBHelper.getInstance(getApplicationContext());
        ArrayList<Categoria> categs = new ArrayList<>();
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
            adapter.setValue((ViewGroup) findViewById(R.id.lvCategorias), i, rnd.nextInt(101));
        }
    }

    public void btnPlay_click(View v) {
        Toast.makeText(getApplicationContext(), "Primer valor: " + adapter.getValue(0), Toast.LENGTH_SHORT).show();
        musicoManager.play(adapter.getComposicion());
    }

    public void btnSave_click(View v) {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflado = inflater.inflate(R.layout.dialog_nombre_composicion, null);
        final EditText txeNom = inflado.findViewById(R.id.txeDialogNombre);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.dialog_nombreComposicion_titulo);
        adb.setCancelable(true);
        adb.setView(inflado);
        adb.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = txeNom.getText().toString();
                if (txt.length() <= 0) {
                    Toast.makeText(getApplicationContext(), R.string.toast_cancel, Toast.LENGTH_SHORT).show();
                    return;
                }
                saveFile(txt);
            }
        });
        adb.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.create().show();
    }
    // endregion

    private void saveFile(String fileName) {
        adapter.getComposicion().setNombre(fileName);

        final File FILE = new File(getFilesDir(), fileName.replace(" ", "_") + ".xml");
        // comprobar si existe
        if (FILE.exists()) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.dialog_sobrescribir_title);
            adb.setMessage(R.string.dialog_sobrescribir_msg);
            adb.setCancelable(false);
            adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveFile(FILE);
                }
            });
            adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), R.string.toast_cancel, Toast.LENGTH_SHORT).show();
                }
            });
            adb.create().show();
        } else {
            saveFile(FILE);
        }
    }

    private void saveFile(final File file) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                adapter.getComposicion().saveToXml(file);
            }
        };
        hilo.start();
        finish();
    }
}
