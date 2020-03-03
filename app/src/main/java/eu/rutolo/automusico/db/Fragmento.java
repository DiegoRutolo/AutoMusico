package eu.rutolo.automusico.db;

import java.io.File;
import java.util.ArrayList;

public class Fragmento {

    private int id;
    private String nombre;
    private File file;
    private String hash;
    private int tempo;
    private TipoEstructural te;
    private TipoFuncional tf;
    private ArrayList<Categoria> categorias;

    // region Contructores
    public Fragmento(String nombre, File file, String hash, int tempo, TipoEstructural te, TipoFuncional tf, ArrayList<Categoria> categorias) {
        this.nombre = nombre;
        this.file = file;
        this.hash = hash;
        this.tempo = tempo;
        this.te = te;
        this.tf = tf;
        this.categorias = categorias;
    }

    public Fragmento(String nombre, File file, String hash, int tempo, TipoEstructural te, TipoFuncional tf) {
        this.nombre = nombre;
        this.file = file;
        this.hash = hash;
        this.tempo = tempo;
        this.te = te;
        this.tf = tf;
        categorias = new ArrayList<>();
    }

    public Fragmento(int id, String nombre, File file, String hash, int tempo, TipoEstructural te, TipoFuncional tf, ArrayList<Categoria> categorias) {
        this.id = id;
        this.nombre = nombre;
        this.file = file;
        this.hash = hash;
        this.tempo = tempo;
        this.te = te;
        this.tf = tf;
        this.categorias = categorias;
    }

    public Fragmento(int id, String nombre, File file, String hash, int tempo, TipoEstructural te, TipoFuncional tf) {
        this.id = id;
        this.nombre = nombre;
        this.file = file;
        this.hash = hash;
        this.tempo = tempo;
        this.te = te;
        this.tf = tf;
        categorias = new ArrayList<>();
    }
    // endregion

    public void addCategoria(Categoria cat) {
        categorias.add(cat);
    }

    // region Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public File getFile() {
        return file;
    }

    public String getHash() {
        return hash;
    }

    public int getTempo() {
        return tempo;
    }

    public TipoEstructural getTe() {
        return te;
    }

    public TipoFuncional getTf() {
        return tf;
    }

    public Categoria[] getCategorias() {
        Categoria[] cats = new Categoria[categorias.size()];
        return categorias.toArray(cats);
    }
    // endregion
}
