package logika;

import java.util.HashMap;
import java.util.Map;


public class SeznamPrikazu {

    // Klic je nazev prikazu, hodnota je samotny prikaz
    private Map<String, IPrikaz> prikazy = new HashMap<>();

    // Prida prikaz do seznamu
    public void vlozPrikaz(IPrikaz prikaz) {
        prikazy.put(prikaz.getNazev(), prikaz);
    }

    // Vrati prikaz podle nazvu, kdyz neexistuje vrati null
    public IPrikaz vratPrikaz(String nazev) {
        return prikazy.get(nazev);
    }
}
