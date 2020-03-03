package eu.rutolo.automusico.db;

class TipoFuncional extends Tipo {

    private int maxSimult;

    public TipoFuncional(String nom, String descr, int maxSimult) {
        super(nom, descr);
        this.maxSimult = maxSimult;
    }

    public TipoFuncional(int idTipo, String nom, String descr, int maxSimult) {
        super(idTipo, nom, descr);
        this.maxSimult = maxSimult;
    }

    public int getMaxSimult() {
        return maxSimult;
    }
}
