package eu.rutolo.automusico.db;

public class TipoEstructural extends Tipo {

    public TipoEstructural(String nom, String descr) {
        super(nom, descr);
    }

    public TipoEstructural(int idTipo, String nom, String descr) {
        super(idTipo, nom, descr);
    }
}
