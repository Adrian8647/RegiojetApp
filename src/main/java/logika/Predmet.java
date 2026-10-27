package logika;


public class Predmet {

    private String id;
    private String nazev;
    private String popis;

    public Predmet(String id, String nazev, String popis) {
        this.id = id;
        this.nazev = nazev;
        this.popis = popis;
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }
}
