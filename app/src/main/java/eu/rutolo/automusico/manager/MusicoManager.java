package eu.rutolo.automusico.manager;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicoManager {

    // region Singleton
    private static MusicoManager musicoManager = new MusicoManager();

    public static MusicoManager getInstance() {
        if (musicoManager == null) {
            musicoManager = new MusicoManager();
        }
        return musicoManager;
    }

    // endregion

    private Context ctx;

    private MusicoManager() {

    }

    public int getDuracionFragmento(int resId) {
        MediaPlayer mp = MediaPlayer.create(ctx, resId);
        int duracion = mp.getDuration();
        mp.release();
        return  duracion;
    }
}
