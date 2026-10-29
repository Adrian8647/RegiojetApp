package logika;


public class Objekt {

    private String id;
    private String nazev;          // tohle pise hrac (napr. "zasuvka")
    private String lokalita;       // id lokality, kde objekt je
    private Stav stavZmeny;
    private String popis;
    private String popisPoZmene;

    public Objekt(String id, String nazev, String lokalita, Stav stavZmeny, String popis, String popisPoZmene) {
        this.id = id;
        this.nazev = nazev;
        this.lokalita = lokalita;
        this.stavZmeny = stavZmeny;
        this.popis = popis;
        this.popisPoZmene = popisPoZmene;
    }

    // Vrati popis podle aktualniho stavu hry
    public String getPopis(HerniPlan plan) {
        if (plan.jeStav(stavZmeny)) {
            return popisPoZmene;
        }
        return popis;
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public String getLokalita() {
        return lokalita;
    }
}
