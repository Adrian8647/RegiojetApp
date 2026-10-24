package logika;

/**
 * Pravidlo pro prikazy "pouzij" a "dej".
 * Rika, co se stane, kdyz hrac pouzije predmet na objekt nebo ho da postave.
 */
public class Pravidlo {

    private String typ;              // "pouzij" nebo "dej"
    private String predmet;          // id predmetu
    private String cil;              // id objektu nebo postavy
    private String podminka;         // "-" = zadna
    private String textNesplneno;    // vypise se, kdyz podminka neplati
    private String efekty;           // co se ma stat (napr. "ziskej:nabijecka,stav:jarda_dal")
    private String text;             // vypise se, kdyz se akce povede

    public Pravidlo(String typ, String predmet, String cil, String podminka,
                    String textNesplneno, String efekty, String text) {
        this.typ = typ;
        this.predmet = predmet;
        this.cil = cil;
        this.podminka = podminka;
        this.textNesplneno = textNesplneno;
        this.efekty = efekty;
        this.text = text;
    }

    public String getTyp() {
        return typ;
    }

    public String getPredmet() {
        return predmet;
    }

    public String getCil() {
        return cil;
    }

    public String getPodminka() {
        return podminka;
    }

    public String getTextNesplneno() {
        return textNesplneno;
    }

    public String getEfekty() {
        return efekty;
    }

    public String getText() {
        return text;
    }
}
