package logika;

import java.util.ArrayList;
import java.util.List;


public class Prostor {

    private String id;
    private String nazev;
    private Stav stavZmeny;       // kdyz tento stav plati, pouzije se popisPoZmene
    private String popis;
    private String popisPoZmene;
    private List<Vychod> vychody = new ArrayList<>();
    private SeznamVeci veci = new SeznamVeci();   // predmety, ktere tu lezi

    public Prostor(String id, String nazev, Stav stavZmeny, String popis, String popisPoZmene) {
        this.id = id;
        this.nazev = nazev;
        this.stavZmeny = stavZmeny;
        this.popis = popis;
        this.popisPoZmene = popisPoZmene;
    }

    // Vrati popis podle toho, jak daleko je hrac ve hre
    public String getPopis(HerniPlan plan) {
        if (plan.jeStav(stavZmeny)) {
            return popisPoZmene;
        }
        return popis;
    }

    // Prida vychod do jine lokality
    public void pridejVychod(Vychod vychod) {
        vychody.add(vychod);
    }

    // Najde vychod podle id cilove lokality, kdyz neexistuje vrati null
    public Vychod najdiVychod(String cil) {
        return vychody.stream()
                .filter(vychod -> vychod.getCil().equals(cil))
                .findFirst()
                .orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public List<Vychod> getVychody() {
        return vychody;
    }

    public SeznamVeci getVeci() {
        return veci;
    }
}
