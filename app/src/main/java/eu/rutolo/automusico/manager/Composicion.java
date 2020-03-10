package eu.rutolo.automusico.manager;

import java.util.HashMap;

import eu.rutolo.automusico.db.Categoria;

public class Composicion {

    private HashMap<Categoria, Integer> vals;

    public Composicion() {
        vals = new HashMap<>();
    }

    public Composicion(HashMap<Categoria, Integer> vals) {
        this.vals = vals;
    }

    public void addVal(Categoria c, int val) {
        vals.put(c, val);
    }
}
