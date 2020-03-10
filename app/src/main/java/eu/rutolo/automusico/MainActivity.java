package eu.rutolo.automusico;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Random;

import eu.rutolo.automusico.R;
import eu.rutolo.automusico.gui.ComposicionFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // cargar composiciones disponibles
        // loadCompos();
    }

    public void loadCompos() {
        ArrayList<Fragment> frags = new ArrayList<>();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < 3; i++) {
            ComposicionFragment frag = new ComposicionFragment();
            frags.add(frag);
            ft.add(R.id.gvMiniaturas, frag);
        }
        ft.commit();
    }

    public void pruebaSonidos() {

    }

    // region Botones
    public void btnAddComposicion_click(View v) {
        Intent i = new Intent(this, EditorActivity.class);
        startActivity(i);
    }
    // endregion
}
