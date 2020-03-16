package eu.rutolo.automusico.db;

public class TipoFuncional extends Tipo {

    private int maxSimult;
    private int minSimult;

    public TipoFuncional(int idTipo, String nom, String descr, int minSimult, int maxSimult) {
        super(idTipo, nom, descr);
        this.minSimult = minSimult;
        this.maxSimult = maxSimult;
    }

    public int getMaxSimult() {
        return maxSimult;
    }

    public int getMinSimult() {
        return minSimult;
    }
}
