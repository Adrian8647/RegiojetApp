package logika;

import java.util.HashMap;
import java.util.Map;

/**
 * Herni plan drzi cely svet hry (lokality, predmety, postavy...)
 * a taky vsechno, co se behem hrani meni (kde je hrac, co ma u sebe, pocitadlo presunu...).
 */
public class HerniPlan {

    // ---- Data nactena ze souboru ve slozce data (klic je vzdy id) ----
    private Map<String, String> texty = new HashMap<>();

    // ---- Stav hry, ktery se meni a uklada ----
    private boolean konecHry = false;

    // Vrati text z texty.txt podle klice
    public String getText(String klic) {
        return texty.get(klic);
    }

    // ---- Gettery a settery ----

    public Map<String, String> getTexty() {
        return texty;
    }

    public boolean isKonecHry() {
        return konecHry;
    }

    public void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
}
