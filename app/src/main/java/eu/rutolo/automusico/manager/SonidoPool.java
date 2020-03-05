package eu.rutolo.automusico.manager;

import android.media.AudioManager;
import android.media.SoundPool;

public class SonidoPool extends SoundPool {
    /**
     * Constructor. Constructs a SoundPool object with the following
     * characteristics:
     *
     * @param maxStreams the maximum number of simultaneous streams for this
     *                   SoundPool object
     * @return a SoundPool object, or null if creation failed
     * @deprecated use {@link Builder} instead to create and configure a
     * SoundPool instance
     */
    public SonidoPool(int maxStreams) {
        super(maxStreams, AudioManager.STREAM_MUSIC, 0);
    }
}
