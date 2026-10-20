package logika;

/**
 * Vychod z jedne lokality do druhe.
 * Muze byt zavreny, dokud neplati podminka (napr. kufr_odklizen).
 */
public class Vychod {

    private String cil;            // id lokality, kam vychod vede
    private String podminka;       // "-" = vychod je vzdy otevreny
    private String textZavreno;    // co se vypise, kdyz je zavreno

    public Vychod(String cil, String podminka, String textZavreno) {
        this.cil = cil;
        this.podminka = podminka;
        this.textZavreno = textZavreno;
    }

    public String getCil() {
        return cil;
    }

    public String getPodminka() {
        return podminka;
    }

    public String getTextZavreno() {
        return textZavreno;
    }
}
