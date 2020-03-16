package eu.rutolo.automusico.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import eu.rutolo.automusico.db.Fragmento;
import eu.rutolo.automusico.db.TipoFuncional;

public class SonidoPool extends SoundPool {

    private HashMap<Fragmento, Integer> fragmentos;

    /**
     * Constructor. Constructs a SoundPool object with the following
     * characteristics:
     *
     * @param maxStreams the maximum number of simultaneous streams for this
     *                   SoundPool object
     * @return a SoundPool object, or null if creation failed
     */
    public SonidoPool(int maxStreams) {
        super(maxStreams, AudioManager.STREAM_MUSIC, 0);
    }

    public void addFragmento(Context ctx, int resId, TipoFuncional ft) {

    }
}
