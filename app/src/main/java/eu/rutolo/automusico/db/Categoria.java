package eu.rutolo.automusico.db;

public class Categoria {

    private int id;
    private String nom;
    private String descr;

    /**
     * constuctor con id para usar en FragmentosDBHelper.
     *
     * @param id
     * @param nom
     * @param descr
     */
    Categoria(int id, String nom, String descr) {
        this.id = id;
        this.nom = nom;
        this.descr = descr;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescr() {
        return descr;
    }

}
