package eu.rutolo.automusico.db;

public abstract class Tipo {

    private int idTipo;
    private String nom;
    private String descr;

    public Tipo(String nom, String descr) {
        this.nom = nom;
        this.descr = descr;
    }

    public Tipo(int idTipo, String nom, String descr) {
        this.idTipo = idTipo;
        this.nom = nom;
        this.descr = descr;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public String getNom() {
        return nom;
    }

    public String getDescr() {
        return descr;
    }
}
