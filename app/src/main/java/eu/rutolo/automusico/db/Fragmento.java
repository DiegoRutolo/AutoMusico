package eu.rutolo.automusico.db;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Cada objeto fragmento representa una pista de música.
 * El helper de la DB utiliza el constructor, pero para poder usarlo es necesario invocar el método
 * <code>configurar</code> desde el <code>MusicoManager</code>.
 */
public class Fragmento {

    private int id;
    private String nombre;
    private File file;
    private String hash;
    private int tempo;
    private TipoEstructural te;
    private TipoFuncional tf;

    private HashMap<Categoria, Integer> categorias;
    private int duracion;
    private String fileName;

    private boolean configurado;

    Fragmento(int id, String nombre, String fileName, String hash, int tempo, TipoEstructural te, TipoFuncional tf) {
        this.id = id;
        this.nombre = nombre;
        this.fileName = fileName;
        this.hash = hash;
        this.tempo = tempo;
        this.te = te;
        this.tf = tf;

        configurado = false;
        categorias = new HashMap<>();
    }

    public void addCategoria(Categoria cat, int intensidad) {
        categorias.put(cat, intensidad);
    }

    public void configurar(File parent, int duracion) {
        this.file = new File(parent, fileName);
        this.duracion = duracion;
        this.configurado = true;
    }

    // region Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFileName() {
        return fileName;
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

    public boolean isConfigurado() {
        return configurado;
    }

    public HashMap<Categoria, Integer> getCategorias() {
        return categorias;
    }
    // endregion
}
