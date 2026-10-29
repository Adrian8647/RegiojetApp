package logika;


public class Uloha {

    private String id;
    private String nazev;
    private String zadani;
    private String zadavatel;      // id postavy, "-" = hlavni ukol
    private Stav stavSplneni;      // kdyz tento stav plati, ukol je splneny

    public Uloha(String id, String nazev, String zadani, String zadavatel, Stav stavSplneni) {
        this.id = id;
        this.nazev = nazev;
        this.zadani = zadani;
        this.zadavatel = zadavatel;
        this.stavSplneni = stavSplneni;
    }

    // Ukol je splneny, kdyz plati jeho stav
    public boolean jeSplnena(HerniPlan plan) {
        return plan.jeStav(stavSplneni);
    }

    // Hrac ukol zna, kdyz je hlavni, kdyz uz mluvil se zadavatelem, nebo kdyz ho uz splnil
    public boolean jeZnama(HerniPlan plan) {
        return zadavatel.equals("-") || plan.jeStav(Stav.zTextu("mluvil_" + zadavatel)) || jeSplnena(plan);
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public String getZadani() {
        return zadani;
    }

    public String getZadavatel() {
        return zadavatel;
    }
}
