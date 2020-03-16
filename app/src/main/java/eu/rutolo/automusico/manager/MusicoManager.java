package eu.rutolo.automusico.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import eu.rutolo.automusico.MainActivity;
import eu.rutolo.automusico.Utils;
import eu.rutolo.automusico.db.Categoria;
import eu.rutolo.automusico.db.Fragmento;
import eu.rutolo.automusico.db.FragmentosDBHelper;
import eu.rutolo.automusico.db.Tipo;
import eu.rutolo.automusico.db.TipoFuncional;

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

	public Fragmento[] listaFragmentos(TipoFuncional tf) {
		ArrayList<Fragmento> frags = new ArrayList<>();
		for (Fragmento f : fragmentos) {
			if (f.getTf().equals(tf)) {
				frags.add(f);
			}
		}

		Fragmento[] res = new Fragmento[frags.size()];
		return frags.toArray(res);
	}

	// https://stackoverflow.com/a/6737362
	public Object getItemPesado(HashMap<Object, Integer> items) {
		int pesoTotal = 0;
		for (int p : items.values()) {
			pesoTotal += p;
		}

		int randVal = rnd.nextInt(pesoTotal+1);
		for (Map.Entry<Object, Integer> vals : items.entrySet()) {
			randVal -= vals.getValue();
			if (randVal <= 0) {
				return vals.getKey();
			}
		}

		return null;
	}

	public Fragmento getNextFragmento(Categoria cat) {
		ArrayList<Fragmento> aplicables = new ArrayList<>();
		HashMap<Object, Integer> mapaPesos = new HashMap<>();
		for (Fragmento f : fragmentos) {
			for (Map.Entry<Categoria, Integer> e : f.getCategorias().entrySet()) {
				if (e.getKey().equals(cat)) {
					aplicables.add(f);
					mapaPesos.put(f, f.getCategorias().get(cat));
				}
			}
		}


		return (Fragmento) getItemPesado(mapaPesos);
	}

	public Categoria getRandomCateg(final Composicion comp) {
		return (Categoria) getItemPesado(new HashMap<Object, Integer>(comp.getVals()));
	}

	public void play(final Composicion comp) {
		// Cargar fragmentos
		sp = new SonidoPool(2);
		mapaIdsFragmentos = new HashMap<>();
		for (Fragmento f : fragmentos) {
			int soundId = sp.load(f.getFile().getAbsolutePath(), 1);
			mapaIdsFragmentos.put(f, soundId);
		}

		for (TipoFuncional tf : dbh.listTipoFuncional()) {
			int n = rnd.nextInt(tf.getMaxSimult()+1) + tf.getMinSimult();
			for (int i = 0; i < n; i++) {
				Fragmento f = getNextFragmento(getRandomCateg(comp));
				if (f == null) {
					continue;
				}
				int soundId = mapaIdsFragmentos.get(f);
				sp.play(soundId, 1, 1, 1, 0, 1);
				Log.d(Utils.DTAG, "reproduciendo " + soundId);
			}
		}
	}
}
