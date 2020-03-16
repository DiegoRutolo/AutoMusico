package eu.rutolo.automusico.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import eu.rutolo.automusico.MainActivity;
import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.Fragmento;
import eu.rutolo.automusico.db.FragmentosDBHelper;

public class MusicoManager {

    // region Singleton
    private static MusicoManager musicoManager;

    public static MusicoManager getInstance() throws Exception {
        if (musicoManager == null) {
            throw new Exception("MusicoManager no inicializado");
        }
        return musicoManager;
    }

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

    private Random rnd;
    private SonidoPool sp;
    private HashMap<Fragmento, Integer> mapaIdsFragmentos;

    private MusicoManager(Context ctx) {
        this.ctx = ctx;
        rnd = new Random();
        // configurar fragmentos
        dbh = FragmentosDBHelper.getInstance(ctx);
        fragmentos = dbh.listFragmento();
        for (Fragmento f : fragmentos) {
            f.configurar(MainActivity.fragsDir, getDuracionFragmento(f.getFileName()));
        }
    }

    public int getDuracionFragmento(String nombre) {
        MediaPlayer mp = MediaPlayer.create(ctx, Uri.fromFile(new File(MainActivity.fragsDir, nombre)));
        int duracion = mp.getDuration();
        mp.release();
        return  duracion;
    }

    public Fragmento getNextFragmento(Categoria cat, int config) {
        ArrayList<Fragmento> aplicables = new ArrayList<>();
        for (Fragmento f : fragmentos) {
            for (Map.Entry<Categoria, Integer> e : f.getCategorias().entrySet()) {
                if (e.getKey().equals(cat)) {
                    aplicables.add(f);
                }
            }
        }

        int intensidadTotal = 0;
        for (Fragmento f : aplicables) {
            intensidadTotal += f.getCategorias().get(cat);
        }

        // https://stackoverflow.com/a/6737362
        int randIndex = -1;
        int randVal = rnd.nextInt(intensidadTotal);
        for (int i = 0; i < aplicables.size(); i++) {
            randVal -= aplicables.get(i).getCategorias().get(cat);
            if (randVal <= 0) {
                randIndex = i;
                break;
            }
        }

        return aplicables.get(randIndex);
    }

    public void play(final Composicion comp) {
        // Cargar fragmentos
        sp = new SonidoPool(2);
        mapaIdsFragmentos = new HashMap<>();
        for (Fragmento f : fragmentos) {
            int soundId = sp.load(f.getFile().getAbsolutePath(), 1);
            mapaIdsFragmentos.put(f, soundId);
        }


    }
}
