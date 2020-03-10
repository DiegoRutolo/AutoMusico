package eu.rutolo.automusico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import eu.rutolo.automusico.db.FragmentosDBHelper;

public class MainActivity extends AppCompatActivity {

    FragmentosDBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = FragmentosDBHelper.getInstance(getApplicationContext());
    }

    // region Botones
    public void btnAddComposicion_click(View v) {
        Intent i = new Intent(this, EditorActivity.class);
        startActivity(i);
    }
    // endregion
}
