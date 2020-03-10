package eu.rutolo.automusico.manager;

import android.content.Context;
import android.media.MediaPlayer;

import eu.rutolo.automusico.db.Fragmento;
import eu.rutolo.automusico.db.FragmentosDBHelper;

public class MusicoManager {

    // region Singleton
    private static MusicoManager musicoManager;

    public static MusicoManager getInstance(Context ctx) {
        if (musicoManager == null) {
            musicoManager = new MusicoManager(ctx);
        }
        return musicoManager;
    }

    // endregion

    private Context ctx;
    private FragmentosDBHelper dbh;
    private Fragmento[] fragmentos;

    private MusicoManager(Context ctx) {
        this.ctx = ctx;
        // configurar fragmentos
        dbh = FragmentosDBHelper.getInstance(ctx);
        fragmentos = dbh.listFragmento();

    }

    public int getDuracionFragmento(int resId) {
        MediaPlayer mp = MediaPlayer.create(ctx, resId);
        int duracion = mp.getDuration();
        mp.release();
        return  duracion;
    }
}
